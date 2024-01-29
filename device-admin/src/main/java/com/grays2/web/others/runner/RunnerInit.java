package com.grays2.web.others.runner;

import com.grays2.common.redis.RedisUtils;
import com.grays2.system.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RunnerInit {
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MenuService menuService;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private DictService dictService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private PermService permService;

    @Async
    @PostConstruct
    public void init() {
        redisUtils.delAll();
        System.out.println("===============初始化权限===============");
        permService.createPermRedis();
        System.out.println("===============检查数据库表===============");
        commonService.checkDataBase();
        System.out.println("===============初始化父菜单===============");
        menuService.createMenuParentRedis();
        System.out.println("===============初始化菜单=================");
        menuService.createMenuRedis();
        System.out.println("===============初始化角色=================");
        roleService.createRoleRedis();
        System.out.println("===============初始化通知=================");
        noticeService.createNoticeRedis();
        System.out.println("===============初始化字典=================");
        dictService.createDictRedis();
        System.out.println("===============初始化完成=================");
    }
}
