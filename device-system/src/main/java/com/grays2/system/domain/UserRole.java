package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户与角色对应关系(SysUserRole)表实体类
 */


@Data
@TableName("sys_user_role")
@Api(value = "用户角色实体", tags = {"用户角色实体"})
public class UserRole {
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("user_id")
    private Integer userId;
    @ApiModelProperty("role_id")
    private Integer roleId;

}
