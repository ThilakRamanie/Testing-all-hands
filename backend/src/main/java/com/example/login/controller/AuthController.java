package com.example.login.controller;

import com.example.login.model.LoginRequest;
import com.example.login.model.LoginResponse;
import com.example.login.model.User;
import com.example.login.service.MockAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private MockAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            String username = loginRequest.getUsername();
            String password = loginRequest.getPassword();

            // Simulate some processing delay
            Thread.sleep(500);

            if (authService.authenticate(username, password)) {
                User user = authService.getUserDetails(username);
                String token = authService.generateToken(username);
                
                LoginResponse response = new LoginResponse(
                    true, 
                    "Login successful", 
                    token, 
                    user
                );
                
                return ResponseEntity.ok(response);
            } else {
                LoginResponse response = new LoginResponse(false, "Invalid username or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LoginResponse response = new LoginResponse(false, "Server error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (Exception e) {
            LoginResponse response = new LoginResponse(false, "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        // In a real application, you would invalidate the token here
        LoginResponse response = new LoginResponse(true, "Logout successful");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, "Authorization header missing or invalid"));
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        
        if (!authService.isValidToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(false, "Invalid token"));
        }

        String username = authService.getUsernameFromToken(token);
        User user = authService.getUserDetails(username);
        
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new LoginResponse(false, "User not found"));
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Login service is running");
    }
}