package com.xuwen.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@PostMapping
//@GetMapping
//@PutMapping
//@Deletemapping

@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello world springboot wiki";
    }
}
