package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sys_perm")
@Api(value = "权限实体", tags = {"权限实体"})
public class Perm {
    @TableId
    @ApiModelProperty("权限")
    private String perm;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("需要的角色级别")
    private Integer jsjb;
}
