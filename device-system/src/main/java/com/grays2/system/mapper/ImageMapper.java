package com.grays2.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.grays2.system.domain.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper extends BaseMapper<Image> {
    Image getLast();
}
