package com.fh.shop.goods.service;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.goods.po.SkuPo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "shop-goods-api")
public interface SkuFeign {

    @PostMapping("/api/sku/selectId/{id}")
    ServerResponse<SkuPo> skuSelectId(@PathVariable("id") Long id);

}
