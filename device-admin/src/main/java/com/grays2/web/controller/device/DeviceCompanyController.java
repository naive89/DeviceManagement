package com.grays2.web.controller.device;

import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceCompany;
import com.grays2.device.domain.bo.CompanyBo;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.service.DeviceCompanyService;
import com.grays2.system.others.perm.PermInter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "device/company", method = {RequestMethod.POST, RequestMethod.GET})
public class DeviceCompanyController {

    private final DeviceCompanyService deviceCompanyService;

    public DeviceCompanyController(DeviceCompanyService deviceCompanyService) {
        this.deviceCompanyService = deviceCompanyService;
    }

    @ResponseBody
    @ApiOperation("获取供应商列表")
    @RequestMapping("/lists")
    public R getLists() {
        List<DeviceCompany> lists = this.deviceCompanyService.getLists();
        return R.ok(lists);
    }

    @ResponseBody
    @ApiOperation("获取供应商分页")
    @RequestMapping("/getList")
    public R<PageVo<DeviceCompany>> getList(@RequestBody PageBo pageBo) {
        PageVo<DeviceCompany> list = this.deviceCompanyService.getList(pageBo);
        return R.ok(list);
    }


    @ResponseBody
    @ApiOperation("插入供应商对象")
    @RequestMapping("/insert")
    @PermInter(perm = ":device:company:insert", name = "添加供应商", jsjb = "1")
    public R<String> insertDeviceType(@RequestBody CompanyBo companyBo) {
        return this.deviceCompanyService.insert(companyBo);
    }

    @ResponseBody
    @ApiOperation("搜索供应商名称")
    @RequestMapping("/search")
    public R<PageVo<DeviceCompany>> searchDeviceType(@MultiRequestBody String name, @MultiRequestBody PageBo pageBo) {
        PageVo<DeviceCompany> search = this.deviceCompanyService.search(name, pageBo);
        return R.ok(search);
    }

    @ResponseBody
    @ApiOperation("删除设备类型对象")
    @RequestMapping("/delete")
    @PermInter(perm = ":device:company:delete", name = "删除设备类型", jsjb = "1")
    public R<PageVo<DeviceCompany>> deleteDeviceType(@MultiRequestBody CompanyBo companyBo, @MultiRequestBody PageBo pageBo) {
        return this.deviceCompanyService.delete(companyBo, pageBo);
    }
}
