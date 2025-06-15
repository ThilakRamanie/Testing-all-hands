package com.example.login.controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticFileHandler implements HttpHandler {
    private String webRoot;
    
    public StaticFileHandler(String webRoot) {
        this.webRoot = webRoot;
    }
    
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        
        // Default to index.html for root path
        if ("/".equals(path)) {
            path = "/index.html";
        }
        
        File file = new File(webRoot + path);
        
        if (!file.exists() || file.isDirectory()) {
            // File not found
            String notFound = "<html><body><h1>404 - File Not Found</h1></body></html>";
            exchange.sendResponseHeaders(404, notFound.length());
            OutputStream os = exchange.getResponseBody();
            os.write(notFound.getBytes());
            os.close();
            return;
        }
        
        // Set content type based on file extension
        String contentType = getContentType(path);
        exchange.getResponseHeaders().add("Content-Type", contentType);
        
        // Send file
        exchange.sendResponseHeaders(200, file.length());
        OutputStream os = exchange.getResponseBody();
        FileInputStream fis = new FileInputStream(file);
        
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        
        fis.close();
        os.close();
    }
    
    private String getContentType(String path) {
        if (path.endsWith(".html")) {
            return "text/html";
        } else if (path.endsWith(".css")) {
            return "text/css";
        } else if (path.endsWith(".js")) {
            return "application/javascript";
        } else if (path.endsWith(".json")) {
            return "application/json";
        } else if (path.endsWith(".png")) {
            return "image/png";
        } else if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (path.endsWith(".gif")) {
            return "image/gif";
        } else {
            return "text/plain";
        }
    }
}