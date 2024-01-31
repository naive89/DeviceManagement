package com.grays2.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceCompany;
import com.grays2.device.domain.DeviceType;
import com.grays2.device.domain.bo.CompanyBo;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.service.DeviceCompanyService;
import com.grays2.device.mapper.DeviceCompanyMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuxianghui
 * @description 针对表【device_company(设备厂商)】的数据库操作Service实现
 * @createDate 2024-01-30 13:40:04
 */
@Service
public class DeviceCompanyServiceImpl extends ServiceImpl<DeviceCompanyMapper, DeviceCompany> implements DeviceCompanyService {

    @Override
    public List<DeviceCompany> getLists() {
        LambdaQueryWrapper<DeviceCompany> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(DeviceCompany::getId);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public PageVo<DeviceCompany> getList(PageBo pageBo) {
        List<DeviceCompany> lists = getLists();
        return new PageVo<>(pageBo, lists);
    }

    @Override
    public R<String> insert(CompanyBo companyBo) {
        DeviceCompany deviceCompany = new DeviceCompany();
        deviceCompany.setName(companyBo.getName());
        deviceCompany.setPhone(companyBo.getPhone());
        deviceCompany.setType(companyBo.getType());
        deviceCompany.setBrand(companyBo.getBrand());
        int insert = this.baseMapper.insert(deviceCompany);
        if (insert == 1) {
            return R.ok("添加成功");
        }
        return R.warn("添加失败");
    }

    @Override
    public R<PageVo<DeviceCompany>> delete(CompanyBo companyBo, PageBo pageBo) {
        LambdaQueryWrapper<DeviceCompany> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DeviceCompany::getId, companyBo.getId());
        this.baseMapper.delete(wrapper);
        PageVo<DeviceCompany> list = this.getList(pageBo);
        return R.ok(list);
    }

    @Override
    public PageVo<DeviceCompany> search(String name, PageBo pageBo) {
        LambdaQueryWrapper<DeviceCompany> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null) {
            queryWrapper.eq(DeviceCompany::getName, name);
        }
        queryWrapper.orderByDesc(DeviceCompany::getId);
        List<DeviceCompany> deviceTypes = this.baseMapper.selectList(queryWrapper);
        return new PageVo<>(pageBo, deviceTypes);
    }
}




