package com.grays2.system.domain.model;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "在线用户实体", tags = {"在线用户实体"})
public class LoginTempInfo {
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("账号")
    private String username;
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("登录设备")
    private String mobile;
    @ApiModelProperty("登录过期时间")
    private long loginTimeout;

    public LoginTempInfo(String account, String username, String token, String mobile, long loginTimeout) {
        this.account = account;
        this.username = username;
        this.token = token;
        this.mobile = mobile;
        this.loginTimeout = loginTimeout;
    }
}
