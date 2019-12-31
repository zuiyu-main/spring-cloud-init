package com.tz.client.model;

import lombok.Data;

/**
 * @author tz
 * @Classname UserDto
 * @Description
 * @Date 2019-12-31 10:13
 */
@Data
public class UserDto {
    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String fullname;
}
