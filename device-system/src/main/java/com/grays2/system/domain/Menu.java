package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.grays2.common.typeHandler.ListToVarcharTypeHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "sys_menu", autoResultMap = true)
@Api(value = "菜单实体", tags = {"菜单实体"})
public class Menu {
    @TableId
    @ApiModelProperty("编号")
    private Integer menuId;
    @ApiModelProperty("父菜单ID，一级菜单为0")
    private Integer parentId;
    @ApiModelProperty("菜单名称")
    private String name;
    @ApiModelProperty("菜单URL")
    private String url;
    @ApiModelProperty("授权")
    @TableField(typeHandler = ListToVarcharTypeHandler.class)
    private List<String> perms;
    @ApiModelProperty("授权类型")
    private String permsType;
    @ApiModelProperty("角色列表")
    @TableField(typeHandler = ListToVarcharTypeHandler.class)
    private List<String> role;
    @ApiModelProperty("角色设置")
    private String roleType;
    @ApiModelProperty("类型   0：目录   1：菜单   2：按钮   3：其他")
    private Integer type;
    @ApiModelProperty("菜单图标")
    private String icon;
    @ApiModelProperty("排序")
    private Integer orderNum;
    @ApiModelProperty("权限")
    private Integer access;
    @TableField(exist = false)
    private List<Menu> children;
    @TableField(exist = false)
    private String parentName;
}
