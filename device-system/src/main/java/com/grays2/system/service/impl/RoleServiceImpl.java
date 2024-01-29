package com.grays2.system.service.impl;

import cn.dev33.satoken.stp.StpUtil;

import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.common.result.R;
import com.grays2.common.utils.StringUtils;
import com.grays2.system.domain.Role;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.bo.RoleBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.mapper.RoleMapper;
import com.grays2.system.mapper.UserMapper;
import com.grays2.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;

    @Override
    public PageVo<Role> getList(PageBo pageBo) {
        List<Role> list = (List<Role>) redisUtils.get(RedisConstant.getList_Role);
        return new PageVo<>(pageBo, list);
    }

    @Override
    public List<Role> getRoleKindList() {
        List<Role> roles = (List<Role>) redisUtils.get(RedisConstant.getList_Role);
        roles = roles.stream().map(o -> {
            Role role = new Role();
            role.setRoleId(o.getRoleId());
            role.setRoleName(o.getRoleName());
            role.setAccess(o.getAccess());
            return role;
        }).collect(Collectors.toList());
        return roles;
    }

    public List<Role> getAllList() {
        return roleMapper.getList();
    }

    @Override
    public void createRoleRedis() {
        List<Role> list = this.getAllList();
        String key = RedisConstant.getList_Role;
        long time = RedisConstant.getList_Role_Time;
        redisUtils.set(key, list, time);
    }

    @Override
    public R<PageVo<Role>> insert(RoleBo bo, PageBo pageBo) {
        if (StringUtils.isEmpty(bo.getRoleName()))
            return R.fail("角色名称为空");
        if (StringUtils.isEmpty(bo.getAccess()))
            return R.fail("角色权限为空");
        if (StringUtils.isEmpty(bo.getActivation()))
            return R.fail("角色权限为空");
        if (bo.getActivation() < 0)
            return R.fail("角色权限为负");
        List<Role> roles = (List<Role>) redisUtils.get(RedisConstant.getList_Role);
//        if (roles.stream().filter(o -> o.getRoleName().equals(bo.getRoleName())).collect(Collectors.toList()).size() != 0)
        if (roles.stream().anyMatch(o -> o.getRoleName().equals(bo.getRoleName())))
            return R.fail("权限名称已存在");
//        if (roles.stream().filter(o -> Objects.equals(o.getAccess(), bo.getAccess())).collect(Collectors.toList()).size() != 0)
        if (roles.stream().anyMatch(o -> Objects.equals(o.getAccess(), bo.getAccess())))
            return R.fail("权限级别已存在");
//        if (roles.stream().filter(o -> o.getAccess() < bo.getAccess() && o.getActivation() < bo.getActivation()).collect(Collectors.toList()).size() != 0)
        if (roles.stream().anyMatch(o -> o.getAccess() < bo.getAccess() && o.getActivation() < bo.getActivation()))
            return R.fail("存在权限高于添加权限，且所需活跃度低于添加权限的活跃度！");
//        if (roles.stream().filter(o -> o.getAccess() > bo.getAccess() && o.getActivation() > bo.getActivation()).collect(Collectors.toList()).size() != 0)
        if (roles.stream().anyMatch(o -> o.getAccess() > bo.getAccess() && o.getActivation() > bo.getActivation()))
            return R.fail("存在权限低于添加权限，且所需活跃度高于添加权限的活跃度！");
        int userId = userMapper.getUserByAccount((String) StpUtil.getLoginId()).getUserId();
        bo.setCreateUserId(userId);
        bo.setCreateTime(LocalDateTime.now());
        roleMapper.insertBo(bo);
        createRoleRedis();
        return R.ok("添加成功", getList(pageBo));
    }

    @Override
    public PageVo<Role> getListSearch(RoleBo roleBo, PageBo pageBo) {
        String s = roleBo.getRoleName();
        List<Role> roles = (List<Role>) redisUtils.get(RedisConstant.getList_Role);
        roles = roles.stream().filter(o -> o.getRoleName().contains(s) || o.getRemark().contains(s)).collect(Collectors.toList());
        return new PageVo<>(pageBo, roles);
    }

    @Override
    public PageVo<Role> update(RoleBo roleBo, PageBo pageBo) {
        roleMapper.updateBo(roleBo);
        createRoleRedis();
        return getList(pageBo);
    }

    @Override
    public PageVo<Role> delete(RoleBo roleBo, PageBo pageBo) {
        roleMapper.deleteBo(roleBo);
        createRoleRedis();
        return getList(pageBo);
    }
}
