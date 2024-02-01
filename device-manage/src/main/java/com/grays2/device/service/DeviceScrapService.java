package com.grays2.device.service;

import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceScrap;
import com.baomidou.mybatisplus.extension.service.IService;
import com.grays2.device.domain.DeviceType;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.ScrapBo;
import com.grays2.device.domain.bo.TypeBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.domain.vo.ScrapVo;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_scrap(设备报废)】的数据库操作Service
 * @createDate 2024-01-30 13:40:04
 */
public interface DeviceScrapService {
    List<ScrapVo> getLists();

    PageVo<ScrapVo> getList(PageBo pageBo);

    R<String> insert(ScrapBo scrap);

    R<PageVo<ScrapVo>> delete(DeviceScrap scrap, PageBo pageBo);

    PageVo<ScrapVo> search(String name, PageBo pageBo);


}
