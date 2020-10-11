package com.tz.authserver.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tz.authserver.auth.entity.OauthClientDetails;
import com.tz.authserver.auth.mapper.OauthClientDetailsMapper;
import com.tz.authserver.auth.service.IOauthClientDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tz
 * @since 2019-12-30
 */
@Service
public class OauthClientDetailsServiceImpl extends ServiceImpl<OauthClientDetailsMapper, OauthClientDetails> implements IOauthClientDetailsService {

}
