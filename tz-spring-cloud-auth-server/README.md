# oauth-server (oauth2.0)
## 授权码模式
* 引入pom依赖
```
 <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-freemarker</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <!--   断路器     -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
        </dependency>

        <dependency>
            <groupId>com.netflix.hystrix</groupId>
            <artifactId>hystrix-javanica</artifactId>
        </dependency>

        <!--  ribbon     -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
        </dependency>

        <!--   feign     -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>

        <!-- 重试机制       -->
        <dependency>
            <groupId>org.springframework.retry</groupId>
            <artifactId>spring-retry</artifactId>
        </dependency>

        <!--   健康检查     -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-commons</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-security</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-jwt</artifactId>
        </dependency>

        <dependency>
            <groupId>javax.interceptor</groupId>
            <artifactId>javax.interceptor-api</artifactId>
            <version>1.2</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>

        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
```
* 配置AuthorizationServer服务，实现AuthorizationServerConfigurerAdapter
```java
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
     * B 令牌访问端点，即url
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
     * C 配置令牌端点 安全约束
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                // /oauth/token_key  url 公开
                .tokenKeyAccess("permitAll()")
                // /oauth/check_token 公开
                .checkTokenAccess("permitAll()")
                // 表单验证，申请令牌
                .allowFormAuthenticationForClients();
    }
    /**
     * D 定义tokenServices
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices();
        services.setClientDetailsService(clientDetailsService);
        services.setSupportRefreshToken(true);
        services.setTokenStore(tokenStore);
        // 令牌默认有限期2小时
        services.setAccessTokenValiditySeconds(7200);
        // 刷新令牌默认有限期3天
        services.setRefreshTokenValiditySeconds(259200);
        return services;
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
}
```
* 定义令牌存储方式
```java
@Configuration
public class TokenConfig {
    /**
     *     令牌存储测试
     */
    @Bean
    public TokenStore tokenStore(){
        // 内存方式，普通令牌
        return new InMemoryTokenStore();
    }
}
```
* 定义web配置，拦截的url等
```java
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 认证管理器
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }

    /**
     * 密码编码器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * D 安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf().disable()
                // 开启验证
                .authorizeRequests()
                // 访问/r/r1需要p1
                .antMatchers("/r/r1").hasAnyAuthority("p1")
                // login* 不需要拦截
                .antMatchers("/login*").permitAll()
                // 其他的url都需要拦截
                .anyRequest().authenticated()
                .and()
                // 支持表单登录
                .formLogin();
    }
}
```
* test生成BCrypt密码
```java

@SpringBootTest
public class AuthServerApplicationTests {

    @Test
    public void contextLoads() {
    }
    @Test
    public void createBCryptPw(){
        String hashpw = BCrypt.hashpw("123", BCrypt.gensalt());
    }

}

```
* 获取验证码，浏览器访问 get 
http://127.0.0.1:8762/auth/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_uri=http://www.baidu.com
* post请求 获取授权码，更改code 为上面url返回的验证码
http://127.0.0.1:8762/auth/oauth/token?client_id=c1&client_secret=secret&grant_type=authorization_code&code=BzdODc&redirect_uri=http://www.baidu.com

## 简化模式（单页面应用）浏览器访问get
http://127.0.0.1:8762/auth/oauth/authorize?client_id=c1&response_type=token&scope=all&redirect_uri=http://www.baidu.com
登录成功之后会返回token，适合没有服务端的应用
https://www.baidu.com/#access_token=2042dddd-726b-4104-a533-9a7f85cab00f&token_type=bearer&expires_in=7199

## 密码模式 post
http://127.0.0.1:8762/auth/oauth/token?client_id=c1&client_secret=secret&grant_type=password&username=tz&password=123&redirect_uri=http://www.baidu.com

## 客户端模式 (没有刷新令牌，内部应用) post
http://127.0.0.1:8762/auth/oauth/token?client_id=c1&client_secret=secret&grant_type=client_credentials 

## 后续见client README第一部分

## jwt jdbc
## 授权服务器jwt配置
* tokenConfig配置jwt
```java
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
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
```
* tokenService 注入accessTokenConverter进去
```
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
```
* 现在就可以使用jwt令牌了
## 资源服务器配置jwt自己认证
* 定义tokenStore
```
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
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
```
* ResourceServerConfig校验更改
```
    // 注入tokenStore
    @Autowired
    private TokenStore tokenStore;
```
* 配置资源验证服务
 ```
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
                // 资源id
                .resourceId(RESOURCES_ID)
                .tokenStore(tokenStore)
                // 验证令牌的服务
//                .tokenServices(tokenServices())
                .stateless(true);
    }
```

## jdbc 保存详情
* mysql配置客户端详情表以及授权码表，sql见resources/db/clientDetail.sql

* auth server 配置客户端详情
```
 @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
        // 内存模式配置 start
//        clients.inMemory()
//                // 客户端id
//                .withClient("c1")
//                // 客户端密钥
//                .secret(new BCryptPasswordEncoder().encode("secret"))
//                // 资源列表
//                .resourceIds("res1")
//                // 该client允许的授权类型，
//                .authorizedGrantTypes("authorization_code","password","client_credentials",
//                        "implicit","refresh_token")
//                // 允许的授权范围，就是一个标识，read，write
//                .scopes("all")
//                .autoApprove(false)
//                // 验证回调地址
//                .redirectUris("http://www.baidu.com");
        // 内存模式配置 end

    }
```
* 注册clientDetailBean
```
   /**
     * 配置jdbc保存客户端详情的配置1
     * @param dataSource
     * @return
     */
    @Bean
    public ClientDetailsService clientDetailsService(DataSource dataSource){
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService)clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }
```
* 授权码修改为保存到数据库
```

    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
    }
```
* 到此，jdbc保存授权客户端信息就结束了
