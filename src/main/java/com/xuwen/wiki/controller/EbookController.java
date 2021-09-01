package com.xuwen.wiki.controller;

import com.xuwen.req.EbookQueryReq;
import com.xuwen.req.EbookSaveReq;
import com.xuwen.resp.CommonResp;
import com.xuwen.resp.EbookQueryResp;
import com.xuwen.resp.PageResp;
import com.xuwen.wiki.service.EbookService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

//@PostMapping
//@GetMapping
//@PutMapping
//@Deletemapping

@RestController
@RequestMapping("/ebook")
public class EbookController {

    @Resource
    private EbookService ebookService;


//    @GetMapping("/list")
//    public CommonResp list(String name){
//        CommonResp<List<Ebook>> resp = new CommonResp<>();
//        List<Ebook> list = ebookService.list(name);
//        resp.setContent(list);
//        return resp;
//    }

    //查询全部数据
    @GetMapping("/list")
    public CommonResp list( @Valid EbookQueryReq req){
        CommonResp<PageResp<EbookQueryResp>> resp = new CommonResp<>();
        PageResp<EbookQueryResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }

    //编辑,保存
    @PostMapping("/save")
    public CommonResp save(@Valid @RequestBody EbookSaveReq req){
        CommonResp resp = new CommonResp<>();
        ebookService.save(req);
        return resp;
    }

    //删除
    @DeleteMapping("/delete/{id}")
    public CommonResp delete(@PathVariable Long id){
        CommonResp resp = new CommonResp<>();
        ebookService.delete(id);
        return resp;
    }






}
