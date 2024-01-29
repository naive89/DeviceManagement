package com.grays2.web.others.power;

import cn.dev33.satoken.stp.StpInterface;
import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.system.domain.Role;
import com.grays2.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class powerInterface implements StpInterface {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RedisUtils redisUtils;

    //添加某些权限可以访问的
    @Override
    public List<String> getPermissionList(Object O, String S) {
        return null;
    }

    //添加某些角色可以访问的
    @Override
    public List<String> getRoleList(Object s, String loginType) {
        String account = (String) s;
        //当前用户所拥有的角色
        Role role = roleMapper.getRoleByAccount(account);
        //全部角色
        List<Role> roles = (List<Role>) redisUtils.get(RedisConstant.getList_Role);
        //权限比当前用户低的角色列表
        return roles.stream().filter(o -> o.getAccess() >= role.getAccess()).collect(Collectors.toList()).stream().map(Role::getRoleName).collect(Collectors.toList());
    }
}
