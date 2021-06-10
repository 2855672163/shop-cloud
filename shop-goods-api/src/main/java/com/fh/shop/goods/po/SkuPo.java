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
 * @since 2021-03-24
 */
@Data
@TableName("t_sku")
public class SkuPo implements Serializable {


    private Long id;

    @TableField("spuId")
    private Long spuId;

    @TableField("skuName")
    private String skuName;

    private BigDecimal price;

    private Integer stock;

    @TableField("specInfo")
    private String specInfo;

    private Long colorId;

    private String Image;

    private Integer frame;

    private Integer newProduct;

    private Integer recommend;

    private Integer sales;

}
