package com.grays2.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.common.result.R;
import com.grays2.common.utils.StringUtils;
import com.grays2.system.domain.Menu;
import com.grays2.system.domain.Role;
import com.grays2.system.domain.RoleMenu;
import com.grays2.system.domain.bo.MenuBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.mapper.MenuMapper;
import com.grays2.system.mapper.RoleMapper;
import com.grays2.system.mapper.RoleMenuMapper;
import com.grays2.system.service.DictService;
import com.grays2.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    List<Integer> updateParentIds = new ArrayList<>();
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private DictService dictService;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    /**
     * 侧边栏动态获取菜单，依据用户权限
     *
     */
    @Override
    public List<Menu> getAsideMenus() {
        String account = (String) StpUtil.getLoginId();
        //获取用户的权限（既角色信息）
        Role role = roleMapper.getRoleByAccount(account);
        //角色权限
        int access = role.getRoleId();
        List<Menu> menus;
        //查询缓存是否存在
        String key = RedisConstant.getList_Menu_aside;
        if (redisUtils.hasKey(key + ":" + role.getAccess())) {
            //缓存存在
            menus = (List<Menu>) redisUtils.get(key + ":" + role.getAccess());
        } else {
            menus = getMenuList().stream().filter(o -> o.getAccess() >= access).collect(Collectors.toList());

            LambdaQueryWrapper<RoleMenu> lqw = new LambdaQueryWrapper<>();
            lqw.eq(RoleMenu::getAccess, access);
            List<RoleMenu> rmList = roleMenuMapper.selectList(lqw);
            if (StringUtils.isNotEmpty(rmList)) {
                List<Integer> deleteList = rmList.stream().filter(o -> "DELETE".equals(o.getRelationship())).collect(Collectors.toList()).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(deleteList)) {
                    List<Menu> deletes = menuMapper.getMenuLists(deleteList);
                    // menus和deletes的差集（menus - deletes）
                    menus = menus.stream().filter(item -> !deletes.contains(item)).collect(Collectors.toList());
                }
                List<Integer> insertList = rmList.stream().filter(o -> "INSERT".equals(o.getRelationship())).collect(Collectors.toList()).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(insertList)) {
                    List<Menu> inserts = menuMapper.getMenuLists(insertList);
                    // menus和inserts并集（去重）
                    menus.addAll(inserts);
                    menus = menus.stream().distinct().collect(Collectors.toList());
                }
            }
            for (Menu i : menus) {
                List<Menu> list = menus.stream().filter(
                        menu -> Objects.equals(menu.getParentId(), i.getMenuId())
//                            && menu.getAccess() <= i.getAccess()
                                && (1 == menu.getType())
                ).collect(Collectors.toList());
                if (!list.isEmpty()) {
                    list.sort(Comparator.comparing(Menu::getOrderNum));
                    i.setChildren(list);
                }
            }
            menus = menus.stream().filter(o -> o.getParentId() == 0).collect(Collectors.toList());
            menus.sort(Comparator.comparing(Menu::getOrderNum));
            long time = RedisConstant.getList_Menu_aside_Time;
            redisUtils.set(key + ":" + role.getAccess(), menus, time);
        }
        return menus;
    }

    //获取所有菜单列表-list<Menu>格式
    private List<Menu> getMenuList() {
        return menuMapper.getMenuList();
    }

    @Override
    public List<Integer> getMenuListByRole(Integer access) {
        List<Menu> menus = getMenuList().stream().filter(o -> o.getAccess() >= access).collect(Collectors.toList());
        List<Integer> list = menus.stream().map(Menu::getMenuId).collect(Collectors.toList());

        LambdaQueryWrapper<RoleMenu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(RoleMenu::getAccess, access);
        List<RoleMenu> rmList = roleMenuMapper.selectList(lqw);
        List<Integer> insertList = rmList.stream().filter(o -> "INSERT".equals(o.getRelationship())).collect(Collectors.toList()).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        List<Integer> deleteList = rmList.stream().filter(o -> "DELETE".equals(o.getRelationship())).collect(Collectors.toList()).stream().map(RoleMenu::getMenuId).collect(Collectors.toList());


        // list和deleteList的差集（list - deleteList）
        list = list.stream().filter(item -> !deleteList.contains(item)).collect(Collectors.toList());
        // list和insertList并集（去重）
        list.addAll(insertList);
        list = list.stream().distinct().collect(Collectors.toList());


        return list;
    }

    @Override
    public R<String> changeMenuAccessByRole(List<Integer> newPower, List<Integer> oldPower, Integer access) {
        List<Integer> insertList = newPower.stream().filter(item -> !oldPower.contains(item)).collect(Collectors.toList());
        List<Integer> deleteList = oldPower.stream().filter(item -> !newPower.contains(item)).collect(Collectors.toList());
        if (StringUtils.isNotEmpty(insertList)) {
            List<RoleMenu> list = insertList.stream().map(i -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setAccess(access);
                roleMenu.setMenuId(i);
                roleMenu.setRelationship("INSERT");
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuMapper.deleteList(list);
            roleMenuMapper.insertList(list);
        }
        if (StringUtils.isNotEmpty(deleteList)) {
            List<RoleMenu> list = deleteList.stream().map(i -> {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setAccess(access);
                roleMenu.setMenuId(i);
                roleMenu.setRelationship("DELETE");
                return roleMenu;
            }).collect(Collectors.toList());
            roleMenuMapper.deleteList(list);
            roleMenuMapper.insertList(list);
        }
        checkRoleMenu();
        return R.ok("操作成功");
    }

    @Async
    public void checkRoleMenu() {
        List<RoleMenu> menus = roleMenuMapper.selectList(new LambdaQueryWrapper<>());
        if (StringUtils.isNotEmpty(menus)) {
            List<RoleMenu> inserts = menus.stream().filter(o -> "INSERT".equals(o.getRelationship())).collect(Collectors.toList());
            List<RoleMenu> deletes = menus.stream().filter(o -> "DELETE".equals(o.getRelationship())).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(inserts) || StringUtils.isNotEmpty(deletes)) {
                List<Menu> menuList = menuMapper.getMenuList();

                //检查权限本有可以访问菜单，却又添加了数据
                if (StringUtils.isNotEmpty(inserts) && menuList != null) {
                    List<RoleMenu> insertList = inserts.stream().filter(o -> StringUtils.isNotEmpty(
                            menuList.stream().filter(menu -> Objects.equals(o.getMenuId(), menu.getMenuId()) && menu.getAccess() >= o.getAccess()
                            ).collect(Collectors.toList())
                    )).collect(Collectors.toList());

                    //删除数组
                    if (StringUtils.isNotEmpty(insertList))
                        roleMenuMapper.deleteList(insertList);
                }

                //检查权限本就不可以访问菜单，却又添加了数据
                if (StringUtils.isNotEmpty(deletes) && menuList != null) {
                    List<RoleMenu> deleteList = inserts.stream().filter(o -> StringUtils.isNotEmpty(
                            menuList.stream().filter(menu -> Objects.equals(o.getMenuId(), menu.getMenuId()) && menu.getAccess() < o.getAccess()
                            ).collect(Collectors.toList())
                    )).collect(Collectors.toList());

                    //删除数组
                    if (StringUtils.isNotEmpty(deleteList))
                        roleMenuMapper.deleteList(deleteList);
                }
            }

        }
    }


    /**
     * 获取父菜单
     */
    @Override
    public List<Menu> listParent() {
        return (List<Menu>) redisUtils.get(RedisConstant.getList_MenuParent);
    }

    /**
     * 获取全部菜单
     * 采用Parent-Children模式
     */
    @Override
    public PageVo<Menu> getList(PageBo pageBo) {
        List<Menu> menus = getList();
        return new PageVo<>(pageBo, menus);
    }

    @Override
    public List<Menu> getList() {
        return (List<Menu>) redisUtils.get(RedisConstant.getList_Menu);
    }

    /**
     * 模糊查询菜单
     */
    @Override
    public PageVo<Menu> getMenusByName(MenuBo menuBo, PageBo pageBo) {
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(menuBo.getName()))
            lqw.eq(Menu::getName, menuBo.getName());
        if (StringUtils.isNotEmpty(menuBo.getUrl()))
            lqw.like(Menu::getUrl, menuBo.getUrl());
        if (StringUtils.isNotEmpty(menuBo.getParentId()))
            lqw.eq(Menu::getParentId, menuBo.getParentId());
        if (StringUtils.isNotEmpty(menuBo.getType()))
            lqw.eq(Menu::getType, menuBo.getType());
        List<Menu> menuList = menuMapper.selectList(lqw);
        return new PageVo<>(pageBo, menuList);
    }

    /**
     * 创建父菜单缓存
     */
    @Override
    public void createMenuParentRedis() {
        List<Menu> menus = getMenuList().stream().filter(o -> o.getParentId() == 0).collect(Collectors.toList());
        long time = RedisConstant.getList_MenuParent_Time;
        String key = RedisConstant.getList_MenuParent;
        redisUtils.set(key, menus, time);
    }

    /**
     * 创建菜单列表缓存
     */
    @Override
    public void createMenuRedis() {
        List<Menu> menus = getAllMenu();
        long time = RedisConstant.getList_Menu_Time;
        String key = RedisConstant.getList_Menu;
        redisUtils.set(key, menus, time);
    }

    /**
     * 获取所有的菜单
     */
    public List<Menu> getAllMenu() {
        List<Menu> menuList = getMenuList();
        List<Menu> menus = menuList.stream().filter(o -> o.getParentId() == 0).collect(Collectors.toList());
        List<Menu> menuList1 = menuList.stream().filter(o -> o.getParentId() != 0).collect(Collectors.toList());
        menus.forEach(menu -> {
            List<Menu> list = menuList1.stream().filter(
                    o -> Objects.equals(o.getParentId(), menu.getMenuId())
            ).collect(Collectors.toList());
            if (!list.isEmpty()) {
                menu.setChildren(list);
            }
        });
        return menus;
    }

    @Override
    public R<PageVo<Menu>> update(Menu menu, PageBo pageBo) {
        updateParentIds.add(menu.getParentId());
        //如果类型为目录，设置父菜单为0
        if (menu.getType() == 0) menu.setParentId(0);
        if (menu.getPerms() == null || menu.getPerms().size() < 2) menu.setPermsType("");
        if (menu.getRole() == null || menu.getRole().isEmpty()) menu.setRoleType("");
        Integer parentId = menuMapper.selectById(menu.getMenuId()).getParentId();
        if (!parentId.equals(menu.getParentId()))
            updateParentIds.add(parentId);
        menuMapper.updateById(menu);
        this.updateMenuOrderNum();
        PageVo<Menu> list = this.getList(pageBo);
        return R.ok("修改成功", list);
    }

    /**
     * 增加菜单
     */
    @Override
    public R<PageVo<Menu>> insert(MenuBo menu, PageBo pageBo) {
        //目录设置父菜单为空
        if (menu.getType() == 0) {
            menu.setParentId(0);
        }
        updateParentIds.add(menu.getParentId());
        LambdaQueryWrapper<Menu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Menu::getUrl, menu.getUrl());
        boolean exists = menuMapper.exists(lqw);
        if (exists) {
            return R.warn("菜单已存在,添加失败");
        } else {
            menuMapper.insertMenuBo(menu);
            //更新菜单列表的排序
            this.updateMenuOrderNum();
            PageVo<Menu> list = this.getList(pageBo);
            return R.ok("添加成功", list);
        }
    }

    /**
     * 更新菜单列表的排序
     */
    @Async
    public void updateMenuOrderNum() {
        updateParentIds.forEach(parentId -> {
            List<Menu> menuList = menuMapper.getMenuListByParentId(parentId);
            List<Menu> menus = menuList.stream().sorted(Comparator.comparing(Menu::getOrderNum).thenComparing(Menu::getMenuId)).collect(Collectors.toList());
            List<Menu> menuChange = new ArrayList<>();
            for (int i = 0; i < menus.size(); i++) {
                Menu menu = menus.get(i);
                if (menu.getOrderNum() != (i + 1)) {
                    Menu menu1 = new Menu();
                    menu1.setMenuId(menu.getMenuId());
                    menu1.setOrderNum(i + 1);
                    menuChange.add(menu1);
                }
            }
            if (StringUtils.isNotEmpty(menuChange)) {
                menuMapper.updateMenuList(menuChange);
            }
        });
        updateParentIds = new ArrayList<>();
        this.updateMenuRedis();
    }

    @Override
    public R<PageVo<Menu>> delete(MenuBo menuBo, PageBo pageBo) {
        this.updateParentIds.add(menuBo.getParentId());
        menuMapper.deleteById(menuBo.getMenuId());
        this.updateMenuOrderNum();
        PageVo<Menu> list = this.getList(pageBo);
        return R.ok("删除成功", list);
    }

    /**
     * 更新Menu表的缓存
     */
    @Async
    public void updateMenuRedis() {
        Set<Object> keys = redisTemplate.keys(RedisConstant.getList_Menu + "*");
        if (keys != null) {
            for (Object key : keys)
                redisUtils.del(String.valueOf(key));
        }
        createMenuRedis();
        createMenuParentRedis();
    }
}
