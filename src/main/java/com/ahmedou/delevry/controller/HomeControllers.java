package com.ahmedou.delevry.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeControllers {
    
    @GetMapping("/")
    public ResponseEntity<Resource> getDashboard() {
        Resource indexHtml = new ClassPathResource("/static/index.html");
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(indexHtml);
    }
}
