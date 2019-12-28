package com.tz.authserver.auth.service.impl;

import com.tz.authserver.auth.entity.OauthApprovals;
import com.tz.authserver.auth.mapper.OauthApprovalsMapper;
import com.tz.authserver.auth.service.IOauthApprovalsService;
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
public class OauthApprovalsServiceImpl extends ServiceImpl<OauthApprovalsMapper, OauthApprovals> implements IOauthApprovalsService {

}
