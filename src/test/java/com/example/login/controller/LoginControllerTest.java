package com.example.login.controller;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpPrincipal;

public class LoginControllerTest {
    private LoginController loginController;
    private MockHttpExchange mockExchange;
    
    @Before
    public void setUp() {
        loginController = new LoginController();
        mockExchange = new MockHttpExchange();
    }
    
    @Test
    public void testHandleOptionsRequest() throws IOException {
        mockExchange.setRequestMethod("OPTIONS");
        
        loginController.handle(mockExchange);
        
        assertEquals("Should return 200 for OPTIONS", 200, mockExchange.getResponseCode());
        assertTrue("Should set CORS headers", mockExchange.getResponseHeaders().containsKey("Access-Control-Allow-Origin"));
    }
    
    @Test
    public void testHandleGetHealthCheck() throws IOException {
        mockExchange.setRequestMethod("GET");
        
        loginController.handle(mockExchange);
        
        assertEquals("Should return 200 for health check", 200, mockExchange.getResponseCode());
        String response = mockExchange.getResponseBodyAsString();
        assertTrue("Response should contain status", response.contains("\"status\":\"OK\""));
        assertTrue("Response should contain message", response.contains("\"message\":\"Login service is running\""));
    }
    
    @Test
    public void testHandlePostValidLogin() throws IOException {
        mockExchange.setRequestMethod("POST");
        String requestBody = "{\"username\":\"demo\",\"password\":\"demo\"}";
        mockExchange.setRequestBody(requestBody);
        
        loginController.handle(mockExchange);
        
        assertEquals("Should return 200 for valid login", 200, mockExchange.getResponseCode());
        String response = mockExchange.getResponseBodyAsString();
        assertTrue("Response should indicate success", response.contains("\"success\":true"));
        assertTrue("Response should contain token", response.contains("\"token\":"));
        assertTrue("Response should contain role", response.contains("\"role\":"));
    }
    
    @Test
    public void testHandlePostInvalidLogin() throws IOException {
        mockExchange.setRequestMethod("POST");
        String requestBody = "{\"username\":\"invalid\",\"password\":\"wrong\"}";
        mockExchange.setRequestBody(requestBody);
        
        loginController.handle(mockExchange);
        
        assertEquals("Should return 401 for invalid login", 401, mockExchange.getResponseCode());
        String response = mockExchange.getResponseBodyAsString();
        assertTrue("Response should indicate failure", response.contains("\"success\":false"));
        assertTrue("Response should contain error message", response.contains("Invalid username or password"));
    }
    
    @Test
    public void testHandlePostMalformedJson() throws IOException {
        mockExchange.setRequestMethod("POST");
        String requestBody = "{invalid json}";
        mockExchange.setRequestBody(requestBody);
        
        loginController.handle(mockExchange);
        
        // The simple JSON parser doesn't throw exceptions, so it returns 401 for null credentials
        assertEquals("Should return 401 for malformed JSON (parsed as null credentials)", 401, mockExchange.getResponseCode());
        String response = mockExchange.getResponseBodyAsString();
        assertTrue("Response should indicate failure", response.contains("\"success\":false"));
        assertTrue("Response should contain required fields error", response.contains("required"));
    }
    
    @Test
    public void testHandleUnsupportedMethod() throws IOException {
        mockExchange.setRequestMethod("DELETE");
        
        loginController.handle(mockExchange);
        
        assertEquals("Should return 405 for unsupported method", 405, mockExchange.getResponseCode());
        String response = mockExchange.getResponseBodyAsString();
        assertTrue("Response should indicate method not allowed", response.contains("Method not allowed"));
    }
    
