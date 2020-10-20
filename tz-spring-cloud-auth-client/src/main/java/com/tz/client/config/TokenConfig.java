package com.tz.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.*;

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
        Resource resource = new ClassPathResource("certs/public_key.txt");
        String publicKey = "";
        try {
            publicKey = InputStream2String(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }
    public static String InputStream2String(InputStream in) {
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(in, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader br = new BufferedReader(reader);
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
