package com.grays2.system.service;


import com.grays2.system.domain.Log;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;

public interface LogService {
    PageVo<Log> getList(PageBo pageBo);

    PageVo<Log> getListSearch(String name, PageBo pageBo);

    void insertLog(Log log);
}