    @Test
    public void testCorsHeaders() throws IOException {
        mockExchange.setRequestMethod("GET");
        
        loginController.handle(mockExchange);
        
        Headers headers = mockExchange.getResponseHeaders();
        assertEquals("Should set CORS origin header", "*", headers.getFirst("Access-Control-Allow-Origin"));
        assertTrue("Should set CORS methods header", headers.getFirst("Access-Control-Allow-Methods").contains("GET"));
        assertTrue("Should set CORS methods header", headers.getFirst("Access-Control-Allow-Methods").contains("POST"));
        assertEquals("Should set CORS headers", "Content-Type", headers.getFirst("Access-Control-Allow-Headers"));
    }
    
    @Test
    public void testContentTypeHeader() throws IOException {
        mockExchange.setRequestMethod("GET");
        
        loginController.handle(mockExchange);
        
        assertEquals("Should set JSON content type", "application/json", 
                    mockExchange.getResponseHeaders().getFirst("Content-Type"));
    }
    
    @Test
    public void testPostWithEmptyCredentials() throws IOException {
        mockExchange.setRequestMethod("POST");
        String requestBody = "{\"username\":\"\",\"password\":\"\"}";
        mockExchange.setRequestBody(requestBody);
        
        loginController.handle(mockExchange);
        
        assertEquals("Should return 401 for empty credentials", 401, mockExchange.getResponseCode());
        String response = mockExchange.getResponseBodyAsString();
        assertTrue("Response should indicate failure", response.contains("\"success\":false"));
        assertTrue("Response should contain empty error", response.contains("cannot be empty"));
    }
    
    @Test
    public void testPostWithMissingFields() throws IOException {
        mockExchange.setRequestMethod("POST");
        String requestBody = "{\"username\":\"demo\"}"; // Missing password
        mockExchange.setRequestBody(requestBody);
        
        loginController.handle(mockExchange);
        
        assertEquals("Should return 401 for missing fields", 401, mockExchange.getResponseCode());
        String response = mockExchange.getResponseBodyAsString();
        assertTrue("Response should indicate failure", response.contains("\"success\":false"));
    }
    
    // Mock HttpExchange class for testing
    private static class MockHttpExchange extends HttpExchange {
        private String requestMethod;
        private InputStream requestBody;
        private ByteArrayOutputStream responseBody = new ByteArrayOutputStream();
        private Headers requestHeaders = new Headers();
        private Headers responseHeaders = new Headers();
        private int responseCode;
        private URI requestURI;
        
        public void setRequestMethod(String method) {
            this.requestMethod = method;
        }
        
        public void setRequestBody(String body) {
            this.requestBody = new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8));
        }
        
        @Override
        public String getRequestMethod() {
            return requestMethod;
        }
        
        @Override
        public InputStream getRequestBody() {
            return requestBody;
        }
        
        @Override
        public OutputStream getResponseBody() {
            return responseBody;
        }
        
        @Override
        public Headers getRequestHeaders() {
            return requestHeaders;
        }
        
        @Override
        public Headers getResponseHeaders() {
            return responseHeaders;
        }
        
        @Override
        public void sendResponseHeaders(int responseCode, long responseLength) {
            this.responseCode = responseCode;
        }
        
        public int getResponseCode() {
            return responseCode;
        }
        
        public String getResponseBodyAsString() {
            return responseBody.toString(StandardCharsets.UTF_8);
        }
        
        @Override
        public URI getRequestURI() {
            return requestURI;
        }
        
        @Override
        public void close() {
            // Mock implementation - do nothing
        }
        
        // Other required methods with minimal implementations
        @Override
        public String getProtocol() { return "HTTP/1.1"; }
        
        @Override
        public java.net.InetSocketAddress getRemoteAddress() { return null; }
        
        @Override
        public java.net.InetSocketAddress getLocalAddress() { return null; }
        
        @Override
        public com.sun.net.httpserver.HttpContext getHttpContext() { return null; }
        
        @Override
        public HttpPrincipal getPrincipal() { return null; }
        
        @Override
        public void setAttribute(String name, Object value) {}
        
        @Override
        public Object getAttribute(String name) { return null; }
        
        @Override
        public void setStreams(InputStream i, OutputStream o) {}
    }
}