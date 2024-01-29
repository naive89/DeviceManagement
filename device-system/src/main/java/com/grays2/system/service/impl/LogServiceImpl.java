package com.grays2.system.service.impl;


import com.grays2.system.domain.Log;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.mapper.LogMapper;
import com.grays2.system.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageVo<Log> getList(PageBo pageBo) {
        List<Log> logs = logMapper.getList();
        return new PageVo<>(pageBo, logs);

    }

    public List<Log> getList() {
        return logMapper.getList();
    }

    @Override
    public PageVo<Log> getListSearch(String s, PageBo pageBo) {
        List<Log> list = this.getList();
        //账号，接口，地址，参数，方法
        list = list.stream().filter(o -> o.getAccount().contains(s) || o.getUri().contains(s) || o.getUrl().contains(s) || o.getParams().contains(s) || o.getMethod().contains(s)).collect(Collectors.toList());
        return new PageVo<>(pageBo, list);
    }

    @Async
    @Override
    public void insertLog(Log log) {
        logMapper.insert(log);
    }
}
