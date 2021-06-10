package com.fh.shop.filter;

import com.alibaba.fastjson.JSON;
import com.fh.shop.common.ResponseEnum;
import com.fh.shop.common.ServerResponse;
import com.fh.shop.po.Member;
import com.fh.shop.util.Md5Util;
import com.fh.shop.util.RedisUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class JwtFilter extends ZuulFilter {

    @Value("${fh.shop.checkUrl}")
    private List<String> checkUrls;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        StringBuffer requestURL = request.getRequestURL();

        String methods = request.getMethod();
        if (methods.equalsIgnoreCase("options")){
            currentContext.setSendZuulResponse(false);
            return null;
        }

        boolean isCheck = false;
        for (String checkUrl : checkUrls){
            if (requestURL.indexOf(checkUrl) > 0){
                isCheck = true;
                break;
            }
        }

        if (!isCheck){
            return null;
        }

        String header = request.getHeader("x-auth");
        if (StringUtils.isEmpty(header)) {
            buildResponse(currentContext, ResponseEnum.LOGIN_IS_NULL);
            return null;
        }
        String[] headerArr = header.split("\\.");
        if (headerArr.length != 2) {
            buildResponse(currentContext, ResponseEnum.TOKEN_IS_NOT_FULL);
            return null;
        }

        String memberVoJsonStringBase64 = headerArr[0];
        String signBase64 = headerArr[1];

        String memberVoJsonString = new String(Base64.getDecoder().decode(memberVoJsonStringBase64));
        String sign = new String(Base64.getDecoder().decode(signBase64));

        if (!Md5Util.md5(memberVoJsonString + "f19u3f19jf1303924hf").equals(sign)) {
            buildResponse(currentContext, ResponseEnum.TOKEN_ERROR);
            return null;
        }

        Member memberPo = JSON.parseObject(memberVoJsonString, Member.class);

        if (!RedisUtil.exists("memberName:" + memberPo.getMemberName())){
            buildResponse(currentContext, ResponseEnum.TOKEN_FULL);
            return null;
        }
        RedisUtil.expire("memberName:" + memberPo.getMemberName(),10 * 60);

        //request.setAttribute("member",memberPo);
        currentContext.addZuulRequestHeader("member",URLEncoder.encode(memberVoJsonString,"utf-8"));
        return null;
    }

    private void buildResponse(RequestContext currentContext, ResponseEnum loginIsNull) {
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        currentContext.setSendZuulResponse(false);
        ServerResponse error = ServerResponse.error(loginIsNull);
        String res = JSON.toJSONString(error);
        currentContext.setResponseBody(res);
    }
}
