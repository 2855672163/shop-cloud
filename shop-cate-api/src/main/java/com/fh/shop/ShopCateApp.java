package com.fh.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shop.cate.mapper")
public class ShopCateApp {

    public static void main(String[] args) {
        SpringApplication.run(ShopCateApp.class,args);
    }

}
