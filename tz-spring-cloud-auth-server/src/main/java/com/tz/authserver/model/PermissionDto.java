package com.tz.authserver.model;

import lombok.Data;

/**
 * @author cxt
 */
@Data
public class PermissionDto {

    private String id;
    private String code;
    private String description;
    private String url;
}
