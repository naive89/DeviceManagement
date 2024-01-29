package com.grays2.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Dict;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DictMapper extends BaseMapper<Dict> {
    int getLastId();

    void updateDBID(@Param("type") String type, @Param("dict") String dict);
}
