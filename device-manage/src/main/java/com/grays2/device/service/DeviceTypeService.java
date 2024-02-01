package com.grays2.device.service;

import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceType;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.TypeBo;
import com.grays2.device.domain.vo.PageVo;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_type(设备类型)】的数据库操作Service
 * @createDate 2024-01-30 13:40:04
 */
public interface DeviceTypeService {
    List<DeviceType> getLists();

    PageVo<DeviceType> getList(PageBo pageBo);

    R<String> insert(TypeBo typeBo);

    R<String> updateInfo(TypeBo typeBo);

    R<PageVo<DeviceType>> deleteDeviceType(TypeBo typeBo,PageBo pageBo);

    PageVo<DeviceType> search(String name, PageBo pageBo);
}
