package com.grays2.system.service;



import com.grays2.system.domain.Notices;
import com.grays2.system.domain.bo.NoticesBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;

import java.util.List;

public interface NoticeService {
    List<Notices> getListIndex();

    void createNoticeRedis();

    PageVo<Notices> getList(PageBo pageBo);

    void insert(NoticesBo bo);
}
