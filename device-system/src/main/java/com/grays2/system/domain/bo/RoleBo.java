package com.grays2.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data


public class RoleBo {
    @TableId
    @ApiModelProperty("用户编号")
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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @ApiModelProperty("权限需要的活跃度")
    private Double activation;

}
