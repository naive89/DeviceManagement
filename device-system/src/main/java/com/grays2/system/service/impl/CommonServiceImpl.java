package com.grays2.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.grays2.common.utils.StringUtils;
import com.grays2.system.domain.*;
import com.grays2.system.mapper.*;
import com.grays2.system.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommonServiceImpl implements CommonService {


    @Autowired
    private DictMapper dictMapper;

    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private LogMapper logMapper;
    @Autowired
    private LoginMapper loginMapper;
    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Value("${dataBase.check}")
    public boolean check;
    @Value("${dataBase.create.createFriendList}")
    public boolean createFriendList;
    @Value("${dataBase.create.createScoreList}")
    public boolean createScoreList;
    @Value("${dataBase.create.createProcessList}")
    public boolean createProcessList;

    @Override
    public void checkDataBase() {
        //  =========================检查数据库=========================
        if (check) {
            //dict
            if (StringUtils.isEmpty(dictMapper.selectList(new LambdaQueryWrapper<>()))) {
                throw new RuntimeException("sys_dict表为空");
            }
            //image
            List<Image> imageList = imageMapper.selectList(new LambdaQueryWrapper<>());
            if (StringUtils.isEmpty(imageList)) {
                throw new RuntimeException("sys_image表为空");
            }

            //menu
            List<Menu> menuList = menuMapper.selectList(new LambdaQueryWrapper<>());
            if (StringUtils.isEmpty(menuList)) {
                throw new RuntimeException("sys_menu表为空");
            }
            //role
            List<Role> roleList = roleMapper.selectList(new LambdaQueryWrapper<>());
            if (StringUtils.isEmpty(roleList)) {
                throw new RuntimeException("sys_role表为空");
            }
            //user
            List<User> userList = userMapper.selectList(new LambdaQueryWrapper<>());
            if (StringUtils.isEmpty(userList)) {
                throw new RuntimeException("sys_user表为空");
            }
            //user_role
            List<UserRole> userRoleList = userRoleMapper.selectList(new LambdaQueryWrapper<>());
            if (StringUtils.isEmpty(userRoleList)) {
                throw new RuntimeException("sys_user_role表为空");
            }
            //2.检查外键是否存在
            //(1) 头像表
            // 约束 用户表
            List<Integer> imageList_ids = imageList.stream().map(Image::getId).collect(Collectors.toList());
            List<Integer> image_id_Not_Exist = userList.stream().filter(o -> !imageList_ids.contains(o.getImageId())).map(User::getUserId).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(image_id_Not_Exist)) {
                System.out.println("用户表字段imageId未遵循外键约束，已删除错误数据");
                LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
                lqw.in(User::getImageId, image_id_Not_Exist);
                userMapper.delete(lqw);
            }
            //(2) 用户表
            // 约束 日志表
            List<String> log_accounts = logMapper.selectList(new LambdaQueryWrapper<>()).stream().map(Log::getAccount).distinct().collect(Collectors.toList());
            List<String> log_account_Not_Exist = log_accounts.stream().filter(o -> !userList.stream().map(User::getAccount).collect(Collectors.toList()).contains(o)).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(log_account_Not_Exist)) {
                System.out.println("日志表字段account未遵循外键约束，已删除错误数据");
                LambdaQueryWrapper<Log> lqw = new LambdaQueryWrapper<>();
                lqw.in(Log::getAccount, log_account_Not_Exist);
                logMapper.delete(lqw);
            }
            // 约束 登录记录表
            List<String> login_account = loginMapper.selectList(new LambdaQueryWrapper<>()).stream().map(Login::getAccount).distinct().collect(Collectors.toList());
            List<String> login_account_Not_Exist = login_account.stream().filter(o -> !userList.stream().map(User::getAccount).collect(Collectors.toList()).contains(o)).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(login_account_Not_Exist)) {
                System.out.println("登录记录表字段account未遵循外键约束，已删除错误数据");
                LambdaQueryWrapper<Login> lqw = new LambdaQueryWrapper<>();
                lqw.in(Login::getAccount, login_account_Not_Exist);
                loginMapper.delete(lqw);
            }
            //(3) 多表约束同一张表
            // 菜单表和角色表 约束 角色菜单表
            List<RoleMenu> roleMenuList = roleMenuMapper.selectList(new LambdaQueryWrapper<>());
            List<Integer> menu_id = menuList.stream().map(Menu::getMenuId).collect(Collectors.toList());
            List<Integer> role_access = roleList.stream().map(Role::getAccess).collect(Collectors.toList());
            List<Integer> role_menu_menuId_Not_Exist = roleMenuList.stream().map(RoleMenu::getMenuId).filter(menuId -> !menu_id.contains(menuId)).collect(Collectors.toList());
            List<Integer> role_menu_access_Not_Exist = roleMenuList.stream().map(RoleMenu::getAccess).filter(access -> !role_access.contains(access)).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(role_menu_menuId_Not_Exist) || StringUtils.isNotEmpty(role_menu_access_Not_Exist)) {
                LambdaQueryWrapper<RoleMenu> lqw = new LambdaQueryWrapper<>();
                if (StringUtils.isNotEmpty(role_menu_menuId_Not_Exist)) {
                    lqw.or().in(RoleMenu::getMenuId, role_menu_menuId_Not_Exist);
                    System.out.println("角色菜单表字段menuId未遵循外键约束，已删除错误数据");
                }
                if (StringUtils.isNotEmpty(role_menu_access_Not_Exist)) {
                    lqw.or().in(RoleMenu::getAccess, role_menu_access_Not_Exist);
                    System.out.println("角色菜单表字段access未遵循外键约束，已删除错误数据");
                }
                roleMenuMapper.delete(lqw);
            }
            // 用户表和角色表 约束 用户角色表
            List<Integer> userId = userList.stream().map(User::getUserId).collect(Collectors.toList());
            List<Integer> roleId = roleList.stream().map(Role::getRoleId).collect(Collectors.toList());
            List<Integer> user_role_userId_Not_Exist = userRoleList.stream().map(UserRole::getUserId).filter(o -> !userId.contains(o)).collect(Collectors.toList());
            List<Integer> user_role_roleId_Not_Exist = userRoleList.stream().map(UserRole::getRoleId).filter(o -> !roleId.contains(o)).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(user_role_userId_Not_Exist) || StringUtils.isNotEmpty(user_role_roleId_Not_Exist)) {
                LambdaQueryWrapper<UserRole> lqw = new LambdaQueryWrapper<>();
                if (StringUtils.isNotEmpty(user_role_userId_Not_Exist)) {
                    lqw.or().in(UserRole::getUserId, user_role_userId_Not_Exist);
                    System.out.println("用户角色表字段userId未遵循外键约束，已删除错误数据");
                }
                if (StringUtils.isNotEmpty(user_role_roleId_Not_Exist)) {
                    lqw.or().in(UserRole::getRoleId, user_role_roleId_Not_Exist);
                    System.out.println("用户角色表字段roleId未遵循外键约束，已删除错误数据");
                }
                userRoleMapper.delete(lqw);
            }
            //3.检查一些表的数据条数是否一致
            //(1) 用户表和用户角色表
            int userList_size = userList.size();
            int userRoleList_size = userRoleList.size();
            if (userRoleList_size != userList_size) {
                List<Integer> id = userList.stream().map(User::getUserId).filter(o -> !userRoleList.stream().map(UserRole::getUserId).collect(Collectors.toList()).contains(o)).collect(Collectors.toList());
                if (StringUtils.isNotEmpty(id)) {
                    LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
                    lqw.in(User::getUserId, id);
                    userMapper.delete(lqw);
                }
                System.out.println("用户表和用户角色表数据条数不一致，已删除错误数据");
            }
        }
    }

    /**
     * 检查角色菜单表是否存在多余数据
     */
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
}
