package com.hxd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Created by Administrator on 2020/8/1/001.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy // 启用了Zuul作为反向代理服务器
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
