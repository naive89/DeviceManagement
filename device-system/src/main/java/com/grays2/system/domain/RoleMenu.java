package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sys_role_menu")
@Api(value = "角色跟菜单对应实体", tags = {"角色跟菜单对应实体"})
public class RoleMenu {
    @TableId
    @ApiModelProperty("id")
    private Integer id;
    @ApiModelProperty("菜单ID")
    private Integer menuId;
    @ApiModelProperty("角色级别")
    private Integer access;
    @ApiModelProperty("对应关系")
    private String relationship;
}
