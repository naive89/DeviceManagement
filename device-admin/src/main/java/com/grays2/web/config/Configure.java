package com.grays2.web.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import com.grays2.common.constant.SystemConstant;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Api(value = "拦截器", tags = {"拦截器"})
public class Configure implements WebMvcConfigurer {
    // 注册 Sa-Token 拦截器，打开注解式鉴权功能
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaInterceptor(handler -> {
                    // 登录认证 -- 拦截所有路由
//                    SaRouter.match("/**", r -> StpUtil.checkLogin());
                    // 角色认证 -- 拦截以 admin 开头的路由，必须具备 admin 角色或者 super-admin 角色才可以通过认证
//                    SaRouter.match("/admin/**", r -> StpUtil.checkRoleOr("admin", "super-admin"));
//                    // 权限认证 -- 不同模块认证不同权限
//                    SaRouter.match("/user/**", r -> StpUtil.checkRole("user"));
//                    SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
//                    // 甚至你可以随意的写一个打印语句
//                    SaRouter.match("/**", r -> System.out.println("--------权限认证成功-------"));
                }).isAnnotation(true))
                //拦截所有接口
                .addPathPatterns("/**")
                //不拦截的接口
                .excludePathPatterns(SystemConstant.Interceptors);
    }

    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                // 设置允许跨域请求的域名
                .allowedOriginPatterns("*")
                // 是否允许cookie
                .allowCredentials(true)
                // 设置允许的请求方式
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                // 设置允许的header属性
                .allowedHeaders("*")
                // 跨域允许时间
                .maxAge(3600);
    }
}
