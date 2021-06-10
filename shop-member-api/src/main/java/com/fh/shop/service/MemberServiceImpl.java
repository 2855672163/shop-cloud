package com.fh.shop.service;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.mapper.MemberMapper;
import com.fh.shop.member.po.MemberPo;
import com.fh.shop.util.Find;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.RedisUtil;
import com.fh.shop.vo.CartVo;
import com.fh.shop.vo.LoginMemberVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public ServerResponse memberLogin(String memberName, String password) {

        if (StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.MEMBER_NAME_PASSWORD_IS_NULL);
        }

        QueryWrapper<MemberPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        MemberPo memberPo = memberMapper.selectOne(queryWrapper);
        if (memberPo == null){
            return ServerResponse.error(ResponseEnum.MEMBER_IS_NULL);
        }

        if (!password.equals(memberPo.getPassword())){
            return ServerResponse.error(ResponseEnum.MEMBER_PASSWORD_ERROR);
        }

        if(memberPo.getActivate() == 0){
            Map map = new HashMap();
            map.put("id",memberPo.getId());
            map.put("mail",memberPo.getMail());
            return ServerResponse.error(ResponseEnum.LOGIN_ACTIVATE_ERROR,map);
        }
        LoginMemberVo loginMemberVo = new LoginMemberVo();
        loginMemberVo.setId(memberPo.getId());
        loginMemberVo.setMemberName(memberPo.getMemberName());
        loginMemberVo.setNickName(memberPo.getNickName());

        String memberVoJsonString = JSON.toJSONString(loginMemberVo);
        String sign = Md5Util.md5(memberVoJsonString + Find.secret);

        String memberVoJsonStringBase64 = Base64.getEncoder().encodeToString(memberVoJsonString.getBytes());
        String signBase64 = Base64.getEncoder().encodeToString(sign.getBytes());

        RedisUtil.setEx("memberName:"+memberName,"",10 * 60);
        return ServerResponse.success(memberVoJsonStringBase64+"."+signBase64);
    }

    @Override
    public ServerResponse selectMemberName(String memberName) {
        if (memberName == null || memberName == ""){
            return ServerResponse.error(ResponseEnum.USERNAME_IS_NULL);
        }
        QueryWrapper<MemberPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("memberName",memberName);
        MemberPo memberPo = memberMapper.selectOne(queryWrapper);
        if (memberPo != null){
            return ServerResponse.error(ResponseEnum.USERNAME_ERROR);
        }
        return ServerResponse.success();
    }

    @Override
    public Integer selectSumStock(Long id) {
        String cartJson = RedisUtil.hget("cart:member:"+id,"cart:" + id);
        //String cartJson = RedisUtil.get("cart:" + id);
        CartVo cartVo = new CartVo();
        cartVo.setSumStock(0);
        if (StringUtils.isNotEmpty(cartJson)){
            cartVo = JSON.parseObject(cartJson, CartVo.class);
        }
        return cartVo.getSumStock();
    }


}