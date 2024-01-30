package com.grays2.device.mapper;

import com.grays2.device.domain.DeviceInspection;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liuxianghui
* @description 针对表【device_inspection(巡检信息)】的数据库操作Mapper
* @createDate 2024-01-30 13:40:04
* @Entity com.grays2.device.domain.DeviceInspection
*/
@Mapper
public interface DeviceInspectionMapper extends BaseMapper<DeviceInspection> {

}




