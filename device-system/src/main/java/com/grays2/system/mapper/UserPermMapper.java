package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.model.UserPerm;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserPermMapper extends BaseMapper<UserPerm> {
    void insertList(List<UserPerm> list);
}
