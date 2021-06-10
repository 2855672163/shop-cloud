package com.fh.shop.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Member {

    private Long id;

    private String memberName;

    private String password;

    private String nickName;

    private String phone;

    private String mail;

    private Integer activate;

    private Integer integral;

}
