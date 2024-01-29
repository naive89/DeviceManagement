package com.grays2.system.others.perm;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.grays2.system.domain.model.UserPerm;
import com.grays2.system.mapper.UserPermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限拦截器
 */
@Component
public class PermInterceptor implements HandlerInterceptor {
    @Autowired
    private UserPermMapper userPermMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //在方法上寻找注解（这里是反射）
        PermInter permission = handlerMethod.getMethodAnnotation(PermInter.class);
        if (permission == null) {
            //方法不存在则在类上寻找注解
            permission = handlerMethod.getBeanType().getAnnotation(PermInter.class);
        }

        //如果没有添加权限注解则直接跳过允许访问
        if (permission == null) {
            return true;
        }

        //获取注解中的值
        String perm = permission.perm();
        String zh = (String) StpUtil.getLoginId();
        LambdaQueryWrapper<UserPerm> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserPerm::getZh, zh);
        List<String> list = userPermMapper.selectList(lqw).stream().map(UserPerm::getPerm).collect(Collectors.toList());
        if (list.contains(perm)) {
            return true;
        }
        throw new Exception("您没有 '" + perm + "' 权限！");
    }

}
