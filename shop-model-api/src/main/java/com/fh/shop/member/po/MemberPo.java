package com.fh.shop.member.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fh.shop.member.vo.MemberCartVo;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("t_member")
public class MemberPo extends MemberCartVo implements Serializable{

    private Long id;

    @TableField("memberName")
    private String memberName;

    @TableField("password")
    private String password;

    @TableField("nickName")
    private String nickName;

    private String phone;

    private String mail;

    private Integer activate;

    private Integer integral;

}
