package com.fh.shop.member.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class MemberCartVo{

    @TableField(exist = false)
    private Integer sumStock;

}
