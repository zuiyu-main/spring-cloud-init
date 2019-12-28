package com.tz.authserver.auth.service;

import com.tz.authserver.auth.entity.OauthAccessToken;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tz
 * @since 2019-12-26
 */
@Repository
public interface IOauthAccessTokenService extends IService<OauthAccessToken> {

}
