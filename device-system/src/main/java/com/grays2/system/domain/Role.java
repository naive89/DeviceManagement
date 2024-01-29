package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_role")
@Api(value = "角色实体", tags = {"角色实体"})
public class Role {
    @TableId
    @ApiModelProperty("编号")
    private Integer roleId;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色权限(1最高)")
    private Integer access;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建者ID")
    private Integer createUserId;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
    @ApiModelProperty("权限需要的活跃度")
    private Double activation;
    @ApiModelProperty("是否可以通过活跃度获取到此权限")
    private String byAct;
}
