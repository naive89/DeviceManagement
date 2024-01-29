package com.grays2.system.domain.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserBo {
    @ApiModelProperty("账号ID")
    private Integer userId;
    @ApiModelProperty("角色账号")
    private String username;
    @ApiModelProperty("角色密码")
    private String password;
    @ApiModelProperty("角色旧密码")
    private String oldPassword;
    @ApiModelProperty("角色名字")
    private String name;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("手机号码")
    private String mobile;
    @ApiModelProperty("描述信息")
    private String remark;
}
