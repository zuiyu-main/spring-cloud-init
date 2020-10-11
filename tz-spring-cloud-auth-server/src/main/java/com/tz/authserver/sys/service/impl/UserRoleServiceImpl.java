package com.tz.authserver.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.authserver.sys.entity.UserRole;
import com.tz.authserver.sys.mapper.UserRoleMapper;
import com.tz.authserver.sys.service.IUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author tz
 * @since 2019-12-30
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

}
