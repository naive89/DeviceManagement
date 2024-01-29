package com.grays2.system.service.impl;


import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.system.domain.Notices;
import com.grays2.system.domain.bo.NoticesBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.mapper.NoticeMapper;
import com.grays2.system.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    final String key = RedisConstant.getList_Notice;
    @Autowired
    private NoticeMapper noticeMapper;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public List<Notices> getListIndex() {
        return ((List<Notices>) redisUtils.get(key));
    }

    @Override
    public void createNoticeRedis() {
        List<Notices> list = noticeMapper.getList();
        long time = RedisConstant.getList_Notice_Time;
        redisUtils.set(key, list, time);
    }

    @Override
    public PageVo<Notices> getList(PageBo pageBo) {
        List<Notices> list = (List<Notices>) redisUtils.get(key);
        return new PageVo<>(pageBo, list);
    }

    @Override
    public void insert(NoticesBo bo) {
        bo.setTime(LocalDate.now());
        noticeMapper.insertBo(bo);

    }
}
