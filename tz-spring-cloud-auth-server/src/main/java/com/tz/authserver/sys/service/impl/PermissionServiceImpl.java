package com.tz.authserver.sys.service.impl;

import com.tz.authserver.sys.entity.Permission;
import com.tz.authserver.sys.mapper.PermissionMapper;
import com.tz.authserver.sys.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户权限表 服务实现类
 * </p>
 *
 * @author tz
 * @since 2019-12-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
