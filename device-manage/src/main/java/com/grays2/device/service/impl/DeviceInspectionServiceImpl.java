package com.grays2.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceInspection;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.vo.InspectionVo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.service.DeviceInspectionService;
import com.grays2.device.mapper.DeviceInspectionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_inspection(巡检信息)】的数据库操作Service实现
 * @createDate 2024-01-30 13:40:04
 */
@Service
public class DeviceInspectionServiceImpl extends ServiceImpl<DeviceInspectionMapper, DeviceInspection> implements DeviceInspectionService {

    @Override
    public List<InspectionVo> getLists() {
        return this.baseMapper.getList();
    }

    @Override
    public PageVo<InspectionVo> getList(PageBo pageBo) {
        List<InspectionVo> lists = this.getLists();
        return new PageVo<>(pageBo, lists);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> insert(DeviceInspection inspection) {
        try {
            int insert = this.baseMapper.insert(inspection);
            if (insert == 1) {
                return R.ok("添加成功");
            }
            return R.warn("添加失败");
        } catch (Exception e) {
            return R.warn("添加失败");
        }
    }

    @Override
    public R<PageVo<InspectionVo>> delete(DeviceInspection inspection, PageBo pageBo) {
        try {
            LambdaQueryWrapper<DeviceInspection> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DeviceInspection::getId, inspection.getId());
            int delete = this.baseMapper.delete(queryWrapper);
            if (delete == 1) {
                List<InspectionVo> list = this.baseMapper.getList();
                return R.ok("删除成功", new PageVo<>(pageBo, list));
            }
            return R.warn("删除失败");
        } catch (Exception e) {
            return R.warn("删除失败");
        }
    }

    @Override
    public PageVo<InspectionVo> search(String name, PageBo pageBo) {
        List<InspectionVo> inspectionVos = this.baseMapper.searchDeviceInspection(name);
        return new PageVo<>(pageBo, inspectionVos);
    }
}




