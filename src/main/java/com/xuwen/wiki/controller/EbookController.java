package com.xuwen.wiki.controller;

import com.xuwen.req.EbookReq;
import com.xuwen.resp.CommonResp;
import com.xuwen.resp.EbookResp;
import com.xuwen.wiki.service.EbookService;
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

    @GetMapping("/list")
    public CommonResp list(EbookReq req){
        CommonResp<List<EbookResp>> resp = new CommonResp<>();
        List<EbookResp> list = ebookService.list(req);
        resp.setContent(list);
        return resp;
    }
}
