package com.fh.shop.cart.controller;

import com.fh.shop.cart.service.CartService;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.member.controller.HanderRequest;
import com.fh.shop.member.po.MemberPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
public class CartController extends HanderRequest {

    @Resource(name = "cartService")
    private CartService cartService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping("/addCart")
    public ServerResponse addCart(Long skuId,Integer count){
        //MemberPo member = (MemberPo) httpServletRequest.getAttribute("member");
        MemberPo memberPo = requestMember(httpServletRequest);
        Long memberId = memberPo.getId();
        return cartService.addCart(memberId,skuId,count);
        //return ServerResponse.success();
    }


    @GetMapping("/selectCart")
    public ServerResponse selectCart(){
        System.out.println("1");
        MemberPo memberPo = requestMember(httpServletRequest);
        Long memberId = memberPo.getId();
        return cartService.selectCart(memberId);
    }


    @PostMapping("/deleteCart")
    public ServerResponse deleteCart(Long skuId){
        MemberPo memberPo = requestMember(httpServletRequest);
        Long memberId = memberPo.getId();
        return cartService.deleteCart(memberId,skuId);
    }

}
