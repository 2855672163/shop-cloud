package com.fh.shop.goods.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuVos {

    private Long id;

    private String skuName;

    private BigDecimal price;

    private String image;

    private Integer stock;

    private String brandName;

    private String cateName;


}