package com.fh.shop.vo;

import com.fh.shop.member.po.MemberPo;
import lombok.Data;

import java.io.Serializable;

@Data
public class RegMemberVo extends MemberPo implements Serializable {

    private String passwordNew;

    private String captcha;

}
