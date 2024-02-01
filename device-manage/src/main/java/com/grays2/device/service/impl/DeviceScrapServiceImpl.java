package com.grays2.device.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.common.result.R;

import com.grays2.device.domain.DeviceScrap;

import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.ScrapBo;

import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.domain.vo.ScrapVo;
import com.grays2.device.service.DeviceScrapService;
import com.grays2.device.mapper.DeviceScrapMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_scrap(设备报废)】的数据库操作Service实现
 * @createDate 2024-01-30 13:40:04
 */
@Service
public class DeviceScrapServiceImpl extends ServiceImpl<DeviceScrapMapper, DeviceScrap> implements DeviceScrapService {

    @Override
    public List<ScrapVo> getLists() {
        return this.baseMapper.getLists();
    }

    @Override
    public PageVo<ScrapVo> getList(PageBo pageBo) {
        List<ScrapVo> lists = this.getLists();
        return new PageVo<>(pageBo, lists);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> insert(ScrapBo scrap) {
        try {
            DeviceScrap deviceScrap = new DeviceScrap();
            deviceScrap.setDeviceId(scrap.getDeviceId());
            deviceScrap.setUserId(scrap.getUserId());
            deviceScrap.setScrapTime(scrap.getScrapTime());
            deviceScrap.setType(scrap.getType());
            deviceScrap.setScrapDestination(scrap.getScrapDestination());
            deviceScrap.setHandle(scrap.getHandle());
            deviceScrap.setRemark(scrap.getRemark());
            int insert = this.baseMapper.insert(deviceScrap);
            if (insert == 1) {
                return R.ok("添加成功");
            }
            return R.warn("添加失败");
        } catch (Exception e) {
            return R.warn("添加失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<PageVo<ScrapVo>> delete(DeviceScrap scrap, PageBo pageBo) {
        LambdaQueryWrapper<DeviceScrap> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeviceScrap::getId, scrap.getId());
        this.baseMapper.delete(queryWrapper);
        List<ScrapVo> lists = this.baseMapper.getLists();
        return R.ok(new PageVo<>(pageBo, lists));
    }

    @Override
    public PageVo<ScrapVo> search(String name, PageBo pageBo) {
        List<ScrapVo> search = this.baseMapper.getSearch(name);
        return new PageVo<>(pageBo, search);
    }
}




