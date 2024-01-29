package com.grays2.system.service.impl;




import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;

import com.grays2.system.domain.vo.UserVo;
import com.grays2.system.mapper.UserMapper;

import com.grays2.system.service.ProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void createShareUserRedis() {
        List<UserVo> userList = userMapper.getUserListVo();
        userList = userList.stream().peek(o -> o.setImage("")).filter(o -> o.getAccess() != null && o.getAccess() <= 5).collect(Collectors.toList());
        long time = RedisConstant.getList_ShareUser_Time;
        String key = RedisConstant.getList_ShareUser;
        redisUtils.set(key, userList, time);
    }
    @Override
    public List<Map<String, Object>> getAllShareUser() {
        String key = RedisConstant.getList_ShareUser;
        List<Map<String, Object>> list = ((List<UserVo>) redisUtils.get(key)).stream().map(o -> {
            Map<String, Object> map = new HashMap<>();
            map.put("userId", o.getUserId());
            map.put("username", o.getUsername());
            return map;
        }).collect(Collectors.toList());
        return list;
    }
}
