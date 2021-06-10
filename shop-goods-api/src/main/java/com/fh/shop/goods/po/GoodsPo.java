package com.fh.shop.goods.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author GZX
 * @since 2021-03-22
 */
@Data
@TableName("t_goods")
public class GoodsPo implements Serializable {

    private Long id;

    /**
     * 商品名称
     */
    @TableField("spuName")
    private String spuName;

    /**
     * 格价
     */
    private BigDecimal price;

    /**
     * 存库
     */
    private Integer stock;

    /**
     * 一级分类id
     */
    private Long cate1;

    /**
     * 二级分类id
     */
    private Long cate2;

    /**
     * 三级分类id
     */
    private Long cate3;

    /**
     * 品牌id
     */
    @TableField("brandId")
    private Long brandId;

    @TableField("attrInfo")
    private String attrInfo;

    @TableField("specInfo")
    private String specInfo;

    private String cateName;

    private Integer frame;

    private Integer newProduct;

    private Integer recommend;

}
