package com.example.login;

import com.example.login.controller.LoginController;
import com.example.login.controller.StaticFileHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class LoginServer {
    private static final int PORT = 12000;
    private static final String WEB_ROOT = "web";
    
    public static void main(String[] args) {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
            
            // API endpoints
            server.createContext("/api/login", new LoginController());
            server.createContext("/api/health", new LoginController());
            
            // Static file serving
            server.createContext("/", new StaticFileHandler(WEB_ROOT));
            
            server.setExecutor(null);
            server.start();
            
            System.out.println("=================================");
            System.out.println("Login Server Started Successfully!");
            System.out.println("=================================");
            System.out.println("Frontend: http://localhost:" + PORT);
            System.out.println("API: http://localhost:" + PORT + "/api");
            System.out.println("Health Check: http://localhost:" + PORT + "/api/health");
            System.out.println();
            System.out.println("Test Users:");
            System.out.println("- admin / admin123 (ADMIN)");
            System.out.println("- user / password (USER)");
            System.out.println("- demo / demo (USER)");
            System.out.println("- test / test123 (USER)");
            System.out.println("- manager / manager123 (MANAGER)");
            System.out.println();
            System.out.println("Press Ctrl+C to stop the server");
            System.out.println("=================================");
            
        } catch (IOException e) {
            System.err.println("Failed to start server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}