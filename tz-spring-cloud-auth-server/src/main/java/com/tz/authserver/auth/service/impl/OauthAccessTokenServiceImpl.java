package com.tz.authserver.auth.service.impl;

import com.tz.authserver.auth.entity.OauthAccessToken;
import com.tz.authserver.auth.mapper.OauthAccessTokenMapper;
import com.tz.authserver.auth.service.IOauthAccessTokenService;
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
public class OauthAccessTokenServiceImpl extends ServiceImpl<OauthAccessTokenMapper, OauthAccessToken> implements IOauthAccessTokenService {

}
