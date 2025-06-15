package com.example.login.service;

import com.example.login.model.User;
import com.example.login.model.LoginRequest;
import com.example.login.model.LoginResponse;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Comprehensive test suite for MockAuthService
 * Tests authentication, token validation, user management, and edge cases
 */
public class MockAuthServiceTest {
    
    private MockAuthService authService;
    
    // Test data constants
    private static final String VALID_USERNAME = "demo";
    private static final String VALID_PASSWORD = "demo";
    private static final String INVALID_USERNAME = "nonexistent";
    private static final String INVALID_PASSWORD = "wrongpassword";
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String MANAGER_USERNAME = "manager";
    private static final String MANAGER_PASSWORD = "manager123";
    
    // Expected user data
    private static final List<String> EXPECTED_USERNAMES = Arrays.asList(
        "admin", "user", "demo", "test", "manager"
    );
    
    @Before
    public void setUp() {
        authService = new MockAuthService();
    }
    
    @After
    public void tearDown() {
        authService = null;
    }
    
    // ========== INITIALIZATION TESTS ==========
    
    @Test
    public void testConstructorInitializesUsers() {
        Map<String, User> users = authService.getAllUsers();
        
        assertThat("Users map should not be null", users, is(notNullValue()));
        assertThat("Should have exactly 5 predefined users", users.size(), is(5));
        
        // Verify all expected users exist
        for (String username : EXPECTED_USERNAMES) {
            assertThat("Should contain user: " + username, 
                users.containsKey(username), is(true));
        }
    }
    
    @Test
    public void testInitializedUsersHaveCorrectRoles() {
        Map<String, User> users = authService.getAllUsers();
        
        assertThat("Admin should have ADMIN role", 
            users.get("admin").getRole(), is("ADMIN"));
        assertThat("Manager should have MANAGER role", 
            users.get("manager").getRole(), is("MANAGER"));
        assertThat("Demo user should have USER role", 
            users.get("demo").getRole(), is("USER"));
        assertThat("Test user should have USER role", 
            users.get("test").getRole(), is("USER"));
        assertThat("Regular user should have USER role", 
            users.get("user").getRole(), is("USER"));
    }
    
    @Test
    public void testInitializedUsersHaveCorrectPasswords() {
        Map<String, User> users = authService.getAllUsers();
        
        assertThat("Admin password should be correct", 
            users.get("admin").getPassword(), is("admin123"));
        assertThat("Manager password should be correct", 
            users.get("manager").getPassword(), is("manager123"));
        assertThat("Demo password should be correct", 
            users.get("demo").getPassword(), is("demo"));
        assertThat("Test password should be correct", 
            users.get("test").getPassword(), is("test123"));
        assertThat("User password should be correct", 
            users.get("user").getPassword(), is("password"));
    }
    
    // ========== SUCCESSFUL AUTHENTICATION TESTS ==========
    
