package com.grays2.system.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
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


public class UserVo {
    @TableId
    @ApiModelProperty("用户编号")
    private Integer userId;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("角色密码")
    private String password;
    @ApiModelProperty("角色名字")
    private String name;
    @ApiModelProperty("加密盐值")
    private String salt;
    @ApiModelProperty("头像")
    @TableField(exist = false)
    private String image;
    @ApiModelProperty("头像")
    private Integer imageId;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("账号状态：0禁用1正常")
    private Integer status;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
    @ApiModelProperty("描述信息")
    private String remark;
    @TableField(exist = false)
    private String roleName;
    @TableField(exist = false)
    private Integer access;
}
