package com.tz.authserver.auth.service.impl;

import com.tz.authserver.auth.entity.OauthRefreshToken;
import com.tz.authserver.auth.mapper.OauthRefreshTokenMapper;
import com.tz.authserver.auth.service.IOauthRefreshTokenService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tz
 * @since 2019-12-26
 */
@Service
public class OauthRefreshTokenServiceImpl extends ServiceImpl<OauthRefreshTokenMapper, OauthRefreshToken> implements IOauthRefreshTokenService {

}
