package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Notices;
import com.grays2.system.domain.bo.NoticesBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notices> {
    List<Notices> getList();

    void insertBo(@Param("bo") NoticesBo bo);
}
