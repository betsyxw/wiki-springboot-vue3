package com.xuwen.wiki.service;

import com.xuwen.wiki.domain.Ebook;
import com.xuwen.wiki.mapper.EbookMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:xuwen
 * Created on 2021/8/14
 */
@Service
public class EbookService {
    @Resource
    private EbookMapper ebookMapper;

    public List<Ebook> list(){

        return ebookMapper.selectByExample(null);
    }

}
