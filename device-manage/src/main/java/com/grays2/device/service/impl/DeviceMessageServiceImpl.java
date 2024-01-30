package com.grays2.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.device.domain.DeviceMessage;
import com.grays2.device.service.DeviceMessageService;
import com.grays2.device.mapper.DeviceMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author liuxianghui
* @description 针对表【device_message(设备信息)】的数据库操作Service实现
* @createDate 2024-01-30 13:40:04
*/
@Service
public class DeviceMessageServiceImpl extends ServiceImpl<DeviceMessageMapper, DeviceMessage>
    implements DeviceMessageService{

}




