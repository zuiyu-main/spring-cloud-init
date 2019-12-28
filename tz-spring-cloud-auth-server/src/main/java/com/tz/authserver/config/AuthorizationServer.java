package com.tz.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Set;

/**
 * @author tz
 * @Classname AuthorizationServer
 * @Description 授权服务器设置
 * @Date 2019-12-19 15:40
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    /**
     * 令牌存储
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * 客户端管理
     */
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    public AuthorizationServer() {
        super();
    }

    /**
     * 配置令牌端点 安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // /oauth/token_key  url 公开
                .tokenKeyAccess("permitAll()")
                // /ouath/check_token 公开
                .checkTokenAccess("permitAll()")
                // 表单验证，申请令牌
                .allowFormAuthenticationForClients();
    }

    /**
     * A 配置客户端详情，支持哪些客户端
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                // 客户端id
                .withClient("c1")
                // 客户端密钥
                .secret(new BCryptPasswordEncoder().encode("secret"))
                // 资源列表
                .resourceIds("res1")
                // 该client允许的授权类型，
                .authorizedGrantTypes("authorization_code","password","client_credentials",
                        "implicit","refresh_token")
                // 允许的授权范围，就是一个标识，read，write
                .scopes("all")
                .autoApprove(false)
                // 验证回调地址
                .redirectUris("http://www.baidu.com");
    }

    /**
     * 设置授权码如何存储
     * @param
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }
//    @Bean
//    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
//        return new JdbcAuthorizationCodeServices(dataSource);
//    }
    /**
     * C 令牌访问端点，即url
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 密码模式需要
                .authenticationManager(authenticationManager)
                // 授权码模式需要
                .authorizationCodeServices(authorizationCodeServices)
                // 令牌管理服务
                .tokenServices(tokenServices())
                //允许post提交
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * B
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        // 令牌增强 加入tokenConvert start
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);
        // 加入tokenConvert end
        // 令牌默认有限期2小时
        services.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有限期3天
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }


}
