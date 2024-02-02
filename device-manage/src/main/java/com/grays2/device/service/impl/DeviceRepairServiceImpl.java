package com.grays2.device.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceRepair;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.RepairBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.domain.vo.RepairVo;
import com.grays2.device.service.DeviceRepairService;
import com.grays2.device.mapper.DeviceRepairMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_repair(维修记录)】的数据库操作Service实现
 * @createDate 2024-01-30 13:40:04
 */
@Service
public class DeviceRepairServiceImpl extends ServiceImpl<DeviceRepairMapper, DeviceRepair> implements DeviceRepairService {

    @Override
    public List<RepairVo> getLists() {
        return this.baseMapper.getLists();
    }

    @Override
    public PageVo<RepairVo> getList(PageBo pageBo) {
        List<RepairVo> lists = this.getLists();
        return new PageVo<>(pageBo, lists);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> insert(RepairBo repairBo) {
        DeviceRepair repair = new DeviceRepair();
        repair.setDeviceId(repairBo.getDeviceMsgId());
        repair.setUserId(repairBo.getUserId());
        repair.setFaultTime(repairBo.getFaultTime());
        repair.setRepairTime(repairBo.getRepairTime());
        repair.setRepairParts(repairBo.getRepairParts());
        repair.setRepairStatus(repairBo.getRepairStatus());
        repair.setFailureCause(repairBo.getFailureCause());
        repair.setRemark(repairBo.getRemark());
        repair.setMethod(repairBo.getMethod());
        repair.setFactory(repairBo.getFactory());
        repair.setCost(repairBo.getCost());
        repair.setDeviceFactory(repairBo.getDeviceFactory());
        try {
            int insert = this.baseMapper.insert(repair);
            if (insert == 1) {
                return R.ok("添加成功");
            }
            return R.warn("添加失败");
        } catch (Exception e) {
            e.printStackTrace();
            return R.warn("添加失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<PageVo<RepairVo>> delete(DeviceRepair repair, PageBo pageBo) {
        try {
            int delete = this.baseMapper.deleteById(repair);
            if (delete == 1) {
                List<RepairVo> lists = this.getLists();
                return R.ok("删除成功", new PageVo<>(pageBo, lists));
            }
            return R.warn("删除失败");
        } catch (Exception e) {
            return R.warn("删除失败");
        }
    }

    @Override
    public PageVo<RepairVo> search(String name, PageBo pageBo) {
        List<RepairVo> repairVos = this.baseMapper.searchDeviceRepair(name);
        return new PageVo<>(pageBo, repairVos);
    }
}




