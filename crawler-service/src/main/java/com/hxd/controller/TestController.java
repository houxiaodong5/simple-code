package com.hxd.controller;

import com.hxd.service.BiLi2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2020/11/18/018.
 */
@RestController
public class TestController {

    @Autowired
    private BiLi2Service biLi2Service;

    @GetMapping("/bili")
    public String crawlerBiLi2(){
        biLi2Service.chineseClassics();
        return "success";
    }
}
