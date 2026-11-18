package com.abalitskyy.deploydemo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {

    private final String version;

    public HelloController(@Value("${APP_VERSION:local}") String version) {
        this.version = version;
    }

    @GetMapping("/api/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Hello everyone from deploy-demo!", "version", version);
    }
}
