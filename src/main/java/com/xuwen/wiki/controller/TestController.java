package com.xuwen.wiki.controller;

import com.xuwen.wiki.domain.Test;
import com.xuwen.wiki.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@PostMapping
//@GetMapping
//@PutMapping
//@Deletemapping

@RestController
public class TestController {

    @Resource
    private TestService testService;


    @GetMapping("/hello")
    public String hello(){
        return "Hello world springboot wiki";
    }

    @GetMapping("/test/list")
    public List<Test> list(){
        return testService.list();
    }
}
