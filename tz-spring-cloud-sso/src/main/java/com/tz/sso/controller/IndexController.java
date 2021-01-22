//package com.tz.sso.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.Map;
//
///**
// * @author tz
// * @Classname IndexController
// * @Description
// * @Date 2020-01-02 15:41
// */
//@RestController
//public class IndexController {
//    @Autowired
//    RestTemplate restTemplate;
//
//    @RequestMapping("/index")
//    public String loginSuccess(HttpServletRequest request) {
//        Map<String, String[]> parameterMap = request.getParameterMap();
//        String code = request.getParameter("code");
//        String state = request.getParameter("state");
//        System.out.println(code);
//        System.out.println(state);
//        // 根据code申请token
//        // 此处可跳转网页，路由等，有前端发起请求token
//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("code", code);
//        map.add("client_id", "sso1");
//        map.add("client_secret", "secret");
//        map.add("redirect_uri", "http://localhost:8780/sso/index");
//        map.add("grant_type", "authorization_code");
//        Map<String,String> resp = restTemplate.postForObject("http://localhost:8763/zuul/auth/oauth/token", map, Map.class);
//        String access_token = resp.get("access_token");
//        System.out.println(access_token);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + access_token);
//        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);
//        ResponseEntity<String> entity = restTemplate.exchange("http://localhost:8763/zuul/client/r/r1", HttpMethod.GET, httpEntity, String.class);
//        return entity.getBody();
//    }
//
//    @GetMapping("/hello")
//    public String hello(){
//        return "hello zuiyu!";
//    }
//}
