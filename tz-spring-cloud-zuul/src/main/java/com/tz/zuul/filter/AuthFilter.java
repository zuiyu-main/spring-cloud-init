package com.tz.zuul.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.tz.zuul.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tz
 * @Classname AuthFilter
 * @Description 权限验证拦截器
 * @Date 2019-12-30 19:19
 */
@Slf4j
public class AuthFilter extends ZuulFilter {
    @Override
    public String filterType() {
        // 访问前拦截
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 数字越小优先级越高
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // true 启用拦截器
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("认证拦截器进入--------------->");
        RequestContext ctx = RequestContext.getCurrentContext();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof OAuth2Authentication)){
            return null;
        }
        OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) authentication;
        Authentication userAuthentication = oAuth2Authentication.getUserAuthentication();
        // 取用户信息
        String principal = userAuthentication.getName();
        // 取用户权限
        List<String> authorities = new ArrayList<>();
        // 从userAuthentication 取出权限，放在authorities
        userAuthentication.getAuthorities().parallelStream().forEach(e->authorities.add(((GrantedAuthority) e).getAuthority()));

        OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
        Map<String, String> requestParameters = oAuth2Request.getRequestParameters();
        Map<String,Object> jsonToken = new HashMap<>(requestParameters);
        if(null != userAuthentication ){
            jsonToken.put("principal",principal);
            jsonToken.put("authorities",authorities);
        }
        // 把身份信息放在请求头，转发子服务
        ctx.addZuulRequestHeader("json-token", EncryptUtil.encodeUTF8StringBase64(JSON.toJSONString(jsonToken)));
        return null;
    }
}
