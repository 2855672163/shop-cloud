package com.fh.shop.cart.vo;

import lombok.Data;

@Data
public class CartInfoVo {

    private Long skuId;

    private String skuName;

    private String price;

    private Integer stock;

    private String sumPrice;

    private String Image;

}
