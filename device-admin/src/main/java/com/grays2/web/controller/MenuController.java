package com.grays2.web.controller;

import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;
import com.grays2.system.domain.Menu;
import com.grays2.system.domain.bo.MenuBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.others.perm.PermInter;
import com.grays2.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单操作
 **/
@RestController
@Api(value = "菜单管理", tags = {"菜单管理"})
@RequestMapping(value = "/menu", method = {RequestMethod.POST, RequestMethod.GET})
public class MenuController {
    @Autowired
    private MenuService menuService;

    @ResponseBody
    @ApiOperation("侧边栏动态获取菜单")
    @RequestMapping("/asideMenus")
    public R<List<Menu>> asideMenus() {
        return R.ok(menuService.getAsideMenus());
    }

    @ResponseBody
    @ApiOperation("根据权限获取菜单列表的ID")
    @RequestMapping("/getList/ByRole/{access}")
    public R<List<Integer>> getMenuListByRole(@PathVariable("access") Integer access) {
        return R.ok(menuService.getMenuListByRole(access));
    }

    @ResponseBody
    @ApiOperation("根据权限修改菜单权限")
    @RequestMapping("/changeMenuAccessByRole")
    @PermInter(perm = ":menu:changeMenuAccessByRole", name = "根据权限修改菜单权限", jsjb = "2")
    public R<String> changeMenuAccessByRole(@MultiRequestBody List<Integer> newPower,
                                            @MultiRequestBody List<Integer> oldPower,
                                            @MultiRequestBody Integer access) {
        return menuService.changeMenuAccessByRole(newPower, oldPower, access);
    }

    @ResponseBody
    @ApiOperation("父菜单列表")
    @RequestMapping("/listParent")
    public R<List<Menu>> listParent() {
        return R.ok(menuService.listParent());
    }

    @ResponseBody
    @ApiOperation("菜单列表-分页")
    @RequestMapping("/getList")
    public R<PageVo<Menu>> getList(@RequestBody PageBo pageBo) {
        return R.ok(menuService.getList(pageBo));
    }

    @ResponseBody
    @ApiOperation("菜单列表-不分页")
    @RequestMapping("/getList/noPage")
    public R<List<Menu>> getListNoPage() {
        return R.ok(menuService.getList());
    }

    @ResponseBody
    @ApiOperation("更新菜单")
    @RequestMapping("/update")
    public R<PageVo<Menu>> update(@MultiRequestBody Menu menu, @MultiRequestBody PageBo pageBo) {
        return menuService.update(menu, pageBo);
    }

    @ResponseBody
    @ApiOperation("菜单管理的模糊查询")
    @RequestMapping("/getList/search")
    public R<PageVo<Menu>> getListSearchByName(@MultiRequestBody MenuBo menuBo, @MultiRequestBody PageBo pageBo) {
        PageVo<Menu> menusByName = menuService.getMenusByName(menuBo, pageBo);
        return R.ok(menusByName);
    }

    @ResponseBody
    @ApiOperation("添加菜单")
    @RequestMapping(path = "/insert")
    @PermInter(perm = ":menu:insert", name = "添加菜单", jsjb = "1")
    public R<PageVo<Menu>> insert(@MultiRequestBody MenuBo menuBo, @MultiRequestBody PageBo pageBo) {
        return menuService.insert(menuBo, pageBo);
    }

    @ResponseBody
    @ApiOperation("删除菜单")
    @RequestMapping("/delete")
    @PermInter(perm = ":menu:delete", name = "删除菜单", jsjb = "1")
    public R<PageVo<Menu>> delete(@MultiRequestBody MenuBo menuBo, @MultiRequestBody PageBo pageBo) {
        return menuService.delete(menuBo, pageBo);
    }
}
