package com.fh.shop.cate.controller;

import com.fh.shop.cate.service.CateService;
import com.fh.shop.common.ServerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
@Slf4j
public class CateController {

    @Resource(name = "cateService")
    private CateService cateService;

    @Value("${server.port}")
    private String port;

    @RequestMapping(value = "/cate/findList",method = RequestMethod.GET)
    public ServerResponse findList(){
        log.info("端口号:{}",port);
        return cateService.findList();
    }

}
