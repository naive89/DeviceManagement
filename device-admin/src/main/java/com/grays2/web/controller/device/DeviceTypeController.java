package com.grays2.web.controller.device;


import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceType;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.TypeBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.service.DeviceTypeService;
import com.grays2.system.others.perm.PermInter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 设备类型
 */
@RestController
@RequestMapping(value = "device/type", method = {RequestMethod.POST, RequestMethod.GET})
public class DeviceTypeController {

    private final DeviceTypeService deviceTypeService;

    public DeviceTypeController(DeviceTypeService deviceTypeService) {
        this.deviceTypeService = deviceTypeService;
    }

    @ResponseBody
    @ApiOperation("获取设备类型列表")
    @RequestMapping("/lists")
    public R getLists() {
        List<DeviceType> lists = this.deviceTypeService.getLists();
        return R.ok(lists);
    }

    @ResponseBody
    @ApiOperation("获取设备类型分页")
    @RequestMapping("/getList")
    public R<PageVo<DeviceType>> getList(@RequestBody PageBo pageBo) {
        PageVo<DeviceType> list = this.deviceTypeService.getList(pageBo);
        return R.ok(list);
    }


    @ResponseBody
    @ApiOperation("插入设备类型对象")
    @RequestMapping("/insert")
    @PermInter(perm = ":device:type:insert", name = "添加设备类型", jsjb = "1")
    public R<String> insertDeviceType(@RequestBody TypeBo typeBo) {
        return this.deviceTypeService.insert(typeBo);
    }

    @ResponseBody
    @ApiOperation("编辑设备类型对象")
    @RequestMapping("/update")
    public R<String> updateDeviceType(@RequestBody TypeBo typeBo) {
        return this.deviceTypeService.updateInfo(typeBo);
    }

    @ResponseBody
    @ApiOperation("搜索设备类型名称")
    @RequestMapping("/search")
    public R<PageVo<DeviceType>> searchDeviceType(@MultiRequestBody String name, @MultiRequestBody PageBo pageBo) {
        PageVo<DeviceType> search = this.deviceTypeService.search(name, pageBo);
        return R.ok(search);
    }

    @ResponseBody
    @ApiOperation("删除设备类型对象")
    @RequestMapping("/delete")
    @PermInter(perm = ":device:type:delete", name = "删除设备类型", jsjb = "1")
    public R<PageVo<DeviceType>> deleteDeviceType(@MultiRequestBody TypeBo typeBo, @MultiRequestBody PageBo pageBo) {
        return this.deviceTypeService.deleteDeviceType(typeBo, pageBo);
    }
}
