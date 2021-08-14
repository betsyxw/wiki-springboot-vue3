package com.xuwen.wiki.service;

import com.xuwen.req.EbookReq;
import com.xuwen.resp.EbookResp;
import com.xuwen.util.CopyUtil;
import com.xuwen.wiki.domain.Ebook;
import com.xuwen.wiki.domain.EbookExample;
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

    public List<EbookResp> list(EbookReq req){
        //根据书名查找,字符串拼接
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        criteria.andNameLike("%"+req.getName()+"%");
        List<Ebook> ebooksList = ebookMapper.selectByExample(ebookExample);

//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebooksList) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook,ebookResp);
        //对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }

        List<EbookResp> list = CopyUtil.copyList(ebooksList, EbookResp.class);
        return list;
    }

}
