package com.fh.shop.cart.service;

import com.alibaba.fastjson.JSON;
import com.fh.shop.cart.vo.CartInfoVo;
import com.fh.shop.cart.vo.CartVo;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.goods.po.SkuPo;
import com.fh.shop.goods.service.SkuFeign;
import com.fh.shop.util.BigDecimalUtil;
import com.fh.shop.util.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service("cartService")
@Transactional(rollbackFor = Exception.class)
public class CartServiceImpl implements CartService{

    @Autowired
    private SkuFeign skuFeign;
    //private SkuMapper skuMapper;

    @Override
    public ServerResponse addCart(Long memberId, Long skuId, Integer count) {

        //判断sku是否为空
        //SkuPo skuPo = skuMapper.selectById(skuId);
        ServerResponse<SkuPo> skuPoServerResponse = skuFeign.skuSelectId(skuId);
        SkuPo skuPo = skuPoServerResponse.getData();
        if (skuPo == null){
            return ServerResponse.error();
        }

        //判断是否上架
        if (skuPo.getFrame() == 0){
            return ServerResponse.error();
        }

        //判断库存和购买量
        if (skuPo.getStock() < Integer.valueOf(count)){
            return ServerResponse.error();
        }

        //String cartString = RedisUtil.get("cart:" + memberId);
        String cartString = RedisUtil.hget("cart:member:"+memberId,"cart:" + memberId);
        CartInfoVo cartInfoVo = new CartInfoVo();
        Map<String,String> map = new HashMap<>();
        if (StringUtils.isEmpty(cartString)){
            if (count < 0){
                return ServerResponse.error();
            }
            CartVo cartVo = new CartVo();
            cartInfoVo.setSkuId(skuId);
            cartInfoVo.setImage(skuPo.getImage());
            cartInfoVo.setPrice(skuPo.getPrice().toString());
            cartInfoVo.setSkuName(skuPo.getSkuName());
            cartInfoVo.setStock(count);
            String price = skuPo.getPrice().toString();
            BigDecimal sumPrice = BigDecimalUtil.multiplyBigDecimal(price, count+"");
            cartInfoVo.setSumPrice(sumPrice.toString());
            cartVo.getCartInfoVoList().add(cartInfoVo);
            cartVo.setSumPrice(sumPrice.toString());
            cartVo.setSumStock(count);

            //RedisUtil.set("cart:"+memberId,JSON.toJSONString(cartVo));
            map.put("cart:"+memberId,JSON.toJSONString(cartVo));
            map.put("cart:count:"+memberId,count+"");
            RedisUtil.hmset("cart:member:"+memberId,map);
        }else {
            CartVo cartVo = JSON.parseObject(cartString, CartVo.class);
            List<CartInfoVo> cartInfoVoList = cartVo.getCartInfoVoList();
            Optional<CartInfoVo> cartInfoVoOptional = cartInfoVoList.stream().filter(x -> x.getSkuId() == skuId).findFirst();
            if (cartInfoVoOptional.isPresent()){
                cartInfoVo = cartInfoVoOptional.get();
                Integer stock = cartInfoVo.getStock() + Integer.valueOf(count);
                if (stock <= 0){
                    cartInfoVoList.removeIf(x -> x.getSkuId() == skuId);
                    if (cartInfoVoList.size() == 0){
                        RedisUtil.del("cart:member:"+memberId);
                        return ServerResponse.success();
                    }
                    Integer sumStock = 0;
                    BigDecimal bigDecimal = new BigDecimal(0);
                    for (CartInfoVo x : cartVo.getCartInfoVoList()) {
                        sumStock += x.getStock().intValue();
                        bigDecimal = bigDecimal.add(new BigDecimal(x.getSumPrice()));
                    }
                    cartVo.setSumStock(sumStock);
                    cartVo.setSumPrice(bigDecimal.toString());
                    //RedisUtil.set("cart:"+memberId,JSON.toJSONString(cartVo));
                    map.put("cart:"+memberId,JSON.toJSONString(cartVo));
                    map.put("cart:count:"+memberId,sumStock+"");
                    RedisUtil.hmset("cart:member:"+memberId,map);
                    return ServerResponse.success();
                }
                cartInfoVo.setStock(stock);
                BigDecimal sumPrice = BigDecimalUtil.multiplyBigDecimal(cartInfoVo.getPrice(), stock + "");
                cartInfoVo.setSumPrice(sumPrice.toString());

                Integer sumStock = 0;
                BigDecimal bigDecimal = new BigDecimal(0);
                for (CartInfoVo x : cartVo.getCartInfoVoList()) {
                    sumStock += x.getStock().intValue();
                    bigDecimal = bigDecimal.add(new BigDecimal(x.getSumPrice()));
                }
                //String sumProces = BigDecimalUtil.addBigDecimal(sumPrice, cartVo.getSumPrice());

                cartVo.setSumStock(sumStock);
                cartVo.setSumPrice(bigDecimal.toString());
                map.put("cart:"+memberId,JSON.toJSONString(cartVo));
                map.put("cart:count:"+memberId,sumStock+"");
            }else {
                if (count < 0){
                    return ServerResponse.error();
                }
                cartInfoVo.setSkuId(skuId);
                cartInfoVo.setImage(skuPo.getImage());
                cartInfoVo.setPrice(skuPo.getPrice().toString());
                cartInfoVo.setSkuName(skuPo.getSkuName());
                cartInfoVo.setStock(count);
                String price = skuPo.getPrice().toString();
                BigDecimal sumPrice = BigDecimalUtil.multiplyBigDecimal(price, count+"");
                cartInfoVo.setSumPrice(sumPrice.toString());
                cartVo.getCartInfoVoList().add(cartInfoVo);
                String sumPrices = BigDecimalUtil.addBigDecimal(sumPrice, cartVo.getSumPrice());
                cartVo.setSumPrice(sumPrices);
                cartVo.setSumStock(cartVo.getSumStock()+count);
                map.put("cart:"+memberId,JSON.toJSONString(cartVo));
                map.put("cart:count:"+memberId,cartVo.getSumStock()+count+"");
            }
            //RedisUtil.set("cart:"+memberId,JSON.toJSONString(cartVo));
            RedisUtil.hmset("cart:member:"+memberId,map);
        }
        return ServerResponse.success();
    }


