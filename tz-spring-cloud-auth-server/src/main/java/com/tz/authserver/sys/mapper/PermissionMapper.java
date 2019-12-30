package com.tz.authserver.sys.mapper;

import com.tz.authserver.sys.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 用户权限表 Mapper 接口
 * </p>
 *
 * @author tz
 * @since 2019-12-30
 */

@Repository
public interface PermissionMapper extends BaseMapper<Permission> {
    /**
     * 根据用户id获取用户权限
     * @param userId
     * @return
     */
    List<String> findPermissionsByUserId(Long userId);

}
