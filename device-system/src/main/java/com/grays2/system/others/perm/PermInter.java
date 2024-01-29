package com.grays2.system.others.perm;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface PermInter {
    /**
     * 权限
     */
    String perm() default "";

    /**
     * 名称
     */
    String name() default "";

    /**
     * 级别
     */
    String jsjb() default "0";
}