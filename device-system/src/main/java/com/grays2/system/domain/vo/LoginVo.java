package com.grays2.system.domain.vo;

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
@Api(value = "登录实体类", tags = {"登录实体类"})
@TableName("sys_login")
public class LoginVo {
    @ApiModelProperty(value = "编号", required = true)
    private Integer id;
    @ApiModelProperty(value = "账号", required = true)
    private String account;
    @ApiModelProperty(value = "用户名", required = true)
    private String username;
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "登录时间", required = true)
    private LocalDateTime time;
    @ApiModelProperty(value = "登录地址", required = true)
    private String ip;
    @ApiModelProperty("请求来源")
    private String innerIp;
}

