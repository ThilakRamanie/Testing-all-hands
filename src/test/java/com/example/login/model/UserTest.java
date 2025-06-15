package com.example.login.model;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class UserTest {
    private User user;
    
    @Before
    public void setUp() {
        user = new User();
    }
    
    @Test
    public void testDefaultConstructor() {
        User defaultUser = new User();
        assertNull("Username should be null", defaultUser.getUsername());
        assertNull("Password should be null", defaultUser.getPassword());
        assertNull("Role should be null", defaultUser.getRole());
    }
    
    @Test
    public void testParameterizedConstructor() {
        User paramUser = new User("testuser", "testpass", "USER");
        assertEquals("Username should match", "testuser", paramUser.getUsername());
        assertEquals("Password should match", "testpass", paramUser.getPassword());
        assertEquals("Role should match", "USER", paramUser.getRole());
    }
    
    @Test
    public void testUsernameGetterSetter() {
        String testUsername = "john_doe";
        user.setUsername(testUsername);
        assertEquals("Username should match set value", testUsername, user.getUsername());
    }
    
    @Test
    public void testPasswordGetterSetter() {
        String testPassword = "securePassword123";
        user.setPassword(testPassword);
        assertEquals("Password should match set value", testPassword, user.getPassword());
    }
    
    @Test
    public void testRoleGetterSetter() {
        String testRole = "ADMIN";
        user.setRole(testRole);
        assertEquals("Role should match set value", testRole, user.getRole());
    }
    
    @Test
    public void testSetNullValues() {
        user.setUsername(null);
        user.setPassword(null);
        user.setRole(null);
        
        assertNull("Username should be null", user.getUsername());
        assertNull("Password should be null", user.getPassword());
        assertNull("Role should be null", user.getRole());
    }
    
    @Test
    public void testSetEmptyValues() {
        user.setUsername("");
        user.setPassword("");
        user.setRole("");
        
        assertEquals("Username should be empty string", "", user.getUsername());
        assertEquals("Password should be empty string", "", user.getPassword());
        assertEquals("Role should be empty string", "", user.getRole());
    }
    
    @Test
    public void testSetWhitespaceValues() {
        user.setUsername("   ");
        user.setPassword("   ");
        user.setRole("   ");
        
        assertEquals("Username should preserve whitespace", "   ", user.getUsername());
        assertEquals("Password should preserve whitespace", "   ", user.getPassword());
        assertEquals("Role should preserve whitespace", "   ", user.getRole());
    }
}