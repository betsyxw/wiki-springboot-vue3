package com.xuwen.wiki.controller;

import com.xuwen.req.CategoryQueryReq;
import com.xuwen.req.CategorySaveReq;
import com.xuwen.resp.CategoryQueryResp;
import com.xuwen.resp.CommonResp;
import com.xuwen.resp.PageResp;
import com.xuwen.wiki.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

//@PostMapping
//@GetMapping
//@PutMapping
//@Deletemapping

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;


//    @GetMapping("/list")
//    public CommonResp list(String name){
//        CommonResp<List<Category>> resp = new CommonResp<>();
//        List<Category> list = categoryService.list(name);
//        resp.setContent(list);
//        return resp;
//    }

    //查询数据
    @GetMapping("/list")
    public CommonResp list( @Valid CategoryQueryReq req){
        CommonResp<PageResp<CategoryQueryResp>> resp = new CommonResp<>();
        PageResp<CategoryQueryResp> list = categoryService.list(req);
        resp.setContent(list);
        return resp;
    }

    //查询全部数据
    @GetMapping("/all")
    public CommonResp all(){
        CommonResp<List<CategoryQueryResp>> resp = new CommonResp<>();
        List<CategoryQueryResp> list = categoryService.all();
        resp.setContent(list);
        return resp;
    }



    //编辑,保存
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody CategorySaveReq req){
        CommonResp resp = new CommonResp<>();
        categoryService.save(req);
        return resp;
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        categoryService.delete(id);
        return resp;
    }






}
