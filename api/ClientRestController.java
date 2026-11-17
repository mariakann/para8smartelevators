package api;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ClientRestController implements HttpHandler {
    private final int floorCount;
    public ClientRestController(int floorCount) {
        this.floorCount = floorCount;
    }
    public String requestElevator(int origin, int destination, int priority) {
        return "A";
    }
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        int code = 200;
        String response;
        if (!exchange.getRequestURI().getPath().equals("/api/ride-requests")) {
            code = 404;
            response = "{\"error\":\"Not found\"}";
        } else if (!exchange.getRequestMethod().equals("POST")) {
            code = 405;
            response = "{\"error\":\"Use POST\"}";
        } else {
            try {
                String body = new String(exchange.getRequestBody().readAllBytes(),
                        StandardCharsets.UTF_8).trim();

                if (!body.startsWith("{") || !body.endsWith("}")) {
                    throw new IllegalArgumentException();
                }
                Map<String, Integer> values = new HashMap<>();
                String data = body.substring(1, body.length() - 1);
                for (String field : data.split(",", -1)) {
                    String[] pair = field.split(":", -1);
                    if (pair.length != 2) {
                        throw new IllegalArgumentException();
                    }
                    String name = pair[0].trim();
                    if (!name.matches("\"(origin|destination|priority)\"")) {
                        throw new IllegalArgumentException();
                    }
                    name = name.substring(1, name.length() - 1);
                    int number = Integer.parseInt(pair[1].trim());
                    if (values.put(name, number) != null) {
                        throw new IllegalArgumentException();
                    }
                }

                if (values.size() != 3
                        || values.get("origin") < 0
                        || values.get("origin") >= floorCount
                        || values.get("destination") < 0
                        || values.get("destination") >= floorCount
                        || values.get("origin").equals(values.get("destination"))
                        || values.get("priority") < 1
                        || values.get("priority") > 3) {
                    throw new IllegalArgumentException();
                }

                String elevatorId = requestElevator(values.get("origin"),
                        values.get("destination"), values.get("priority"));
                response = "{\"elevatorId\":\"" + elevatorId + "\"}";
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
}
