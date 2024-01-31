package com.grays2.web.controller.sys;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import cn.dev33.satoken.stp.StpUtil;
import com.grays2.common.result.R;
import com.grays2.system.domain.Login;
import com.grays2.system.domain.bo.LoginBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.UserBo;
import com.grays2.system.domain.model.LoginTempInfo;
import com.grays2.system.domain.vo.LoginVo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.service.LoginService;
import com.grays2.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SaCheckLogin
@RestController
@Api(value = "登录管理", tags = {"登录管理"})
@RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @ApiOperation("登录")
    @RequestMapping("/")
    public R<Object> doLogin(@RequestBody LoginBo bo) {
        return loginService.doLogin(bo);
    }

    @ResponseBody
    @ApiOperation("注册")
    @RequestMapping("/register")
    public R<String> doRegister(@RequestBody UserBo bo) {
        return loginService.doRegister(bo);
    }

    @SaIgnore
    @ResponseBody
    @ApiOperation("退出")
    @RequestMapping("/out")
    public R<String> loginOut() {
        StpUtil.logout();
        return R.ok("退出成功");
    }

    @ResponseBody
    @ApiOperation("强制退出")
    @RequestMapping("/forcedOut/{username}")
    public R<PageVo<LoginTempInfo>> forcedOut(@PathVariable("username") String username, @RequestBody PageBo pageBo) {
        StpUtil.logout(username);
        PageVo<LoginTempInfo> list = userService.getOnlineList(pageBo);
        return R.ok("强制退出成功", list);
    }

    @ResponseBody
    @ApiOperation("主页回显登录记录")
    @RequestMapping("/getList/Index")
    public R<List<LoginVo>> getListIndex() {
        return R.ok(loginService.getListIndex());
    }

    @ResponseBody
    @RequestMapping("/getList")
    public R<PageVo<LoginVo>> getLists(@RequestBody PageBo pageBo) {
        return R.ok(loginService.getList(pageBo));
    }
}
