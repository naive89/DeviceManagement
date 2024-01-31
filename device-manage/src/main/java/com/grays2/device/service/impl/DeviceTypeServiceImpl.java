package com.grays2.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceType;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.TypeBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.service.DeviceTypeService;
import com.grays2.device.mapper.DeviceTypeMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_type(设备类型)】的数据库操作Service实现
 * @createDate 2024-01-30 13:40:04
 */
@Service
public class DeviceTypeServiceImpl extends ServiceImpl<DeviceTypeMapper, DeviceType> implements DeviceTypeService {

    @Override
    public List<DeviceType> getLists() {
        LambdaQueryWrapper<DeviceType> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(DeviceType::getCreateTime);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageVo<DeviceType> getList(PageBo pageBo) {
        List<DeviceType> lists = getLists();
        return new PageVo<>(pageBo, lists);
    }

    @Override
    public R<String> insert(TypeBo typeBo) {
        DeviceType deviceType = new DeviceType();
        deviceType.setName(typeBo.getName());
        deviceType.setPrincipal(typeBo.getPrincipal());
        deviceType.setRemark(typeBo.getRemark());
        int insert = this.baseMapper.insert(deviceType);
        if (insert == 1) {
            return R.ok("添加成功");
        }
        return R.warn("添加失败");
    }

    @Override
    public R<String> updateInfo(TypeBo typeBo) {
        DeviceType deviceType = new DeviceType();
        deviceType.setId(typeBo.getId());
        deviceType.setName(typeBo.getName());
        deviceType.setPrincipal(typeBo.getPrincipal());
        deviceType.setRemark(typeBo.getRemark());
        deviceType.setCreateTime(typeBo.getCreateTime());
        int update = this.baseMapper.updateById(deviceType);
        if (update == 1) {
            return R.ok("编辑成功");
        }
        return R.warn("编辑失败");
    }

    @Override
    public R<PageVo<DeviceType>> deleteDeviceType(TypeBo typeBo, PageBo pageBo) {
        LambdaQueryWrapper<DeviceType> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceType::getId, typeBo.getId());
        this.baseMapper.delete(wrapper);
        PageVo<DeviceType> list = this.getList(pageBo);
        return R.ok(list);
    }

    @Override
    public PageVo<DeviceType> search(String name, PageBo pageBo) {
        LambdaQueryWrapper<DeviceType> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null) {
            queryWrapper.eq(DeviceType::getName, name);
        }
        queryWrapper.orderByDesc(DeviceType::getCreateTime);
        List<DeviceType> deviceTypes = this.baseMapper.selectList(queryWrapper);
        return new PageVo<>(pageBo, deviceTypes);
    }
}




