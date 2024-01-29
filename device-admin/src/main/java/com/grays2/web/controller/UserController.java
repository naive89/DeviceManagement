package com.grays2.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.grays2.common.result.R;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.UserBo;
import com.grays2.system.domain.model.LoginTempInfo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.domain.vo.UserVo;
import com.grays2.system.others.perm.PermInter;
import com.grays2.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Api(value = "用户管理", tags = {"用户管理"})
@RequestMapping(value = "/user", method = {RequestMethod.POST, RequestMethod.GET})
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @ApiOperation("用户列表")
    @RequestMapping("/getList")
    public R<PageVo<UserVo>> getList(@RequestBody PageBo pageBo) {
        return R.ok(userService.getList(pageBo));
    }

    @ResponseBody
    @ApiOperation("在线用户列表")
    @RequestMapping("/Online/getList")
    @PermInter(perm = ":user:Online:getList", name = "在线用户列表", jsjb = "1")
    public R<PageVo<LoginTempInfo>> getOnlineList(@RequestBody PageBo pageBo) {
        return R.ok(userService.getOnlineList(pageBo));
    }

    @ResponseBody
    @ApiOperation("搜索用户列表-支持模糊查询")
    @RequestMapping("/getList/search/{username}")
    public R<PageVo<UserVo>> selectUserLists(@PathVariable("username") String username, @RequestBody PageBo pageBo) {
        return R.ok(userService.selectUserLists(username, pageBo));
    }

    @ResponseBody
    @ApiOperation("获取用户头像")
    @RequestMapping("/userImage")
    public R<byte[]> getUserImage() {
        String account = (String) StpUtil.getLoginId();
        return R.ok(userService.getUserImage(account));
    }

    @ResponseBody
    @RequestMapping("/getInfo")
    @ApiOperation("主页回显个人信息")
    public R<Map<String, Object>> getInfo() {
        Map<String, Object> info = userService.getInfo();
        return R.ok(info);
    }

    @ResponseBody
    @RequestMapping("/getInfoData")
    @ApiOperation("个人中心回显个人信息")
    public R<Map<String, Object>> getInfoData() {
        Map<String, Object> info = userService.getInfoData();
        return R.ok(info);
    }

    @ResponseBody
    @ApiOperation("改变用户状态")
    @RequestMapping("/ChangeStatus")
    @PermInter(perm = ":user:ChangeStatus", name = "改变用户状态", jsjb = "2")
    public R<String> ChangeStatus(@RequestBody UserBo bo) {
        return userService.ChangeStatus(bo);
    }

    @ResponseBody
    @ApiOperation("批量删除")
    @RequestMapping("/deleteList")
    public R<String> deleteList(@RequestBody List<UserBo> userList) {
        userService.deleteList(userList);
        return R.ok("删除成功");
    }

    @ResponseBody
    @ApiOperation("删除")
    @RequestMapping("/delete")
    public R<String> delete(@RequestBody UserBo bo) {
        return userService.delete(bo);
    }

    @ResponseBody
    @ApiOperation("添加用户")
    @RequestMapping(path = "/insert")
    public R<String> insert(@RequestBody UserBo bo) {
        return userService.insert(bo);
    }

    @ResponseBody
    @ApiOperation("修改密码")
    @RequestMapping(path = "/updatePwd")
    public R<String> updatePwd(@RequestBody UserBo bo) {
        return userService.updatePwd(bo);
    }

    @ResponseBody
    @ApiOperation("编辑用户信息")
    @RequestMapping(path = "/updateUserInfo")
    public R<String> updateUserInfo(@RequestBody UserBo bo) {
        return userService.updateUserInfo(bo);
    }

}
