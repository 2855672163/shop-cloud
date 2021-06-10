package com.fh.shop.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResetMember implements Serializable {

    private String memberName;

    private String mail;

    private String password;

    private String passwordNew;

    private String code;

}