    @Test
    public void testAuthenticateValidCredentials() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertSuccessfulAuthentication(response, "USER");
    }
    
    @Test
    public void testAuthenticateAllValidUsers() {
        // Test all predefined users
        testUserAuthentication("admin", "admin123", "ADMIN");
        testUserAuthentication("demo", "demo", "USER");
        testUserAuthentication("test", "test123", "USER");
        testUserAuthentication("user", "password", "USER");
        testUserAuthentication("manager", "manager123", "MANAGER");
    }
    
    @Test
    public void testAuthenticateAdminUser() {
        LoginRequest request = new LoginRequest(ADMIN_USERNAME, ADMIN_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertSuccessfulAuthentication(response, "ADMIN");
    }
    
    @Test
    public void testAuthenticateManagerUser() {
        LoginRequest request = new LoginRequest(MANAGER_USERNAME, MANAGER_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertSuccessfulAuthentication(response, "MANAGER");
    }
    
    // ========== FAILED AUTHENTICATION TESTS ==========
    
    @Test
    public void testAuthenticateInvalidUsername() {
        LoginRequest request = new LoginRequest(INVALID_USERNAME, VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Invalid username or password");
    }
    
    @Test
    public void testAuthenticateInvalidPassword() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, INVALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Invalid username or password");
    }
    
    @Test
    public void testAuthenticateInvalidCredentials() {
        LoginRequest request = new LoginRequest(INVALID_USERNAME, INVALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Invalid username or password");
    }
    
    @Test
    public void testAuthenticateCaseSensitiveUsername() {
        LoginRequest request = new LoginRequest("DEMO", VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Invalid username or password");
    }
    
    @Test
    public void testAuthenticateCaseSensitivePassword() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, "DEMO");
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Invalid username or password");
    }
    
    // ========== NULL AND EMPTY INPUT VALIDATION TESTS ==========
    
    @Test
    public void testAuthenticateNullRequest() {
        LoginResponse response = authService.authenticate(null);
        
        assertFailedAuthentication(response, "Username and password are required");
    }
    
    @Test
    public void testAuthenticateNullUsername() {
        LoginRequest request = new LoginRequest(null, VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password are required");
    }
    
    @Test
    public void testAuthenticateNullPassword() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, null);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password are required");
    }
    
    @Test
    public void testAuthenticateNullBothFields() {
        LoginRequest request = new LoginRequest(null, null);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password are required");
    }
    
    @Test
    public void testAuthenticateEmptyUsername() {
        LoginRequest request = new LoginRequest("", VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password cannot be empty");
    }
    
    @Test
    public void testAuthenticateEmptyPassword() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, "");
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password cannot be empty");
    }
    
    @Test
    public void testAuthenticateEmptyBothFields() {
        LoginRequest request = new LoginRequest("", "");
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password cannot be empty");
    }
    
    @Test
    public void testAuthenticateWhitespaceUsername() {
        LoginRequest request = new LoginRequest("   ", VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password cannot be empty");
    }
    
    @Test
    public void testAuthenticateWhitespacePassword() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, "   ");
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password cannot be empty");
    }
    
    @Test
    public void testAuthenticateWhitespaceBothFields() {
        LoginRequest request = new LoginRequest("   ", "   ");
        LoginResponse response = authService.authenticate(request);
        
        assertFailedAuthentication(response, "Username and password cannot be empty");
    }
    
    @Test
    public void testAuthenticateTrimsWhitespace() {
        LoginRequest request = new LoginRequest("  demo  ", "  demo  ");
        LoginResponse response = authService.authenticate(request);
        
        assertSuccessfulAuthentication(response, "USER");
    }
    
    @Test
    public void testAuthenticateTrimsWhitespaceAllUsers() {
        testUserAuthentication("  admin  ", "  admin123  ", "ADMIN");
        testUserAuthentication("  manager  ", "  manager123  ", "MANAGER");
    }
    
    // ========== TOKEN VALIDATION TESTS ==========
    
    @Test
    public void testValidateTokenValid() {
        String validToken = "token_abcdef1234567890";
        assertThat("Valid token should pass validation", 
            authService.validateToken(validToken), is(true));
    }
    
    @Test
    public void testValidateTokenGeneratedToken() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        
        assertThat("Generated token should be valid", 
            authService.validateToken(response.getToken()), is(true));
    }
    
    @Test
    public void testValidateTokenInvalidPrefix() {
        String invalidToken = "invalid_abcdef1234567890";
        assertThat("Token without correct prefix should fail", 
            authService.validateToken(invalidToken), is(false));
    }
    
    @Test
    public void testValidateTokenTooShort() {
        String shortToken = "token_abc";
        assertThat("Short token should fail validation", 
            authService.validateToken(shortToken), is(false));
    }
    
    @Test
    public void testValidateTokenMinimumLength() {
        String minimumToken = "token_1234567890"; // exactly 11 characters
        assertThat("Token with minimum length should pass", 
            authService.validateToken(minimumToken), is(true));
    }
    
    @Test
    public void testValidateTokenNull() {
        assertThat("Null token should fail validation", 
            authService.validateToken(null), is(false));
    }
    
    @Test
    public void testValidateTokenEmpty() {
        assertThat("Empty token should fail validation", 
            authService.validateToken(""), is(false));
    }
    
    @Test
    public void testValidateTokenWhitespace() {
        assertThat("Whitespace token should fail validation", 
            authService.validateToken("   "), is(false));
    }
    
    @Test
    public void testValidateTokenSpecialCharacters() {
        String tokenWithSpecialChars = "token_abc!@#$%^&*()";
        assertThat("Token with special characters should pass if long enough", 
            authService.validateToken(tokenWithSpecialChars), is(true));
    }
    
    // ========== TOKEN GENERATION TESTS ==========
    
    @Test
    public void testTokenGeneration() {
        LoginRequest request1 = new LoginRequest(VALID_USERNAME, VALID_PASSWORD);
        LoginRequest request2 = new LoginRequest(VALID_USERNAME, VALID_PASSWORD);
        
        LoginResponse response1 = authService.authenticate(request1);
        LoginResponse response2 = authService.authenticate(request2);
        
        assertThat("Tokens should be unique", 
            response1.getToken(), is(not(equalTo(response2.getToken()))));
        assertThat("First token should be valid", 
            authService.validateToken(response1.getToken()), is(true));
        assertThat("Second token should be valid", 
            authService.validateToken(response2.getToken()), is(true));
    }
    
    @Test
    public void testTokenGenerationFormat() {
        LoginRequest request = new LoginRequest(VALID_USERNAME, VALID_PASSWORD);
        LoginResponse response = authService.authenticate(request);
        String token = response.getToken();
        
        assertThat("Token should start with token_", 
            token.startsWith("token_"), is(true));
        assertThat("Token should be at least 22 characters long", 
            token.length() >= 22, is(true));
        assertThat("Token should contain only valid characters", 
            token.matches("token_[a-f0-9]+"), is(true));
    }
    
    @Test
    public void testTokenGenerationConsistency() {
        // Generate multiple tokens and verify they all follow the same format
        for (int i = 0; i < 10; i++) {
            LoginRequest request = new LoginRequest(VALID_USERNAME, VALID_PASSWORD);
            LoginResponse response = authService.authenticate(request);
            String token = response.getToken();
            
            assertThat("Token " + i + " should be valid", 
                authService.validateToken(token), is(true));
            assertThat("Token " + i + " should have correct format", 
                token.startsWith("token_"), is(true));
        }
    }
    
    // ========== USER MANAGEMENT TESTS ==========
    
    @Test
    public void testGetAllUsersReturnsDefensiveCopy() {
        Map<String, User> users1 = authService.getAllUsers();
        Map<String, User> users2 = authService.getAllUsers();
        
        assertThat("Should return different instances", users1, is(not(sameInstance(users2))));
        assertThat("Should have same content", users1.size(), is(users2.size()));
        
        // Modify one map and ensure the other is not affected
        users1.clear();
        assertThat("Original map should not be affected", 
            authService.getAllUsers().isEmpty(), is(false));
    }
    
    @Test
    public void testGetAllUsersImmutability() {
        Map<String, User> users = authService.getAllUsers();
        int originalSize = users.size();
        
        // Try to modify the returned map
        users.put("hacker", new User("hacker", "password", "HACKER"));
        
        // Verify the service's internal state is not affected
        Map<String, User> freshUsers = authService.getAllUsers();
        assertThat("Service should not be affected by external modifications", 
            freshUsers.size(), is(originalSize));
        assertThat("Hacker user should not exist in service", 
            freshUsers.containsKey("hacker"), is(false));
    }
    
    // ========== PERFORMANCE AND CONCURRENCY TESTS ==========
    
    @Test
    public void testConcurrentAuthentication() throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<LoginResponse>> futures = Arrays.asList(
            executor.submit(() -> authService.authenticate(new LoginRequest("demo", "demo"))),
            executor.submit(() -> authService.authenticate(new LoginRequest("admin", "admin123"))),
            executor.submit(() -> authService.authenticate(new LoginRequest("manager", "manager123"))),
            executor.submit(() -> authService.authenticate(new LoginRequest("test", "test123"))),
            executor.submit(() -> authService.authenticate(new LoginRequest("user", "password")))
        );
        
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        // Verify all authentications succeeded
        for (Future<LoginResponse> future : futures) {
            LoginResponse response = future.get();
            assertThat("Concurrent authentication should succeed", 
                response.isSuccess(), is(true));
            assertThat("Token should be generated", 
                response.getToken(), is(notNullValue()));
        }
    }
    
    @Test
    public void testAuthenticationPerformance() {
        long startTime = System.currentTimeMillis();
        
        // Perform 1000 authentications
        for (int i = 0; i < 1000; i++) {
            LoginRequest request = new LoginRequest(VALID_USERNAME, VALID_PASSWORD);
            LoginResponse response = authService.authenticate(request);
            assertThat("Authentication should succeed", response.isSuccess(), is(true));
        }
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        
        assertThat("1000 authentications should complete within 1 second", 
            duration < 1000, is(true));
    }
    
    // ========== HELPER METHODS ==========
    
    /**
     * Helper method to test user authentication
     */
    private void testUserAuthentication(String username, String password, String expectedRole) {
        LoginRequest request = new LoginRequest(username, password);
        LoginResponse response = authService.authenticate(request);
        
        assertSuccessfulAuthentication(response, expectedRole);
    }
    
    /**
     * Helper method to assert successful authentication
     */
    private void assertSuccessfulAuthentication(LoginResponse response, String expectedRole) {
        assertThat("Authentication should succeed", response.isSuccess(), is(true));
        assertThat("Should return success message", response.getMessage(), is("Login successful"));
        assertThat("Token should be generated", response.getToken(), is(notNullValue()));
        assertThat("Role should match expected", response.getRole(), is(expectedRole));
        assertThat("Token should start with token_", 
            response.getToken().startsWith("token_"), is(true));
        assertThat("Token should be valid", 
            authService.validateToken(response.getToken()), is(true));
    }
    
    /**
     * Helper method to assert failed authentication
     */
    private void assertFailedAuthentication(LoginResponse response, String expectedMessage) {
        assertThat("Authentication should fail", response.isSuccess(), is(false));
        assertThat("Should return expected error message", response.getMessage(), is(expectedMessage));
        assertThat("Token should be null", response.getToken(), is(nullValue()));
        assertThat("Role should be null", response.getRole(), is(nullValue()));
    }
}