package com.fh.shop.goods.controller;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.goods.po.SkuPo;
import com.fh.shop.goods.service.GoodsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
@RestController
@RequestMapping("/api")
public class GoodsController {

    @Resource(name = "goodsService")
    private GoodsService goodsService;

    @RequestMapping(value = "/goods/recommendNewProduct",method = RequestMethod.GET)
    public ServerResponse selectSkuList(){
        return goodsService.selectSkuList();
    }

    @PostMapping("/sku/selectId/{id}")
    public SkuPo skuSelectId(@PathVariable Long id){
        return goodsService.skuSelectId(id);
    }
    //0 */10 * * * ?
    /*@Scheduled(cron = "0/30 * * * * ?")//每隔10分钟
    public void mailSku()
    {
        goodsService.mailSku();
    }*/

}
