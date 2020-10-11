package com.tz.authserver.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tz.authserver.sys.entity.User;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 当前系统用户 Mapper 接口
 * </p>
 *
 * @author tz
 * @since 2019-12-30
 */

@Repository
public interface UserMapper extends BaseMapper<User> {



}
