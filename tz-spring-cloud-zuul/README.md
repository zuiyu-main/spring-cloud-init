# zuul 路由拦截
* 请求时注意添加前缀/zuul,此时请求token url变为这样
* http://127.0.0.1:8763/zuul/auth/oauth/token?client_id=c1&client_secret=secret&username=tz&password=123&grant_type=password

# 配置微服务拦截
* ResourceServerConfig 添加服务的拦截即可
* 例如 配置资源服务
`
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
`
* 配置验证服务器访问
`

    @Configuration
    @EnableResourceServer
    public class AuthServerConfig extends ResourceServerConfigurerAdapter{
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.tokenStore(tokenStore).resourceId(RESOURCES_ID).stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/auth/**").permitAll();
        }
    }
`
* yml 对应url拦截配置
`
zuul:
  retryable: true
  ignored-services: "*"
  add-host-header: true
  sensitive-headers: "*"
  routes:
    auth-server:
      # 转发请求时是否去掉前缀
      stripPrefix: false
      path: /auth/**
    client-service:
      stripPrefix: false
      path: /client/**
`
# 自定义负载均衡策略
 [自定义负载均衡，可定义单个服务ip负载均衡](https://blog.csdn.net/ypp91zr/article/details/88312708)
 [负载均衡算法](https://blog.csdn.net/ypp91zr/article/details/88270403)