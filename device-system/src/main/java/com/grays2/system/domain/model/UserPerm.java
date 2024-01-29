package com.grays2.system.domain.model;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 账号和权限关联表
 */
@Data
@TableName("sys_user_perm")
@Api(value = "账号和权限关联实体", tags = {"账号和权限关联实体"})
public class UserPerm {
    @TableId
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("账号")
    private String zh;

    @ApiModelProperty("权限")
    private String perm;
}
