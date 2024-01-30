package com.grays2.device.mapper;

import com.grays2.device.domain.DeviceType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author liuxianghui
 * @description 针对表【device_type(设备类型)】的数据库操作Mapper
 * @createDate 2024-01-30 13:40:04
 * @Entity com.grays2.device.domain.DeviceType
 */
@Mapper
public interface DeviceTypeMapper extends BaseMapper<DeviceType> {

}




