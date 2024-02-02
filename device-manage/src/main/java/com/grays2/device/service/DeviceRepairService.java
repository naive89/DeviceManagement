package com.grays2.device.service;

import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceRepair;
import com.baomidou.mybatisplus.extension.service.IService;
import com.grays2.device.domain.DeviceScrap;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.RepairBo;
import com.grays2.device.domain.bo.ScrapBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.domain.vo.RepairVo;
import com.grays2.device.domain.vo.ScrapVo;

import java.util.List;

/**
* @author liuxianghui
* @description 针对表【device_repair(维修记录)】的数据库操作Service
* @createDate 2024-01-30 13:40:04
*/
public interface DeviceRepairService {
    List<RepairVo> getLists();

    PageVo<RepairVo> getList(PageBo pageBo);

    R<String> insert(RepairBo repairBo);

    R<PageVo<RepairVo>> delete(DeviceRepair repair, PageBo pageBo);

    PageVo<RepairVo> search(String name, PageBo pageBo);
}
