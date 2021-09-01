package com.xuwen.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwen.req.CategoryQueryReq;
import com.xuwen.req.CategorySaveReq;
import com.xuwen.resp.CategoryQueryResp;
import com.xuwen.resp.PageResp;
import com.xuwen.util.CopyUtil;
import com.xuwen.util.SnowFlake;
import com.xuwen.wiki.domain.Category;
import com.xuwen.wiki.domain.CategoryExample;
import com.xuwen.wiki.mapper.CategoryMapper;
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
public class CategoryService {
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);


    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<CategoryQueryResp> list(CategoryQueryReq req){

        //根据书名查找,字符串拼接
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();

        //导入分页插件pom，然后写,pagehelper就+limit而已
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categorysList = categoryMapper.selectByExample(categoryExample);
        PageInfo<Category> pageInfo = new PageInfo<>(categorysList);
        LOG.info("总行数：{}",pageInfo.getTotal());
        LOG.info("总页数：{}",pageInfo.getPages());


//        List<CategoryResp> respList = new ArrayList<>();
//        for (Category category : categorysList) {
////            CategoryResp categoryResp = new CategoryResp();
////            BeanUtils.copyProperties(category,categoryResp);
        //对象复制
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            respList.add(categoryResp);
//        }

        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categorysList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);


        return pageResp;
    }

    //save,保存(编辑保存)
    public void save(CategorySaveReq req){
        Category category = CopyUtil.copy(req,Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //id=空，就是新增
            category.setId(snowFlake.nextId());
//            categoryMapper.insert(category);
            //insertSelective比insert好用，允许null
            categoryMapper.insertSelective(category);
        }else{
            //存在id，更新+编辑
            categoryMapper.updateByPrimaryKey(category);
        }

    }

    //delete,删除
    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }

}
