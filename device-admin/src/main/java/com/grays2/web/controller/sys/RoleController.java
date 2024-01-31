package com.grays2.web.controller.sys;

import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;
import com.grays2.system.domain.Role;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.RoleBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.others.perm.PermInter;
import com.grays2.system.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "角色管理", tags = {"角色管理"})
@RequestMapping(value = "/role", method = {RequestMethod.POST, RequestMethod.GET})
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ResponseBody
    @ApiOperation("角色列表-分页接口")
    @RequestMapping("/getList")
    public R<PageVo<Role>> getList(@RequestBody PageBo pageBo) {
        return R.ok(roleService.getList(pageBo));
    }

    @ResponseBody
    @ApiOperation("角色列表")
    @RequestMapping("/getRoleKindList")
    public R<List<Role>> getRoleKindList() {
        return R.ok(roleService.getRoleKindList());
    }

    @ResponseBody
    @ApiOperation("搜索角色列表")
    @RequestMapping("/getList/search")
    public R<PageVo<Role>> getListSearch(@MultiRequestBody RoleBo roleBo, @MultiRequestBody PageBo pageBo) {
        return R.ok(roleService.getListSearch(roleBo, pageBo));
    }

    @ResponseBody
    @ApiOperation("添加角色")
    @RequestMapping("/insert")
    @PermInter(perm = ":role:insert", name = "添加角色", jsjb = "1")
    public R<PageVo<Role>> insert(@MultiRequestBody RoleBo roleBo, @MultiRequestBody PageBo pageBo) {
        return roleService.insert(roleBo, pageBo);
    }

    @ResponseBody
    @ApiOperation("修改角色")
    @RequestMapping("/update")
    @PermInter(perm = ":role:update", name = "修改角色", jsjb = "1")
    public R<PageVo<Role>> update(@MultiRequestBody RoleBo roleBo, @MultiRequestBody PageBo pageBo) {
        return R.ok("修改成功", roleService.update(roleBo, pageBo));
    }

    @ResponseBody
    @ApiOperation("删除角色")
    @RequestMapping("/delete")
    @PermInter(perm = ":role:delete", name = "删除角色", jsjb = "1")
    public R<PageVo<Role>> delete(@MultiRequestBody RoleBo roleBo, @MultiRequestBody PageBo pageBo) {
        return R.ok("删除成功", roleService.delete(roleBo, pageBo));
    }
}
