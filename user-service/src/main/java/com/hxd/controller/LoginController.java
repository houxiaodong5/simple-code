package com.hxd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/8/1/001.
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @GetMapping("/qq")
    public String login() {
        return "login by qq successful!";
    }
}
