package com.tz.authserver;


import com.tz.authserver.auth.entity.OauthClientDetails;
import com.tz.authserver.auth.mapper.OauthClientDetailsMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author tz
 * @Classname ClientDetailTest
 * @Description 客户端详情数据库添加
 * @Date 2019-12-30 10:36
 */
@SpringBootTest
//@RunWith(SpringRunner.class)
public class ClientDetailTest {
    @Autowired
    private OauthClientDetailsMapper oauthClientDetailsMapper;
    @Test
    public void createBCryptPw(){
        String gensalt = BCrypt.gensalt();
        System.out.println(gensalt);
        String hashpw = BCrypt.hashpw("123", gensalt);
        System.out.println(hashpw);
    }
    @Test
    public void addClientDetailsTest(){
        OauthClientDetails clientDetails1 = new OauthClientDetails();
        clientDetails1.setClientId("c1");
        clientDetails1.setResourceIds("res1");
        // secret
        clientDetails1.setClientSecret("$2a$10$8my0HiupMLPyZG2fosho4.Go9hk519ArmX.B27FGabCyQWuVFKnjm");
        clientDetails1.setScope("ROLE_ADMIN,ROLE_USER,ROLE_API");
        // "authorization_code","password","client_credentials", "implicit","refresh_token"
        clientDetails1.setAuthorizedGrantTypes("authorization_code,password,client_credentials,implicit,refresh_token");
        clientDetails1.setWebServerRedirectUri("http://www.baidu.com");
        clientDetails1.setCreateTime(LocalDateTime.now());
        clientDetails1.setAccessTokenValidity(7200);
        clientDetails1.setRefreshTokenValidity(259200);
        clientDetails1.setArchived(0);
        clientDetails1.setTrusted(0);
        clientDetails1.setAutoapprove("false");

        // client 2
        OauthClientDetails clientDetails2 = new OauthClientDetails();
        clientDetails2.setClientId("c2");
        clientDetails2.setResourceIds("res2");
        // secret
        clientDetails2.setClientSecret("$2a$10$8my0HiupMLPyZG2fosho4.Go9hk519ArmX.B27FGabCyQWuVFKnjm");
        clientDetails2.setScope("ROLE_API");
        // "authorization_code","password","client_credentials", "implicit","refresh_token"
        clientDetails2.setAuthorizedGrantTypes("authorization_code,password,client_credentials,implicit,refresh_token");
        clientDetails2.setWebServerRedirectUri("http://www.baidu.com");
        clientDetails2.setCreateTime(LocalDateTime.now());
        clientDetails2.setAccessTokenValidity(31536000);
        clientDetails2.setRefreshTokenValidity(2592000);
        clientDetails2.setArchived(0);
        clientDetails2.setTrusted(0);
        clientDetails2.setAutoapprove("false");
        oauthClientDetailsMapper.insert(clientDetails1);
        oauthClientDetailsMapper.insert(clientDetails2);

    }
}
