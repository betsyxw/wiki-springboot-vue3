package com.xuwen.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwen.req.EbookQueryReq;
import com.xuwen.req.EbookSaveReq;
import com.xuwen.resp.EbookQueryResp;
import com.xuwen.resp.PageResp;
import com.xuwen.util.CopyUtil;
import com.xuwen.util.SnowFlake;
import com.xuwen.wiki.domain.Ebook;
import com.xuwen.wiki.domain.EbookExample;
import com.xuwen.wiki.mapper.EbookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * author:xuwen
 * Created on 2021/8/14
 */
@Service
public class EbookService {
    private static final Logger LOG = LoggerFactory.getLogger(EbookService.class);


    @Resource
    private EbookMapper ebookMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<EbookQueryResp> list(EbookQueryReq req){

        //根据书名查找,字符串拼接
        EbookExample ebookExample = new EbookExample();
        EbookExample.Criteria criteria = ebookExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getName())){
            criteria.andNameLike("%"+req.getName()+"%");
        }
        //导入分页插件pom，然后写,pagehelper就+limit而已
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Ebook> ebooksList = ebookMapper.selectByExample(ebookExample);
        PageInfo<Ebook> pageInfo = new PageInfo<>(ebooksList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());


//        List<EbookResp> respList = new ArrayList<>();
//        for (Ebook ebook : ebooksList) {
////            EbookResp ebookResp = new EbookResp();
////            BeanUtils.copyProperties(ebook,ebookResp);
        //对象复制
//            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
//            respList.add(ebookResp);
//        }

        //列表复制
        List<EbookQueryResp> list = CopyUtil.copyList(ebooksList, EbookQueryResp.class);

        PageResp<EbookQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);


        return pageResp;
    }

    //save,保存(编辑保存)
    public void save(EbookSaveReq req){
        Ebook ebook = CopyUtil.copy(req,Ebook.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //id=空，就是新增
            ebook.setId(snowFlake.nextId());
//            ebookMapper.insert(ebook);
            //insertSelective比insert好用，允许null
            ebookMapper.insertSelective(ebook);
        }else{
            //存在id，更新+编辑
            ebookMapper.updateByPrimaryKey(ebook);
        }


    }

}
