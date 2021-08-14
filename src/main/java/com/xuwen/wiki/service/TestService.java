package com.xuwen.wiki.service;

import com.xuwen.wiki.domain.Test;
import com.xuwen.wiki.mapper.TestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:xuwen
 * Created on 2021/5/24
 */
@Service
public class TestService {
//    @Autowired都可以写一个即可
    @Resource
    private TestMapper testMapper;


    //方法
    public List<Test> list(){

        return testMapper.list();
    }

}
