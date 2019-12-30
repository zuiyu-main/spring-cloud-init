package com.tz.authserver.sys.service.impl;

import com.tz.authserver.sys.entity.Role;
import com.tz.authserver.sys.mapper.RoleMapper;
import com.tz.authserver.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
