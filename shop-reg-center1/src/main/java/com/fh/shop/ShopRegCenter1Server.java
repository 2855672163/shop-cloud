package com.fh.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShopRegCenter1Server {

    public static void main(String[] args) {
        SpringApplication.run(ShopRegCenter1Server.class,args);
    }

}
