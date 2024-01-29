package com.grays2.common.constant;

import io.swagger.annotations.Api;

@Api(value = "活跃度常量", tags = {"活跃度常量"})
public class ActivationConstant {
    /**
     * 活跃度系数-每日登录
     */
    public static final double ACTIVATION_Login = 1.0;
    /**
     * 活跃度常量-每日登录
     */
    public static final String Login = "Login";
    /**
     * 活跃度系数-创建项目
     */
    public static final double ACTIVATION_AddItem = 5.0;
    /**
     * 活跃度常量-创建项目
     */
    public static final String AddItem = "AddItem";
    /**
     * 活跃度系数-审核项目
     */
    public static final double ACTIVATION_Process = 3.0;
    /**
     * 活跃度常量-审核项目
     */
    public static final String Process = "Process";
    /**
     * 活跃度系数-打分
     */
    public static final double ACTIVATION_Score = 10.0;
    /**
     * 活跃度常量-打分
     */
    public static final String Score = "Score";
}
