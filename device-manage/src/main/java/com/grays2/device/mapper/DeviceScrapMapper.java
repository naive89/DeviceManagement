package com.grays2.device.mapper;

import com.grays2.device.domain.DeviceScrap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author liuxianghui
* @description 针对表【device_scrap(设备报废)】的数据库操作Mapper
* @createDate 2024-01-30 13:40:04
* @Entity com.grays2.device.domain.DeviceScrap
*/
@Mapper
public interface DeviceScrapMapper extends BaseMapper<DeviceScrap> {

}



