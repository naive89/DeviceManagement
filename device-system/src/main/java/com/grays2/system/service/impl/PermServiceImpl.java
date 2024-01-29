package com.grays2.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.grays2.common.constant.SystemConstant;
import com.grays2.common.utils.ClassUtils;
import com.grays2.system.domain.Perm;
import com.grays2.system.domain.Role;
import com.grays2.system.domain.User;
import com.grays2.system.domain.model.UserPerm;
import com.grays2.system.mapper.PermMapper;
import com.grays2.system.mapper.RoleMapper;
import com.grays2.system.mapper.UserMapper;
import com.grays2.system.mapper.UserPermMapper;
import com.grays2.system.others.perm.PermInter;
import com.grays2.system.service.PermService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermServiceImpl extends ServiceImpl<PermMapper, Perm> implements PermService {
    @Autowired
    private PermMapper permMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserPermMapper userPermMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public List<Perm> getList() {
        return permMapper.selectList(new LambdaQueryWrapper<>());
    }

    @Override
    @SneakyThrows
    public void createPermRedis() {
        List<Perm> perms = new ArrayList<>();
        Integer jb = roleMapper.selectList(new LambdaQueryWrapper<>()).stream().max(Comparator.comparing(Role::getAccess)).get().getAccess();

        String packagePath = SystemConstant.ControllerPath;
        // 获取包下所有的类
        List<Class<?>> classes = ClassUtils.getClzFromPkg(packagePath);

//        List<Class<?>> classes = getClasses(packagePath);
        classes.stream().peek(c -> {
            Method[] methods = c.getDeclaredMethods();
            for (Method method : methods) {
                PermInter inter = method.getDeclaredAnnotation(PermInter.class);
                if (inter != null) {
                    Perm perm = new Perm();
                    perm.setJsjb(Integer.parseInt(inter.jsjb()));
                    perm.setName(inter.name());
                    perm.setPerm(inter.perm());
                    if (perm.getJsjb() == 0) perm.setJsjb(jb);
                    perms.add(perm);
                }
            }

        }).collect(Collectors.toList());

        this.createUserPermList(perms);

        permMapper.insertList(perms);
    }

    //将账号和权限关联起来
    @Async
    public void createUserPermList(List<Perm> perms) {
        List<UserPerm> list1 = userPermMapper.selectList(new LambdaQueryWrapper<>());

        userPermMapper.delete(new LambdaQueryWrapper<>());

        List<UserPerm> list = new ArrayList<>();

        List<User> userList = userMapper.selectList(new LambdaQueryWrapper<>());

        userList.stream().peek(o -> {
            Integer access = roleMapper.getRoleByAccount(o.getAccount()).getAccess();
            List<Perm> permList = perms.stream().filter(p -> p.getJsjb() >= access).collect(Collectors.toList());
            for (Perm perm : permList) {
                UserPerm userPerm = new UserPerm();
                userPerm.setZh(o.getAccount());
                userPerm.setPerm(perm.getPerm());
                list.add(userPerm);
            }
        }).collect(Collectors.toList());

        list1.addAll(list);
        list1 = list1.stream().peek(o -> o.setId(null)).distinct().collect(Collectors.toList());

        userPermMapper.insertList(list1);
    }
}
