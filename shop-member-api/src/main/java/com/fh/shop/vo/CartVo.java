package com.fh.shop.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartVo {

    private List<CartInfoVo> cartInfoVoList = new ArrayList<>();

    private String sumPrice;

    private Integer sumStock;

}
