package com.fh.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.member.po.MemberPo;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper extends BaseMapper<MemberPo> {
    void updateIntegral(@Param("memberId") Long memberId, @Param("integral") Integer integral);
}
