package com.grays2.system.domain.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录记录
 * (SysLogin)表实体类
 */
@Data
@Api(value = "登录实体类", tags = {"登录实体类"})
@TableName("sys_login")
public class LoginBo {
    @ApiModelProperty(value = "账号", required = true)
    private String account;
    @ApiModelProperty(value = "密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)
    private String code;
}

