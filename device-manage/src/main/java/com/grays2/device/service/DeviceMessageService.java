package com.grays2.device.service;




import com.grays2.common.result.R;

import com.grays2.device.domain.DeviceMessage;
import com.grays2.device.domain.bo.PageBo;

import com.grays2.device.domain.vo.DeviceMessageVo;
import com.grays2.device.domain.vo.PageVo;


import java.util.List;

/**
* @author liuxianghui
* @description 针对表【device_message(设备信息)】的数据库操作Service
* @createDate 2024-01-30 13:40:04
*/
public interface DeviceMessageService {


    List<DeviceMessageVo> getLists();

    PageVo<DeviceMessageVo> getList(PageBo pageBo);

    R<String> insert(DeviceMessage scrap);

    R<PageVo<DeviceMessageVo>> delete(DeviceMessageVo scrap, PageBo pageBo);

    PageVo<DeviceMessageVo> search(String name, PageBo pageBo);
}
