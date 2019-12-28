package com.tz.authserver.auth.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author tz
 * @since 2019-12-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("oauth_refresh_token")
@ApiModel(value="OauthRefreshToken对象", description="")
public class OauthRefreshToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String tokenId;

    private String token;

    private String authentication;


}
