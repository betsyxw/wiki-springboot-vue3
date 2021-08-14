package com.xuwen.wiki.controller;

import com.xuwen.wiki.domain.Test;
import com.xuwen.wiki.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@PostMapping
//@GetMapping
//@PutMapping
//@Deletemapping

@RestController
public class TestController {
    //引入service层内容
    @Resource
    private TestService testService;


    @GetMapping("/hello")
    public String hello(){
        return "Hello world";
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "Hello world Post,"+name;
    }

    @GetMapping("/test/list")
    public List<Test> list(){

        return testService.list();
    }
}
