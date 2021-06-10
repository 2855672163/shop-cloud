package com.fh.shop.member.controller;

import com.alibaba.fastjson.JSON;
import com.fh.shop.member.po.MemberPo;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class HanderRequest {

    public MemberPo requestMember(HttpServletRequest request){
        try {
            String memberString = URLDecoder.decode(request.getHeader("member"),"utf-8");
            MemberPo member = JSON.parseObject(memberString, MemberPo.class);
            return member;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
