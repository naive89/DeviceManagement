package com.grays2.common.constant;

import io.swagger.annotations.Api;

@Api(value = "缓存常量", tags = {"缓存常量"})
public class RedisConstant {
    /**
     * 验证码缓存
     */
    public static final String code = "code" ;
    /**
     * 验证码缓存
     * 时间5分钟
     */
    public static final long code_Time = 60 * 5;
    /**
     * 缓存-Menu列表
     */
    public static final String getList_Menu = "getList:Menu";
    /**
     * 缓存-Menu列表缓存-保存时间-永久
     */
    public static final long getList_Menu_Time = -1;
    /**
     * 缓存-Menu列表
     */
    public static final String getList_Menu_aside = "getList:Menu:aside";
    /**
     * 缓存-Menu列表缓存-保存时间-永久
     */
    public static final long getList_Menu_aside_Time = 60;
    /**
     * 缓存-Role列表
     */
    public static final String getList_Role = "getList:Role";
    /**
     * 缓存-Role列表缓存-保存时间-永久
     */
    public static final long getList_Role_Time = -1;
    /**
     * 缓存-Menu列表
     */
    public static final String getList_MenuParent = "getList:MenuParent" ;
    /**
     * 缓存-Menu列表缓存-保存时间-永久
     */
    public static final long getList_MenuParent_Time = -1;
    /**
     * 缓存-Notice列表
     */
    public static final String getList_Notice = "getList:Notice" ;
    /**
     * 缓存-Notice列表缓存-保存时间-永久
     */
    public static final long getList_Notice_Time = -1;
    /**
     * 缓存-Item列表
     */
    public static final String getList_Item = "getList:Item" ;
    /**
     * 缓存-Item列表缓存-保存时间-永久
     */
    public static final long getList_Item_Time = -1;
    /**
     * 缓存-ItemMyList列表
     */
    public static final String getList_ItemMyList = getList_Item + ":" ;
    /**
     * 缓存-ItemMyList列表缓存-保存时间-永久
     */
    public static final long getList_ItemMyList_Time = -1;
    /**
     * 缓存-审核人员列表
     */
    public static final String getList_ShareUser = "getList:ShareUser" ;
    /**
     * 缓存-审核人员列表缓存-保存时间-永久
     */
    public static final long getList_ShareUser_Time = -1;
    /**
     * 字典缓存
     */
    public static final String dict = "Dict:" ;
    /**
     * 字典类型-数据库自增ID
     */
    public static final String db_id = "db_id" ;
    /**
     * 字典类型-menu
     */
    public static final String db_menu = "menu" ;
    /**
     * 字典类型-friend
     */
    public static final String db_friend = "friend" ;
    /**
     * 字典类型-user
     */
    public static final String db_user = "user" ;
    /**
     * 字典类型-item
     */
    public static final String db_item = "item" ;
    /**
     * 字典类型-project
     */
    public static final String db_project = "project" ;
    /**
     * 字典类型-image
     */
    public static final String db_image = "image" ;
    /**
     * 字典类型-score
     */
    public static final String db_score = "score" ;
    /**
     * 字典类型-role
     */
    public static final String db_role = "role" ;
    /**
     * 字典类型-user_role
     */
    public static final String db_user_role = "user_role" ;
    /**
     * 字典类型-dict
     */
    public static final String db_dict = "dict" ;
    /**
     * 字典类型-process
     */
    public static final String db_process = "process" ;
    /**
     * 字典类型-perms
     */
    public static final String db_perms = "perms" ;
    /**
     * 字典类型-activation
     */
    public static final String db_activation = "activation";
    /**
     * 字典类型-数据库自增ID集合
     */
    public static final String[] db_ids = {db_activation, db_process, db_menu, db_friend, db_dict, db_role, db_score, db_user_role, db_user, db_project, db_item, db_image};
}
