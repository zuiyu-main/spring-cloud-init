package com.tz.authserver.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author tz
 * @Classname UserDto
 * @Description
 * @Date 2019-12-25 16:08
 */
@Data
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String fullname;
}
