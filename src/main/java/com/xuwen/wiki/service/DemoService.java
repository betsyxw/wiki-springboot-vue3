package com.xuwen.wiki.service;

import com.xuwen.wiki.domain.Demo;
import com.xuwen.wiki.domain.Test;
import com.xuwen.wiki.mapper.DemoMapper;
import com.xuwen.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:xuwen
 * Created on 2021/5/24
 */
@Service
public class DemoService {
    @Resource
    private DemoMapper demoMapper;

    public List<Demo> list(){

        return demoMapper.selectByExample(null);
    }

}
