package com.tz.zuul.config;

import com.tz.base.exception.AuthExceptionEntryPoint;
import com.tz.base.handle.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @author tz
 * @Classname ResourceServerConfig
 * @Description 资源设置
 * @Date 2019-12-19 15:40
 */
@Configuration
public class ResourceServerConfig  {

    /**
     * 资源id
     */
    private static final String RESOURCES_ID = "res1";


    @Configuration
    @EnableResourceServer
    public class AuthServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore)
                    .resourceId(RESOURCES_ID)
                    .authenticationEntryPoint(new AuthExceptionEntryPoint())
                    .accessDeniedHandler(new CustomAccessDeniedHandler())
                    .stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/auth/**").permitAll();
        }
    }
    @Configuration
    @EnableResourceServer
    public class ClientServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore).resourceId(RESOURCES_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/client/**").access("#oauth2.hasScope('ROLE_API')");
        }
    }
    @Configuration
    @EnableResourceServer
    public class SsoServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore).resourceId("sso1").stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/sso/**").access("#oauth2.hasScope('ROLE_API')");
        }
    }
    @Configuration
    @EnableResourceServer
    public class ClientCrawlServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore).resourceId("crawl1").stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/client/test/**").access("#oauth2.hasScope('ROLE_API')");
        }
    }

}
