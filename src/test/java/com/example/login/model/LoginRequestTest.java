package com.example.login.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class LoginRequestTest {
    private LoginRequest loginRequest;
    
    @Before
    public void setUp() {
        loginRequest = new LoginRequest();
    }
    
    @Test
    public void testDefaultConstructor() {
        LoginRequest defaultRequest = new LoginRequest();
        assertNull("Username should be null", defaultRequest.getUsername());
        assertNull("Password should be null", defaultRequest.getPassword());
    }
    
    @Test
    public void testParameterizedConstructor() {
        LoginRequest paramRequest = new LoginRequest("testuser", "testpass");
        assertEquals("Username should match", "testuser", paramRequest.getUsername());
        assertEquals("Password should match", "testpass", paramRequest.getPassword());
    }
    
    @Test
    public void testUsernameGetterSetter() {
        String testUsername = "admin";
        loginRequest.setUsername(testUsername);
        assertEquals("Username should match set value", testUsername, loginRequest.getUsername());
    }
    
    @Test
    public void testPasswordGetterSetter() {
        String testPassword = "password123";
        loginRequest.setPassword(testPassword);
        assertEquals("Password should match set value", testPassword, loginRequest.getPassword());
    }
    
    @Test
    public void testSetNullValues() {
        loginRequest.setUsername(null);
        loginRequest.setPassword(null);
        
        assertNull("Username should be null", loginRequest.getUsername());
        assertNull("Password should be null", loginRequest.getPassword());
    }
    
    @Test
    public void testSetEmptyValues() {
        loginRequest.setUsername("");
        loginRequest.setPassword("");
        
        assertEquals("Username should be empty string", "", loginRequest.getUsername());
        assertEquals("Password should be empty string", "", loginRequest.getPassword());
    }
    
    @Test
    public void testParameterizedConstructorWithNullValues() {
        LoginRequest nullRequest = new LoginRequest(null, null);
        assertNull("Username should be null", nullRequest.getUsername());
        assertNull("Password should be null", nullRequest.getPassword());
    }
    
    @Test
    public void testParameterizedConstructorWithEmptyValues() {
        LoginRequest emptyRequest = new LoginRequest("", "");
        assertEquals("Username should be empty string", "", emptyRequest.getUsername());
        assertEquals("Password should be empty string", "", emptyRequest.getPassword());
    }
    
    @Test
    public void testParameterizedConstructorWithSpecialCharacters() {
        String specialUsername = "user@domain.com";
        String specialPassword = "p@ssw0rd!#$";
        LoginRequest specialRequest = new LoginRequest(specialUsername, specialPassword);
        
        assertEquals("Username with special chars should match", specialUsername, specialRequest.getUsername());
        assertEquals("Password with special chars should match", specialPassword, specialRequest.getPassword());
    }
}