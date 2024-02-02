package com.grays2.system.service;


import com.grays2.common.result.R;
import com.grays2.system.domain.Dict;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;

import java.util.List;

public interface DictService {
    void createDictRedis();

    List<Dict> getDict(String dict);

    int getId(String db);

    void updateDBId(String db);

    PageVo<Dict>getLists(PageBo pageBo);
}
