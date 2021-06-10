package com.fh.shop.cate.service;

import com.alibaba.fastjson.JSON;
import com.fh.shop.cate.mapper.CateMapper;
import com.fh.shop.cate.po.Cate;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("cateService")
public class CateServiceImpl implements CateService{

    @Autowired
    private CateMapper cateMapper;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse findList() {
        /*String cateListJson = RedisUtil.get("cateList");
        if (StringUtils.isNotEmpty(cateListJson)){
            List<Cate> cateList = JSON.parseArray(cateListJson, Cate.class);
            return ServerResponse.success(cateList);
        }*/
        List<Cate> cateList = cateMapper.selectList(null);
        /*String jsonString = JSON.toJSONString(cateList);
        RedisUtil.set("cateList",jsonString);*/
        return ServerResponse.success(cateList);
    }

}
