package com.grays2.web.controller.device;

import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;

import com.grays2.device.domain.DeviceMessage;

import com.grays2.device.domain.bo.PageBo;

import com.grays2.device.domain.vo.DeviceMessageVo;
import com.grays2.device.domain.vo.PageVo;

import com.grays2.device.service.DeviceMessageService;
import com.grays2.system.others.perm.PermInter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "device/message", method = {RequestMethod.POST, RequestMethod.GET})
public class DeviceMessageController {

    private final DeviceMessageService deviceMessageService;

    public DeviceMessageController(DeviceMessageService deviceMessageService) {
        this.deviceMessageService = deviceMessageService;
    }

    @ResponseBody
    @ApiOperation("获取设备信息列表")
    @RequestMapping("/lists")
    public R getLists() {
        List<DeviceMessageVo> lists = this.deviceMessageService.getLists();
        return R.ok(lists);
    }

    @ResponseBody
    @ApiOperation("获取设备信息分页")
    @RequestMapping("/getList")
    public R<PageVo<DeviceMessageVo>> getList(@RequestBody PageBo pageBo) {
        PageVo<DeviceMessageVo> list = this.deviceMessageService.getList(pageBo);
        return R.ok(list);
    }

    @ResponseBody
    @ApiOperation("添加设备信息对象")
    @RequestMapping("/insert")
    @PermInter(perm = ":device:message:insert", name = "添加设备设备信息", jsjb = "1")
    public R<String> insertDeviceMessage(@RequestBody DeviceMessage message) {
        return this.deviceMessageService.insert(message);
    }

    @ResponseBody
    @ApiOperation("搜索设备信息")
    @RequestMapping("/search")
    public R<PageVo<DeviceMessageVo>> searchDeviceMessage(@MultiRequestBody String name, @MultiRequestBody PageBo pageBo) {
        PageVo<DeviceMessageVo> search = this.deviceMessageService.search(name, pageBo);
        return R.ok(search);
    }

    @ResponseBody
    @ApiOperation("删除设备信息对象")
    @RequestMapping("/delete")
    @PermInter(perm = ":device:message:delete", name = "删除设备信息", jsjb = "1")
    public R<PageVo<DeviceMessageVo>> deleteDeviceMessage(@MultiRequestBody DeviceMessageVo deviceScrap, @MultiRequestBody PageBo pageBo) {
        return this.deviceMessageService.delete(deviceScrap, pageBo);
    }
}
