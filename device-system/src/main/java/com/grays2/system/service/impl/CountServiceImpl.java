package com.grays2.system.service.impl;




import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.system.domain.User;
import com.grays2.system.mapper.UserMapper;
import com.grays2.system.service.CountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountServiceImpl implements CountService {
    final String key = RedisConstant.getList_Item;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public Map<String, Object> getProjectLine() {
        List<List<Long>> data = new ArrayList<>();
        List<String> x_ = new ArrayList<>();//横坐标
        List<String> finalX_ = x_;
        Map<String, Object> map = new HashMap<>();
        //横坐标
        x_ = finalX_.stream().distinct().collect(Collectors.toList());
        map.put("x", x_);
        //折线数据
        map.put("data", data);
        //项目种类
        return map;
    }

    @Override
    public List<Map<String, Integer>> getUserInsert() {
        Map<Integer, List<User>> map = userMapper.getUserList().stream().collect(Collectors.groupingBy(user -> Integer.parseInt(user.getCreateTime().toString().split("-")[0])));
        Map<Integer, Integer> map1 = new HashMap<>();
        map.forEach((k, v) -> map1.put(k, v.size()));
        TreeMap<Integer, Integer> treeMap = new TreeMap<>(map1);
        List<Map<String, Integer>> list = new ArrayList<>();
        treeMap.forEach((k, v) -> {
            Map<String, Integer> m = new HashMap<>();
            m.put("year", k);
            m.put("count", v);
            list.add(m);
        });
        return list;
    }

}
