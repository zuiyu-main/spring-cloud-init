package com.tz.authserver.service;

import com.alibaba.fastjson.JSON;
import com.tz.authserver.dao.UserDao;
import com.tz.authserver.model.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        //将来连接数据库根据账号查询用户信息
//        UserDto userDto = userDao.getUserByUsername(username);
//        if(userDto == null){
//            //如果用户查不到，返回null，由provider来抛出异常
//            return null;
//        }
//        //根据用户的id查询用户的权限
//        List<String> permissions = userDao.findPermissionsByUserId(userDto.getId());
//        //将permissions转成数组
//        String[] permissionArray = new String[permissions.size()];
//        permissions.toArray(permissionArray);
//        //将userDto转成json
//        String principal = JSON.toJSONString(userDto);
//        UserDetails userDetails = User.withUsername(principal).password(userDto.getPassword()).authorities(permissionArray).build();
        UserDetails userDetails = User.withUsername("tz").password("$2a$10$pTffBwh9mawjeGG9K5ZhbenBfWQRV1aFVgZqVt59eM67iJhDqARyG").authorities("p1").build();
        return userDetails;
    }
}
