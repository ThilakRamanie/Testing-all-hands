package com.example.login.service;

import com.example.login.model.User;
import com.example.login.model.LoginRequest;
import com.example.login.model.LoginResponse;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.util.Map;

public class MockAuthServiceTest {
    private MockAuthService authService;
    
    @Before
    public void setUp() {
        authService = new MockAuthService();
    }
    
    @Test
    public void testConstructorInitializesUsers() {
        Map<String, User> users = authService.getAllUsers();
        assertNotNull("Users map should not be null", users);
        assertTrue("Should have predefined users", users.size() > 0);
        
        // Check if default users exist
        assertTrue("Should contain admin user", users.containsKey("admin"));
        assertTrue("Should contain demo user", users.containsKey("demo"));
        assertTrue("Should contain user", users.containsKey("user"));
        assertTrue("Should contain test user", users.containsKey("test"));
        assertTrue("Should contain manager user", users.containsKey("manager"));
    }
    
    @Test
    public void testAuthenticateValidCredentials() {
        LoginRequest request = new LoginRequest("demo", "demo");
        LoginResponse response = authService.authenticate(request);
        
        assertTrue("Authentication should succeed", response.isSuccess());
        assertEquals("Should return success message", "Login successful", response.getMessage());
        assertNotNull("Token should be generated", response.getToken());
        assertEquals("Role should be USER", "USER", response.getRole());
        assertTrue("Token should start with token_", response.getToken().startsWith("token_"));
    }
    
    @Test
    public void testAuthenticateInvalidUsername() {
        LoginRequest request = new LoginRequest("nonexistent", "password");
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return invalid credentials message", "Invalid username or password", response.getMessage());
        assertNull("Token should be null", response.getToken());
        assertNull("Role should be null", response.getRole());
    }
    
    @Test
    public void testAuthenticateInvalidPassword() {
        LoginRequest request = new LoginRequest("demo", "wrongpassword");
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return invalid credentials message", "Invalid username or password", response.getMessage());
        assertNull("Token should be null", response.getToken());
        assertNull("Role should be null", response.getRole());
    }
    
    @Test
    public void testAuthenticateNullRequest() {
        LoginResponse response = authService.authenticate(null);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return required fields message", "Username and password are required", response.getMessage());
        assertNull("Token should be null", response.getToken());
        assertNull("Role should be null", response.getRole());
    }
    
    @Test
    public void testAuthenticateNullUsername() {
        LoginRequest request = new LoginRequest(null, "password");
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return required fields message", "Username and password are required", response.getMessage());
    }
    
    @Test
    public void testAuthenticateNullPassword() {
        LoginRequest request = new LoginRequest("demo", null);
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return required fields message", "Username and password are required", response.getMessage());
    }
    
    @Test
    public void testAuthenticateEmptyUsername() {
        LoginRequest request = new LoginRequest("", "password");
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return empty fields message", "Username and password cannot be empty", response.getMessage());
    }
    
    @Test
    public void testAuthenticateEmptyPassword() {
        LoginRequest request = new LoginRequest("demo", "");
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return empty fields message", "Username and password cannot be empty", response.getMessage());
    }
    
    @Test
    public void testAuthenticateWhitespaceUsername() {
        LoginRequest request = new LoginRequest("   ", "password");
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return empty fields message", "Username and password cannot be empty", response.getMessage());
    }
    
    @Test
    public void testAuthenticateWhitespacePassword() {
        LoginRequest request = new LoginRequest("demo", "   ");
        LoginResponse response = authService.authenticate(request);
        
        assertFalse("Authentication should fail", response.isSuccess());
        assertEquals("Should return empty fields message", "Username and password cannot be empty", response.getMessage());
    }
    
    @Test
    public void testAuthenticateTrimsWhitespace() {
        LoginRequest request = new LoginRequest("  demo  ", "  demo  ");
        LoginResponse response = authService.authenticate(request);
        
        assertTrue("Authentication should succeed with trimmed values", response.isSuccess());
        assertEquals("Should return success message", "Login successful", response.getMessage());
    }
    
    @Test
    public void testAuthenticateAdminUser() {
        LoginRequest request = new LoginRequest("admin", "admin123");
        LoginResponse response = authService.authenticate(request);
        
        assertTrue("Admin authentication should succeed", response.isSuccess());
        assertEquals("Role should be ADMIN", "ADMIN", response.getRole());
    }
    
    @Test
    public void testAuthenticateManagerUser() {
        LoginRequest request = new LoginRequest("manager", "manager123");
        LoginResponse response = authService.authenticate(request);
        
        assertTrue("Manager authentication should succeed", response.isSuccess());
        assertEquals("Role should be MANAGER", "MANAGER", response.getRole());
    }
    
    @Test
    public void testValidateTokenValid() {
        String validToken = "token_abcdef1234567890";
        assertTrue("Valid token should pass validation", authService.validateToken(validToken));
    }
    
    @Test
    public void testValidateTokenInvalidPrefix() {
        String invalidToken = "invalid_abcdef1234567890";
        assertFalse("Token without correct prefix should fail", authService.validateToken(invalidToken));
    }
    
    @Test
    public void testValidateTokenTooShort() {
        String shortToken = "token_abc";
        assertFalse("Short token should fail validation", authService.validateToken(shortToken));
    }
    
    @Test
    public void testValidateTokenNull() {
        assertFalse("Null token should fail validation", authService.validateToken(null));
    }
    
    @Test
    public void testValidateTokenEmpty() {
        assertFalse("Empty token should fail validation", authService.validateToken(""));
    }
    
    @Test
    public void testGetAllUsersReturnsDefensiveCopy() {
        Map<String, User> users1 = authService.getAllUsers();
        Map<String, User> users2 = authService.getAllUsers();
        
        assertNotSame("Should return different instances", users1, users2);
        assertEquals("Should have same content", users1.size(), users2.size());
        
        // Modify one map and ensure the other is not affected
        users1.clear();
        assertFalse("Original map should not be affected", authService.getAllUsers().isEmpty());
    }
    
    @Test
    public void testTokenGeneration() {
        LoginRequest request1 = new LoginRequest("demo", "demo");
        LoginRequest request2 = new LoginRequest("demo", "demo");
        
        LoginResponse response1 = authService.authenticate(request1);
        LoginResponse response2 = authService.authenticate(request2);
        
        assertNotEquals("Tokens should be unique", response1.getToken(), response2.getToken());
        assertTrue("Both tokens should be valid", authService.validateToken(response1.getToken()));
        assertTrue("Both tokens should be valid", authService.validateToken(response2.getToken()));
    }
}