    @Transactional(readOnly = true)
    @Override
    public ServerResponse selectCart(Long memberId) {
        String cart = RedisUtil.hget("cart:member:"+memberId,"cart:" + memberId);
        //String cart = RedisUtil.get("cart:" + memberId);
        CartVo cartVo = JSON.parseObject(cart, CartVo.class);
        return ServerResponse.success(cartVo);
    }

    @Override
    public ServerResponse deleteCart(Long memberId, Long skuId) {
        Map<String,String> map = new HashMap<>();
        String cartJson = RedisUtil.hget("cart:member:" + memberId, "cart:" + memberId);
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        List<CartInfoVo> cartInfoVoList = cartVo.getCartInfoVoList();
        cartInfoVoList.removeIf(x -> x.getSkuId() == skuId);
        if (cartInfoVoList.size() == 0){
            RedisUtil.del("cart:member:"+memberId);
            return ServerResponse.success();
        }
        Integer sumStock = 0;
        BigDecimal bigDecimal = new BigDecimal(0);
        for (CartInfoVo x : cartVo.getCartInfoVoList()) {
            sumStock += x.getStock().intValue();
            bigDecimal = bigDecimal.add(new BigDecimal(x.getSumPrice()));
        }
        //String sumProces = BigDecimalUtil.addBigDecimal(sumPrice, cartVo.getSumPrice());
        cartVo.setSumStock(sumStock);
        cartVo.setSumPrice(bigDecimal.toString());
        map.put("cart:"+memberId,JSON.toJSONString(cartVo));
        map.put("cart:count:"+memberId,sumStock+"");
        RedisUtil.hmset("cart:member:"+memberId,map);
        return ServerResponse.success();
    }

}
