package com.fh.shop.cart.service;

import com.fh.shop.common.ServerResponse;

public interface CartService {
    ServerResponse addCart(Long memberId, Long skuId, Integer count);

    ServerResponse selectCart(Long memberId);

    ServerResponse deleteCart(Long memberId, Long skuId);
}
