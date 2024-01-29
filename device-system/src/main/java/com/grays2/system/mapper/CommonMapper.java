package com.grays2.system.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommonMapper {


    String getImage(@Param("imageId") int imageId);
}
