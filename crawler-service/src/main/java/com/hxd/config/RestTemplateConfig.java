package com.hxd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Administrator on 2020/11/18/018.
 */
@Configuration
public class RestTemplateConfig {

    @Autowired
    private RestTemplateBuilder builder;

    @Bean("restTemplate")
    public RestTemplate init() {
        //return builder.basicAuthorization("15594156771","hxd@0513").build();
        return builder.build();
    }
}
