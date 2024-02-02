package com.grays2.web.controller.device;

import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;
import com.grays2.device.domain.DeviceRepair;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.RepairBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.domain.vo.RepairVo;
import com.grays2.device.service.DeviceRepairService;
import com.grays2.system.others.perm.PermInter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "device/repair", method = {RequestMethod.POST, RequestMethod.GET})
public class DeviceRepairController {

    private final DeviceRepairService deviceRepairService;

    public DeviceRepairController(DeviceRepairService deviceRepairService) {
        this.deviceRepairService = deviceRepairService;
    }

    @ResponseBody
    @ApiOperation("获取维修信息列表")
    @RequestMapping("/lists")
    public R getLists() {
        List<RepairVo> lists = this.deviceRepairService.getLists();
        return R.ok(lists);
    }

    @ResponseBody
    @ApiOperation("获取维修信息分页")
    @RequestMapping("/getList")
    public R<PageVo<RepairVo>> getList(@RequestBody PageBo pageBo) {
        PageVo<RepairVo> list = this.deviceRepairService.getList(pageBo);
        return R.ok(list);
    }

    @ResponseBody
    @ApiOperation("添加维修信息对象")
    @RequestMapping("/insert")
    @PermInter(perm = ":device:repair:insert", name = "添加维修信息", jsjb = "1")
    public R<String> insertDeviceMessage(@RequestBody RepairBo repairBo) {
        return this.deviceRepairService.insert(repairBo);
    }

    @ResponseBody
    @ApiOperation("搜索维修信息")
    @RequestMapping("/search")
    public R<PageVo<RepairVo>> searchDeviceMessage(@MultiRequestBody String name, @MultiRequestBody PageBo pageBo) {
        PageVo<RepairVo> search = this.deviceRepairService.search(name, pageBo);
        return R.ok(search);
    }

    @ResponseBody
    @ApiOperation("删除维修信息对象")
    @RequestMapping("/delete")
    @PermInter(perm = ":device:repair:delete", name = "删除维修信息", jsjb = "1")
    public R<PageVo<RepairVo>> deleteDeviceMessage(@MultiRequestBody DeviceRepair repair, @MultiRequestBody PageBo pageBo) {
        return this.deviceRepairService.delete(repair, pageBo);
    }
}
