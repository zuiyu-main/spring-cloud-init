package com.tz.authserver.model;

import lombok.Data;

/**
 * @author tz
 * @Classname UserDto
 * @Description
 * @Date 2019-12-25 16:08
 */
@Data
public class UserDto {
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;
}
