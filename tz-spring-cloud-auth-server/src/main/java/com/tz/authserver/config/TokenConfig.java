package com.tz.authserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

/**
 * @author tz
 * @Classname TokenConfig
 * @Description
 * @Date 2019-12-24 16:16
 */
@Configuration
public class TokenConfig {
    /**
     * jwt
     * 对称加密key
     */
    private static final String SIGNING_KEY = "tz_admin";
    /**
     *     令牌存储测试
     */
    @Bean
    public TokenStore tokenStore(){
        // 内存方式，普通令牌
//        return new InMemoryTokenStore();
        // jwt
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        // 对称密钥，资源服务器使用该密钥验证
//        converter.setSigningKey(SIGNING_KEY);
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("certs/zuiyu.jks"), "123456".toCharArray());
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("zuiyu"));
        return converter;
    }
}
