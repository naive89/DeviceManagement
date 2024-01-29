package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LogMapper extends BaseMapper<Log> {
    List<Log> getList();
}
