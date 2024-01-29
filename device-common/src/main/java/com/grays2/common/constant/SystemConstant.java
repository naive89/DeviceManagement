package com.grays2.common.constant;

import io.swagger.annotations.Api;

@Api(value = "系统常量", tags = {"系统常量"})
public class SystemConstant {
    /**
     * log日志跳过的方法
     */
    public static final String[] passMethod = {"asideMenus"};
    /**
     * 不输出参数以及不记录参数
     */
    public static final String[] NotOutputAndRecord = {"/login/"};
    /**
     * 最小account 十亿
     */
    public static final Integer billion = 1000000000;
    /**
     * 拦截器白名单
     */
    public static final String[] Interceptors = {"/code/", "/login/", "/login/register"};
    /**
     * 账号状态-正常激活
     */
    public static final int USER_STATUS_NORMAL = 1;
    /**
     * 账号状态-禁止使用
     */
    public static final int USER_STATUS_DISABLE = 0;
    /**
     * 账号状态-暂时禁止
     */
    public static final String userStatus = "TEMPORARILY_DISABLE" ;
    /**
     * 账号密码最大错误次数
     */
    public static final Integer passwordErrorNum = 5;
    /**
     * 账号密码最大错误次数记录时间
     * 10分钟
     */
    public static final long passwordErrorNum_Time = 60 * 10;
    /**
     * 项目实体所在位置
     */
    public static final String DomainPath = "com.example.system.domain" ;
    /**
     * 新增用户默认头像ID
     */
    public static final int image_Id = 1;
    /**
     * 好友关系-正常
     */
    public static final int friend_status_normal = 2;
    /**
     * 本地保存文件基地址
     */
    public static final String Upload = "C:\\upload\\" ;
    /**
     * 本地图片保存地址
     */
    public static final String IMAGE_Upload = Upload + "image\\" ;
    /**
     * 附件下载地址
     */
    public static final String FILE_Upload = Upload + "file\\" ;
    /**
     * 返回结果最大长度
     */
    public static final Integer resultMaxLen = 4194304;
    /**
     * 接口所在位置
     */
    public static final String ControllerPath = "com.grays2.web.controller";
}
