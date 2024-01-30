package com.grays2.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.device.domain.DeviceType;
import com.grays2.device.service.DeviceTypeService;
import com.grays2.device.mapper.DeviceTypeMapper;
import org.springframework.stereotype.Service;

/**
* @author liuxianghui
* @description 针对表【device_type(设备类型)】的数据库操作Service实现
* @createDate 2024-01-30 13:40:04
*/
@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType>
    implements DeviceTypeService{

}




