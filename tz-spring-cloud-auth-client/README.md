# 第一部分  start 
# 资源访问测试
url: get 
http://127.0.0.1:8881/client/r1
请求头： key = Authorization,value = Bearer + token 
## 配置认证url
```java
@SpringBootConfiguration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    private static final String RESOURCES_ID = "res1";

    public ResourceServerConfig() {
        super();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                // 资源id
                .resourceId(RESOURCES_ID)
                // 验证令牌的服务
                .tokenServices(tokenServices())
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('all')")
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    /**
     * 令牌验证
     */
    @Bean
    public ResourceServerTokenServices tokenServices(){
        // 使用远程服务请求校验token，指定校验token的url，client_id，client_secret
        RemoteTokenServices remoteTokenServices = new RemoteTokenServices();
        remoteTokenServices.setCheckTokenEndpointUrl("http://127.0.0.1:8888/auth/oauth/check_token");
        remoteTokenServices.setClientId("c1");
        remoteTokenServices.setClientSecret("secret");
        return remoteTokenServices;
    }
}
```
## 要想资源拦截验证生效，需要配置安全拦截机制
```java
@SpringBootConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                // 拦截所有/r/**请求
                .antMatchers("/r/**").authenticated()
                // 其他放行
                .anyRequest().permitAll();
    }
}
```
# 第一部分 end
