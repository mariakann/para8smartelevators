package api;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

public class RestApiServer {
    private HttpServer server;
    private final int floorCount;
    private final int elevatorCount;

    public RestApiServer(int floorCount, int elevatorCount) {
        if (floorCount < 3 || floorCount > 200
                || elevatorCount < 2 || elevatorCount > 20) {
            throw new IllegalArgumentException("Invalid building size");
        }

        this.floorCount = floorCount;
        this.elevatorCount = elevatorCount;
    }

    public void startServer() throws IOException {
        server = HttpServer.create(
                new InetSocketAddress("127.0.0.1", 8080), 0);
        configureRoutes();
        server.start();
    }

    public void stopServer() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }

    public void configureRoutes() {
        server.createContext("/api/ride-requests",
                new ClientRestController(floorCount));

        server.createContext("/api/elevators/",
                new ElevatorRestController(floorCount, elevatorCount));
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Enter floors and elevators");
            return;
        }

        int floors = Integer.parseInt(args[0]);
        int elevators = Integer.parseInt(args[1]);

        RestApiServer server = new RestApiServer(floors, elevators);
        server.startServer();

        System.out.println("Server started on port 8080");
    }
}
