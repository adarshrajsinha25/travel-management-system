package com.EaseTravel.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @GetMapping("/api/test")
    public Map<String, String> testApi() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("message", "API is working!");
        return response;
    }
}

