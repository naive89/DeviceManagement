package com.grays2.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.system.domain.User;
import com.grays2.system.mapper.UserMapper;
import com.grays2.system.service.CountService;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
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



    @Override
    @SneakyThrows
    public Map<String, Object> getContribution() {
        String url = "https://gitee.com/cai-bin00_admin";
        Document document = Jsoup.parse(new URL(url), 1000000);
        Elements boxLess = new Elements();
        boxLess.addAll(document.getElementsByClass("box less"));
        boxLess.addAll(document.getElementsByClass("box little"));
        boxLess.addAll(document.getElementsByClass("box some"));
        boxLess.addAll(document.getElementsByClass("box many"));
        boxLess.addAll(document.getElementsByClass("box much"));

        Elements dates = document.getElementsByAttribute("date");
        String start = dates.first().toString().split("个贡献：")[1].split("\" date=\"")[0];
        String end = dates.last().toString().split("个贡献：")[1].split("\" date=\"")[0];

        List<List<Object>> data = new ArrayList<>();
        boxLess.forEach(box -> {
            String s = box.toString();
            String[] split = s.split("data-content=\"")[1].split("\"")[0].split("个贡献：");
            int num = Integer.parseInt(split[0]);
            String day = split[1];
            List<Object> list = new ArrayList<>();
            list.add(day);
            list.add(num);
            data.add(list);
        });
        Map<String, Object> resultMap = new HashMap<>();

        List<String> date = new ArrayList<>();
        date.add(start);
        date.add(end);
        resultMap.put("date", date);
        resultMap.put("data", data);
        return resultMap;
    }

}
