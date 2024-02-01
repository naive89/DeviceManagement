package com.grays2.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceMessage;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.vo.DeviceMessageVo;
import com.grays2.device.domain.vo.PageVo;

import com.grays2.device.service.DeviceMessageService;
import com.grays2.device.mapper.DeviceMessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_message(设备信息)】的数据库操作Service实现
 * @createDate 2024-01-30 13:40:04
 */
@Service
public class DeviceMessageServiceImpl extends ServiceImpl<DeviceMessageMapper, DeviceMessage> implements DeviceMessageService {

    @Override
    public List<DeviceMessageVo> getLists() {
        return this.baseMapper.getLists();
    }

    @Override
    public PageVo<DeviceMessageVo> getList(PageBo pageBo) {
        List<DeviceMessageVo> lists = this.getLists();
        return new PageVo<>(pageBo, lists);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> insert(DeviceMessage message) {
        try {
            int insert = this.baseMapper.insert(message);
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
    public R<PageVo<DeviceMessageVo>> delete(DeviceMessageVo deviceMessageVo, PageBo pageBo) {
        LambdaQueryWrapper<DeviceMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DeviceMessage::getId, deviceMessageVo.getId());
        int delete = this.baseMapper.delete(queryWrapper);
        if (delete == 1) {
            List<DeviceMessageVo> lists = this.getLists();
            return R.ok("删除成功", new PageVo<>(pageBo, lists));
        }
        return R.warn("删除失败");
    }

    @Override
    public PageVo<DeviceMessageVo> search(String name, PageBo pageBo) {
        List<DeviceMessageVo> deviceMessageVos = this.baseMapper.searchDeviceMessage(name);
        return new PageVo<>(pageBo, deviceMessageVos);
    }
}




