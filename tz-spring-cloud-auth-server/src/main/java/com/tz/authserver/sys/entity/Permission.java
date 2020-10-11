package com.tz.authserver.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author tz
 * @since 2019-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
@ApiModel(value="Permission对象", description="用户权限表")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "上级权限id")
    private Long parentId;

    @ApiModelProperty(value = "资源名称")
    private String resName;

    @ApiModelProperty(value = "资源类型")
    private String resType;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "权限url")
    private String url;


}
