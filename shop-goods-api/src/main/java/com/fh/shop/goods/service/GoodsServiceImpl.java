package com.fh.shop.goods.service;

import com.fh.shop.common.ServerResponse;
import com.fh.shop.goods.mapper.GoodsMapper;
import com.fh.shop.goods.mapper.SkuMapper;
import com.fh.shop.goods.po.SkuPo;
import com.fh.shop.goods.vo.SkuVos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("goodsService")
@Transactional(rollbackFor = Exception.class)
public class GoodsServiceImpl implements  GoodsService{

    @Autowired
    private GoodsMapper goodsMapper;

//    @Autowired
//    private IUserMapper userMapper;
//
    @Autowired
    private SkuMapper skuMapper;
//
//    @Autowired
//    private MailUtils mailUtils;

    @Override
    @Transactional(readOnly = true)
    public ServerResponse selectSkuList() {
        /*String skuList = RedisUtil.get("skuList");
        if (StringUtils.isNotEmpty(skuList)){
            List<SkuVo> skuVos = JSON.parseArray(skuList, SkuVo.class);
            return ServerResponse.success(skuVos);
        }*/
        List<SkuVos> skuVos = skuMapper.selectSkuList();
        //String skuListJson = JSON.toJSONString(skuVos);
        //RedisUtil.set("skuList",skuListJson);
        return ServerResponse.success(skuVos);
    }

    @Override
    public SkuPo skuSelectId(Long id) {
        return skuMapper.selectById(id);
    }

//    @Override
//    public void mailSku() {
//        List<SkuVos> skuVoList = skuMapper.selectSkuUser();
//
//        StringBuffer stringBuffer = new StringBuffer("<table  border='1' width='800' height='150' cellspacing='0' cellpadding='0' id='table1'><tr><td>SKU名字</td><td>价格</td><td>库存</td><td>品牌名</td><td>分类名</td></tr>");
//
//        skuVoList.forEach(x -> {
//            stringBuffer.append("<tr><td>"+x.getSkuName()+"</td><td>"+x.getPrice()+"</td><td>"+x.getStock()+"</td><td>"+x.getBrandName()+"</td><td>"+x.getCateName()+"</td></tr>");
//        });
//
//        stringBuffer.append("</table>");
//
//        List<User> userList = userMapper.selectList(null);
//
//        userList.forEach(x -> {
//            mailUtils.sendMail(x.getMail(),"库存警告-"+x.getUserName(),stringBuffer.toString());
//        });
//    }
}
