package com.fh.shop.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.goods.po.SkuPo;
import com.fh.shop.goods.vo.SkuVos;
import com.fh.shop.goods.vo.UpdateSkuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SkuMapper extends BaseMapper<SkuPo> {

    List<SkuVos> selectSkuUser();

    List<SkuVos> selectSkuList();


    int updateSkuStock(UpdateSkuVo x);

    void updateBySales(@Param("skuId") Long skuId, @Param("sales") Integer sales);
}
