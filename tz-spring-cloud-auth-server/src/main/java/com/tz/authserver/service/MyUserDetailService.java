package com.tz.authserver.service;

import com.alibaba.fastjson.JSON;
import com.tz.authserver.model.UserDto;
import com.tz.authserver.sys.entity.Permission;
import com.tz.authserver.sys.mapper.PermissionMapper;
import com.tz.authserver.sys.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tz
 * @Classname MyUserDetailService
 * @Description
 * @Date 2019-12-25 16:06
 */
@Service
@Slf4j
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String,Object> params = new HashMap<>(16);
        params.put("user_name",username);
        List<com.tz.authserver.sys.entity.User> users = userMapper.selectByMap(params);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        com.tz.authserver.sys.entity.User user = users.get(0);
        //根据用户的id查询用户的权限
        List<String> permissions = permissionMapper.findPermissionsByUserId(user.getUserId());
        //将permissions转成数组
        String[] permissionArray = new String[permissions.size()];
        permissions.toArray(permissionArray);
//        //将userDto转成json
        UserDto userDto = UserDto.builder()
                .id(user.getUserId())
                .username(user.getUserName())
                .fullname(user.getFullName())
                .password(user.getPassword()).build();
        String principal = JSON.toJSONString(userDto);
        UserDetails userDetails = User.withUsername(principal).password(user.getPassword()).authorities(permissionArray).build();
//        UserDetails userDetails = User.withUsername("tz").password("$2a$10$pTffBwh9mawjeGG9K5ZhbenBfWQRV1aFVgZqVt59eM67iJhDqARyG").authorities("p1").build();
        return userDetails;
    }
}
