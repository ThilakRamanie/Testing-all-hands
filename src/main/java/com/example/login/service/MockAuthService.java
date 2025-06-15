package com.example.login.service;

import com.example.login.model.User;
import com.example.login.model.LoginRequest;
import com.example.login.model.LoginResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MockAuthService {
    private Map<String, User> users;
    
    public MockAuthService() {
        initializeUsers();
    }
    
    private void initializeUsers() {
        users = new HashMap<>();
        users.put("admin", new User("admin", "admin123", "ADMIN"));
        users.put("user", new User("user", "password", "USER"));
        users.put("demo", new User("demo", "demo", "USER"));
        users.put("test", new User("test", "test123", "USER"));
        users.put("manager", new User("manager", "manager123", "MANAGER"));
    }
    
    public LoginResponse authenticate(LoginRequest request) {
        if (request == null || request.getUsername() == null || request.getPassword() == null) {
            return new LoginResponse(false, "Username and password are required");
        }
        
        String username = request.getUsername().trim();
        String password = request.getPassword().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            return new LoginResponse(false, "Username and password cannot be empty");
        }
        
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            String token = generateToken();
            return new LoginResponse(true, "Login successful", token, user.getRole());
        }
        
        return new LoginResponse(false, "Invalid username or password");
    }
    
    public boolean validateToken(String token) {
        // Simple mock token validation
        return token != null && token.startsWith("token_") && token.length() > 10;
    }
    
    private String generateToken() {
        return "token_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
    }
    
    public Map<String, User> getAllUsers() {
        return new HashMap<>(users);
    }
}