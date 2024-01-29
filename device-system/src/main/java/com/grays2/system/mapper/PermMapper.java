package com.grays2.system.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Perm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermMapper extends BaseMapper<Perm> {
    void insertList(@Param("list") List<Perm> list);
}
