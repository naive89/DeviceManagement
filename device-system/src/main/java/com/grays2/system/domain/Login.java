package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录记录
 * (SysLogin)表实体类
 */
@Data
@TableName("sys_login")
@Api(value = "登录实体", tags = {"登录实体"})
public class Login {
    @TableId
    @ApiModelProperty(value = "登录编号", required = true)
    private Integer id;
    @ApiModelProperty(value = "账号", required = true)
    private String account;
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登录时间", required = true)
    private LocalDateTime time;
    @ApiModelProperty(value = "登录地址", required = true)
    private String ip;
    @ApiModelProperty("请求来源")
    private String innerIp;
    @ApiModelProperty("设备来源")
    private String mobile;

}

