package com.xuwen.wiki.controller;

import com.xuwen.wiki.domain.Demo;
import com.xuwen.wiki.service.DemoService;
import com.xuwen.wiki.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

//@PostMapping
//@GetMapping
//@PutMapping
//@Deletemapping

@RestController
@RequestMapping("/demo")
public class DemoController {

    @Resource
    private DemoService demoService;


    @GetMapping("/list")
    public List<Demo> list(){
        return demoService.list();
    }
}
