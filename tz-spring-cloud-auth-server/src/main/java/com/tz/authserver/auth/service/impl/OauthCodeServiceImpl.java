package com.tz.authserver.auth.service.impl;

import com.tz.authserver.auth.entity.OauthCode;
import com.tz.authserver.auth.mapper.OauthCodeMapper;
import com.tz.authserver.auth.service.IOauthCodeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class OauthCodeServiceImpl extends ServiceImpl<OauthCodeMapper, OauthCode> implements IOauthCodeService {

}
