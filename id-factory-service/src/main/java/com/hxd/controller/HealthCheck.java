package com.hxd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/8/1/001.
 */
@RestController
public class HealthCheck {
    private static final String HEALTH = "id-service-health";

    @GetMapping("/health")
    public String healthCheck() {
        return HEALTH;
    }
}
