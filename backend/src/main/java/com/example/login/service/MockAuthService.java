package com.example.login.service;

import com.example.login.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MockAuthService {
    
    private final Map<String, String> mockUsers;
    private final Map<String, User> userDetails;

    public MockAuthService() {
        // Initialize mock users
        mockUsers = new HashMap<>();
        userDetails = new HashMap<>();
        
        // Add some test users
        mockUsers.put("admin", "admin123");
        mockUsers.put("user", "password");
        mockUsers.put("test", "test123");
        mockUsers.put("demo", "demo");
        
        // Add user details
        userDetails.put("admin", new User("admin", "admin@example.com", "ADMIN"));
        userDetails.put("user", new User("user", "user@example.com", "USER"));
        userDetails.put("test", new User("test", "test@example.com", "USER"));
        userDetails.put("demo", new User("demo", "demo@example.com", "USER"));
    }

    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        
        String storedPassword = mockUsers.get(username.toLowerCase());
        return password.equals(storedPassword);
    }

    public User getUserDetails(String username) {
        return userDetails.get(username.toLowerCase());
    }

    public String generateToken(String username) {
        // Generate a mock JWT-like token
        return "mock-jwt-token-" + UUID.randomUUID().toString() + "-" + username;
    }

    public boolean isValidToken(String token) {
        // Simple mock validation - just check if it starts with our prefix
        return token != null && token.startsWith("mock-jwt-token-");
    }

    public String getUsernameFromToken(String token) {
        if (!isValidToken(token)) {
            return null;
        }
        
        // Extract username from the end of the token
        String[] parts = token.split("-");
        if (parts.length > 0) {
            return parts[parts.length - 1];
        }
        return null;
    }
}