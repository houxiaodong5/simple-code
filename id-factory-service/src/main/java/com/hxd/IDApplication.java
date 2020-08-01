package com.hxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by Administrator on 2020/8/1/001.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class IDApplication {
    public static void main(String[] args) {
        SpringApplication.run(IDApplication.class, args);
    }
}
