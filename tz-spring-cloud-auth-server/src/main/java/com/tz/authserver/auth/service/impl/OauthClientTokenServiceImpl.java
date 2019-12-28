package com.tz.authserver.auth.service.impl;

import com.tz.authserver.auth.entity.OauthClientToken;
import com.tz.authserver.auth.mapper.OauthClientTokenMapper;
import com.tz.authserver.auth.service.IOauthClientTokenService;
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
public class OauthClientTokenServiceImpl extends ServiceImpl<OauthClientTokenMapper, OauthClientToken> implements IOauthClientTokenService {

}
