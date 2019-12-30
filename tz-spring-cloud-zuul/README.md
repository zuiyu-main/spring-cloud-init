# zuul 路由拦截
* 请求时注意添加前缀/zuul,此时请求token url变为这样
* http://127.0.0.1:8763/zuul/auth/oauth/token?client_id=c1&client_secret=secret&username=tz&password=123&grant_type=password