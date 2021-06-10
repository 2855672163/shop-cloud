package com.fh.shop.controller;
import com.alibaba.fastjson.JSON;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.member.controller.HanderRequest;
import com.fh.shop.member.po.MemberPo;
import com.fh.shop.service.MemberService;
import com.fh.shop.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
@RequestMapping("/api")
public class MemberController extends HanderRequest{

    @Autowired
    private MemberService memberService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @PostMapping("/member/login")
    public ServerResponse memberLogin(String memberName, String password){
        return memberService.memberLogin(memberName,password);
    }

    @GetMapping("/member/findMember")
    public ServerResponse findMember(){
        //MemberPo member = (MemberPo) httpServletRequest.getAttribute("member");
//        String memberString = URLDecoder.decode(httpServletRequest.getHeader("member"),"utf-8");
//        MemberPo member = JSON.parseObject(memberString, MemberPo.class);
        MemberPo member = requestMember(httpServletRequest);
        Integer sumStock = memberService.selectSumStock(member.getId());
        member.setSumStock(sumStock);
        return ServerResponse.success(member);
    }

    @GetMapping("/member/write")
    public ServerResponse write() throws UnsupportedEncodingException {
        //MemberPo member = (MemberPo) httpServletRequest.getAttribute("member");
        String memberString = URLDecoder.decode(httpServletRequest.getHeader("member"),"utf-8");
        MemberPo memberPo = JSON.parseObject(memberString, MemberPo.class);
        RedisUtil.del("memberName:"+memberPo.getMemberName());
        return ServerResponse.success();
    }

}
