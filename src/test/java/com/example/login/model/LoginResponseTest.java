package com.example.login.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class LoginResponseTest {
    private LoginResponse loginResponse;
    
    @Before
    public void setUp() {
        loginResponse = new LoginResponse();
    }
    
    @Test
    public void testDefaultConstructor() {
        LoginResponse defaultResponse = new LoginResponse();
        assertFalse("Success should be false by default", defaultResponse.isSuccess());
        assertNull("Message should be null", defaultResponse.getMessage());
        assertNull("Token should be null", defaultResponse.getToken());
        assertNull("Role should be null", defaultResponse.getRole());
    }
    
    @Test
    public void testTwoParameterConstructor() {
        LoginResponse twoParamResponse = new LoginResponse(true, "Login successful");
        assertTrue("Success should be true", twoParamResponse.isSuccess());
        assertEquals("Message should match", "Login successful", twoParamResponse.getMessage());
        assertNull("Token should be null", twoParamResponse.getToken());
        assertNull("Role should be null", twoParamResponse.getRole());
    }
    
    @Test
    public void testFourParameterConstructor() {
        LoginResponse fourParamResponse = new LoginResponse(true, "Success", "token123", "ADMIN");
        assertTrue("Success should be true", fourParamResponse.isSuccess());
        assertEquals("Message should match", "Success", fourParamResponse.getMessage());
        assertEquals("Token should match", "token123", fourParamResponse.getToken());
        assertEquals("Role should match", "ADMIN", fourParamResponse.getRole());
    }
    
    @Test
    public void testSuccessGetterSetter() {
        loginResponse.setSuccess(true);
        assertTrue("Success should be true", loginResponse.isSuccess());
        
        loginResponse.setSuccess(false);
        assertFalse("Success should be false", loginResponse.isSuccess());
    }
    
    @Test
    public void testMessageGetterSetter() {
        String testMessage = "Authentication failed";
        loginResponse.setMessage(testMessage);
        assertEquals("Message should match set value", testMessage, loginResponse.getMessage());
    }
    
    @Test
    public void testTokenGetterSetter() {
        String testToken = "jwt_token_12345";
        loginResponse.setToken(testToken);
        assertEquals("Token should match set value", testToken, loginResponse.getToken());
    }
    
    @Test
    public void testRoleGetterSetter() {
        String testRole = "USER";
        loginResponse.setRole(testRole);
        assertEquals("Role should match set value", testRole, loginResponse.getRole());
    }
    
    @Test
    public void testSetNullValues() {
        loginResponse.setMessage(null);
        loginResponse.setToken(null);
        loginResponse.setRole(null);
        
        assertNull("Message should be null", loginResponse.getMessage());
        assertNull("Token should be null", loginResponse.getToken());
        assertNull("Role should be null", loginResponse.getRole());
    }
    
    @Test
    public void testSetEmptyValues() {
        loginResponse.setMessage("");
        loginResponse.setToken("");
        loginResponse.setRole("");
        
        assertEquals("Message should be empty string", "", loginResponse.getMessage());
        assertEquals("Token should be empty string", "", loginResponse.getToken());
        assertEquals("Role should be empty string", "", loginResponse.getRole());
    }
    
    @Test
    public void testFailureResponse() {
        LoginResponse failureResponse = new LoginResponse(false, "Invalid credentials");
        assertFalse("Success should be false", failureResponse.isSuccess());
        assertEquals("Message should indicate failure", "Invalid credentials", failureResponse.getMessage());
    }
    
    @Test
    public void testSuccessResponseWithTokenAndRole() {
        LoginResponse successResponse = new LoginResponse(true, "Welcome", "abc123", "MANAGER");
        assertTrue("Success should be true", successResponse.isSuccess());
        assertEquals("Message should be welcome", "Welcome", successResponse.getMessage());
        assertEquals("Token should be set", "abc123", successResponse.getToken());
        assertEquals("Role should be MANAGER", "MANAGER", successResponse.getRole());
    }
}