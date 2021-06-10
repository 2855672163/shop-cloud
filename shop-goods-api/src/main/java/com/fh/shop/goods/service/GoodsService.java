package com.fh.shop.goods.service;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.goods.po.SkuPo;

public interface GoodsService {
    ServerResponse selectSkuList();

    SkuPo skuSelectId(Long id);

    //void mailSku();
}
