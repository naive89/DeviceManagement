package com.grays2.web.controller.device;

import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceInspection;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.vo.InspectionVo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.service.DeviceInspectionService;
import com.grays2.system.others.perm.PermInter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "device/inspection", method = {RequestMethod.POST, RequestMethod.GET})
public class DeviceInspectionController {

    private final DeviceInspectionService deviceInspectionService;

    public DeviceInspectionController(DeviceInspectionService deviceInspectionService) {
        this.deviceInspectionService = deviceInspectionService;
    }

    @ResponseBody
    @ApiOperation("获取巡检列表")
    @RequestMapping("/lists")
    public R getLists() {
        List<InspectionVo> lists = this.deviceInspectionService.getLists();
        return R.ok(lists);
    }

    @ResponseBody
    @ApiOperation("获取巡检分页")
    @RequestMapping("/getList")
    public R<PageVo<InspectionVo>> getList(@RequestBody PageBo pageBo) {
        PageVo<InspectionVo> list = this.deviceInspectionService.getList(pageBo);
        return R.ok(list);
    }


    @ResponseBody
    @ApiOperation("插入巡检对象")
    @RequestMapping("/insert")
    @PermInter(perm = ":device:inspection:insert", name = "添加巡检信息", jsjb = "1")
    public R<String> insertDeviceInspection(@RequestBody DeviceInspection inspection) {
        return this.deviceInspectionService.insert(inspection);
    }

    @ResponseBody
    @ApiOperation("搜索巡检信息")
    @RequestMapping("/search")
    public R<PageVo<InspectionVo>> searchDeviceInspection(@MultiRequestBody String name, @MultiRequestBody PageBo pageBo) {
        PageVo<InspectionVo> search = this.deviceInspectionService.search(name, pageBo);
        return R.ok(search);
    }

    @ResponseBody
    @ApiOperation("删除巡检信息对象")
    @RequestMapping("/delete")
    @PermInter(perm = ":device:inspection:delete", name = "删除巡检信息", jsjb = "1")
    public R<PageVo<InspectionVo>> deleteDeviceInspection(@MultiRequestBody DeviceInspection inspection, @MultiRequestBody PageBo pageBo) {
        return this.deviceInspectionService.delete(inspection, pageBo);
    }
}
