package com.grays2.device.mapper;

import com.grays2.device.domain.DeviceScrap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.grays2.device.domain.vo.ScrapVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_scrap(设备报废)】的数据库操作Mapper
 * @createDate 2024-01-30 13:40:04
 * @Entity com.grays2.device.domain.DeviceScrap
 */
@Mapper
public interface DeviceScrapMapper extends BaseMapper<DeviceScrap> {

    List<ScrapVo> getLists();

    List<ScrapVo> getSearch(String name);
}




