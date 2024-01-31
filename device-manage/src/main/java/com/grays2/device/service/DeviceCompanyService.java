package com.grays2.device.service;


import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceCompany;

import com.grays2.device.domain.bo.CompanyBo;
import com.grays2.device.domain.bo.PageBo;

import com.grays2.device.domain.vo.PageVo;

import java.util.List;

/**
* @author liuxianghui
* @description 针对表【device_company(设备厂商)】的数据库操作Service
* @createDate 2024-01-30 13:40:04
*/
public interface DeviceCompanyService {
    List<DeviceCompany> getLists();

    PageVo<DeviceCompany> getList(PageBo pageBo);

    R<String> insert(CompanyBo companyBo);

    R<PageVo<DeviceCompany>> delete(CompanyBo companyBo,PageBo pageBo);

    PageVo<DeviceCompany> search(String name, PageBo pageBo);
}
