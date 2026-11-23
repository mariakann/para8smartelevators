package api;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ElevatorRestController implements HttpHandler {
    private final int floorCount;
    private final int elevatorCount;

    public ElevatorRestController(int floorCount, int elevatorCount) {
        this.floorCount = floorCount;
        this.elevatorCount = elevatorCount;
    }

    public void sendCommand(String elevatorId, int[] stops) {
        System.out.println("Elevator: " + elevatorId);
        for (int stop : stops) {
            System.out.println("Next stop: " + stop);
        }
    }

    public void receiveStatusUpdate(String elevatorId, int currentFloor,
            String status, int[] nextStops) {
        System.out.println("Elevator: " + elevatorId);
        System.out.println("Current floor: " + currentFloor);
        System.out.println("Status: " + status);
        for (int stop : nextStops) {
            System.out.println("Next stop: " + stop);
        }
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String[] path = exchange.getRequestURI().getPath().split("/");
        int code = 200;
        String response = "{\"message\":\"Received\"}";

        if (path.length != 5 || !path[1].equals("api")
                || !path[2].equals("elevators")
                || path[3].length() != 1
                || path[3].charAt(0) < 'A'
                || path[3].charAt(0) >= 'A' + elevatorCount
                || (!path[4].equals("commands")
                && !path[4].equals("status"))) {
            code = 404;
            response = "{\"error\":\"Not found\"}";
        } else if (!exchange.getRequestMethod().equals("POST")) {
            code = 405;
            response = "{\"error\":\"Use POST\"}";
        } else {
            try {
                String body = new String(exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8).trim();
                Map<String, String> values = readFields(body);

                if (path[4].equals("commands")) {
                    if (values.size() != 1 || !values.containsKey("stops")) {
                        throw new IllegalArgumentException();
                    }
                    sendCommand(path[3], readStops(values.get("stops")));
                } else {
                    if (values.size() < 2 || values.size() > 3
                            || !values.containsKey("currentFloor")
                            || !values.containsKey("status")) {
                        throw new IllegalArgumentException();
                    }

                    int currentFloor = Integer.parseInt(values.get("currentFloor"));
                    if (currentFloor < 0 || currentFloor >= floorCount) {
                        throw new IllegalArgumentException();
                    }

                    String value = values.get("status");
                    if (!value.matches("\"[A-Z_]+\"")) {
                        throw new IllegalArgumentException();
                    }
                    String status = value.substring(1, value.length() - 1);

                    int[] nextStops = values.containsKey("nextStops")
                            ? readStops(values.get("nextStops")) : new int[0];
                    receiveStatusUpdate(path[3], currentFloor, status, nextStops);
                }
            } catch (IllegalArgumentException e) {
                code = 400;
                response = "{\"error\":\"Invalid request\"}";
            }
        }

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set(
                "Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(code, bytes.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
        }
    }

    private Map<String, String> readFields(String body) {
        if (!body.startsWith("{") || !body.endsWith("}")) {
            throw new IllegalArgumentException();
        }

        Map<String, String> values = new HashMap<>();
        String data = body.substring(1, body.length() - 1).trim();
        for (String field : data.split(",(?=\\s*\")", -1)) {
            String[] pair = field.split(":", 2);
            if (pair.length != 2 || !pair[0].trim().matches("\"[a-zA-Z]+\"")) {
                throw new IllegalArgumentException();
            }

            String name = pair[0].trim();
            name = name.substring(1, name.length() - 1);
            if (!name.equals("stops") && !name.equals("currentFloor")
                    && !name.equals("status") && !name.equals("nextStops")) {
                throw new IllegalArgumentException();
            }

            if (values.put(name, pair[1].trim()) != null) {
                throw new IllegalArgumentException();
            }
        }
        return values;
    }

    private int[] readStops(String value) {
        if (!value.startsWith("[") || !value.endsWith("]")) {
            throw new IllegalArgumentException();
        }

        String numbers = value.substring(1, value.length() - 1).trim();
        if (numbers.isEmpty()) {
            return new int[0];
        }

        String[] parts = numbers.split(",", -1);
        int[] stops = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            stops[i] = Integer.parseInt(parts[i].trim());
            if (stops[i] < 0 || stops[i] >= floorCount) {
                throw new IllegalArgumentException();
            }
        }
        return stops;
    }
}
