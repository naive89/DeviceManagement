package com.grays2.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.common.result.R;
import com.grays2.common.utils.StringUtils;
import com.grays2.system.domain.Dict;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.mapper.*;
import com.grays2.system.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DictServiceImpl implements DictService {
    final String key = RedisConstant.dict;
    @Autowired
    private DictMapper dictMapper;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public void createDictRedis() {
        LambdaQueryWrapper<Dict> lqw = new LambdaQueryWrapper<>();
        List<Dict> dictList = dictMapper.selectList(lqw);
        String key = RedisConstant.dict;
        Map<String, List<Dict>> map = dictList.stream().collect(Collectors.groupingBy(Dict::getType));
        map.forEach((K, V) -> redisUtils.set(key + K, V));
        redisUtils.set(key + RedisConstant.db_id, getDBId());
    }

    @Override
    public List<Dict> getDict(String dict) {
        String key = RedisConstant.dict;
        return (List<Dict>) redisUtils.get(key + dict);
    }

    public Map<String, Integer> getDBId() {
        String[] db_ids = RedisConstant.db_ids;
        Map<String, Integer> map = new HashMap<>();
        for (String s : db_ids) map.put(s, getDBId(s));
        return map;
    }

    @Override
    public int getId(String db) {
        String key = RedisConstant.dict + RedisConstant.db_id;
        Map<String, Integer> map = (Map<String, Integer>) redisUtils.get(key);
        return map.get(db);
    }

    @Async
    @Override
    public void updateDBId(String db) {
        String key_db_id = key + RedisConstant.db_id;
        Map<String, Integer> map = (Map<String, Integer>) redisUtils.get(key_db_id);
        map.put(db, getDBId(db));
        redisUtils.set(key_db_id, map);
        dictMapper.updateDBID(key, map.toString());
    }

    @Override
    public PageVo<Dict> getLists(PageBo pageBo) {
        List<Dict> dictLists = this.dictMapper.selectList(null);
        return new PageVo<>(pageBo,dictLists);
    }

    public int getDBId(String db) {
        switch (db) {
            case "user":
                if (StringUtils.lisEmpty(userMapper.selectList(new LambdaQueryWrapper<>()))) return 1;
                return userMapper.getLastId() + 1;
            case "role":
                if (StringUtils.lisEmpty(roleMapper.selectList(new LambdaQueryWrapper<>()))) return 1;
                return roleMapper.getLastRole().getRoleId() + 1;
            case "menu":
                if (StringUtils.lisEmpty(menuMapper.selectList(new LambdaQueryWrapper<>()))) return 1;
                return menuMapper.getLastId() + 1;
            case "dict":
                if (StringUtils.lisEmpty(dictMapper.selectList(new LambdaQueryWrapper<>()))) return 1;
                return dictMapper.getLastId() + 1;
            case "image":
                if (StringUtils.lisEmpty(imageMapper.selectList(new LambdaQueryWrapper<>()))) return 1;
                return imageMapper.getLast().getId() + 1;
            case "user_role":
                if (StringUtils.lisEmpty(userRoleMapper.selectList(new LambdaQueryWrapper<>()))) return 1;
                return userRoleMapper.getLastId() + 1;
            default:
                return 0;
        }
    }

}
