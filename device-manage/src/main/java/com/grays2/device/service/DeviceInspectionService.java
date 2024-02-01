package com.grays2.device.service;

import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceInspection;
import com.grays2.device.domain.DeviceMessage;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.vo.DeviceMessageVo;
import com.grays2.device.domain.vo.InspectionVo;
import com.grays2.device.domain.vo.PageVo;

import java.util.List;

/**
* @author liuxianghui
* @description 针对表【device_inspection(巡检信息)】的数据库操作Service
* @createDate 2024-01-30 13:40:04
*/
public interface DeviceInspectionService  {
    List<InspectionVo> getLists();

    PageVo<InspectionVo> getList(PageBo pageBo);

    R<String> insert(DeviceInspection inspection);

    R<PageVo<InspectionVo>> delete(DeviceInspection inspection, PageBo pageBo);

    PageVo<InspectionVo> search(String name, PageBo pageBo);
}
