package com.grays2.system.domain.model;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginUser {
    @TableId
    @ApiModelProperty("用户编号")
    private Integer userId;
    @ApiModelProperty("角色账号")
    private String username;
    @ApiModelProperty("角色密码")
    private String password;
    @ApiModelProperty("盐")
    private String salt;
    @ApiModelProperty("账号状态：0禁用1正常")
    private Integer status;
}
