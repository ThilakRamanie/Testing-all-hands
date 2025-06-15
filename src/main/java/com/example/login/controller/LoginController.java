package com.example.login.controller;

import com.example.login.model.LoginRequest;
import com.example.login.model.LoginResponse;
import com.example.login.service.MockAuthService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class LoginController implements HttpHandler {
    private MockAuthService authService;
    
    public LoginController() {
        this.authService = new MockAuthService();
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // Set CORS headers
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
        
        String method = exchange.getRequestMethod();
        
        if ("OPTIONS".equals(method)) {
            exchange.sendResponseHeaders(200, 0);
            exchange.close();
            return;
        }
        
        if ("POST".equals(method)) {
            handleLogin(exchange);
        } else if ("GET".equals(method)) {
            handleHealthCheck(exchange);
        } else {
            sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
        }
    }
    
    private void handleLogin(HttpExchange exchange) throws IOException {
        try {
            // Read request body
            InputStream inputStream = exchange.getRequestBody();
            String requestBody = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            
            // Parse JSON manually (simple parsing for username and password)
            LoginRequest loginRequest = parseLoginRequest(requestBody);
            
            // Authenticate
            LoginResponse response = authService.authenticate(loginRequest);
            
            // Convert response to JSON
            String jsonResponse = toJson(response);
            
            // Send response
            int statusCode = response.isSuccess() ? 200 : 401;
            sendResponse(exchange, statusCode, jsonResponse);
            
        } catch (Exception e) {
            sendResponse(exchange, 400, "{\"success\":false,\"message\":\"Invalid request format\"}");
        }
    }
    
    private void handleHealthCheck(HttpExchange exchange) throws IOException {
        String response = "{\"status\":\"OK\",\"message\":\"Login service is running\"}";
        sendResponse(exchange, 200, response);
    }
    
    private LoginRequest parseLoginRequest(String json) {
        // Simple JSON parsing for username and password
        String username = extractJsonValue(json, "username");
        String password = extractJsonValue(json, "password");
        return new LoginRequest(username, password);
    }
    
    private String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\"";
        int keyIndex = json.indexOf(searchKey);
        if (keyIndex == -1) return null;
        
        int colonIndex = json.indexOf(":", keyIndex);
        if (colonIndex == -1) return null;
        
        int startQuote = json.indexOf("\"", colonIndex);
        if (startQuote == -1) return null;
        
        int endQuote = json.indexOf("\"", startQuote + 1);
        if (endQuote == -1) return null;
        
        return json.substring(startQuote + 1, endQuote);
    }
    
    private String toJson(LoginResponse response) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"success\":").append(response.isSuccess()).append(",");
        json.append("\"message\":\"").append(response.getMessage()).append("\"");
        
        if (response.getToken() != null) {
            json.append(",\"token\":\"").append(response.getToken()).append("\"");
        }
        
        if (response.getRole() != null) {
            json.append(",\"role\":\"").append(response.getRole()).append("\"");
        }
        
        json.append("}");
        return json.toString();
    }
    
    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        
        OutputStream outputStream = exchange.getResponseBody();
        outputStream.write(responseBytes);
        outputStream.close();
    }
}