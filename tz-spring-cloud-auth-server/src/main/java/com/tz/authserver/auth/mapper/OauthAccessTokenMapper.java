package com.tz.authserver.auth.mapper;

import com.tz.authserver.auth.entity.OauthAccessToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tz
 * @since 2019-12-26
 */
@Repository
@Mapper
public interface OauthAccessTokenMapper extends BaseMapper<OauthAccessToken> {

}
