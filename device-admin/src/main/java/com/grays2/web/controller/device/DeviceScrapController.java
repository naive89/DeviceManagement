package com.grays2.web.controller.device;

import com.grays2.common.basic.MultiRequestBody;
import com.grays2.common.result.R;

import com.grays2.device.domain.DeviceScrap;
import com.grays2.device.domain.bo.PageBo;
import com.grays2.device.domain.bo.ScrapBo;
import com.grays2.device.domain.vo.PageVo;
import com.grays2.device.domain.vo.ScrapVo;
import com.grays2.device.service.DeviceScrapService;
import com.grays2.system.others.perm.PermInter;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "device/scrap", method = {RequestMethod.POST, RequestMethod.GET})
public class DeviceScrapController {

    private final DeviceScrapService deviceScrapService;

    public DeviceScrapController(DeviceScrapService deviceScrapService) {
        this.deviceScrapService = deviceScrapService;
    }

    @ResponseBody
    @ApiOperation("获取设备报废列表")
    @RequestMapping("/lists")
    public R getLists() {
        List<ScrapVo> lists = this.deviceScrapService.getLists();
        return R.ok(lists);
    }

    @ResponseBody
    @ApiOperation("获取设备报废分页")
    @RequestMapping("/getList")
    public R<PageVo<ScrapVo>> getList(@RequestBody PageBo pageBo) {
        PageVo<ScrapVo> list = this.deviceScrapService.getList(pageBo);
        return R.ok(list);
    }

    @ResponseBody
    @ApiOperation("添加设备报废对象")
    @RequestMapping("/insert")
    @PermInter(perm = ":device:scrap:insert", name = "添加设备报废", jsjb = "1")
    public R<String> insertDeviceScrap(@RequestBody ScrapBo scrapBo) {
        return this.deviceScrapService.insert(scrapBo);
    }

    @ResponseBody
    @ApiOperation("搜索设备报废类型")
    @RequestMapping("/search")
    public R<PageVo<ScrapVo>> searchDeviceScrap(@MultiRequestBody String name, @MultiRequestBody PageBo pageBo) {
        PageVo<ScrapVo> search = this.deviceScrapService.search(name, pageBo);
        return R.ok(search);
    }

    @ResponseBody
    @ApiOperation("删除设备报废对象")
    @RequestMapping("/delete")
    @PermInter(perm = ":device:scrap:delete", name = "删除设备报废", jsjb = "1")
    public R<PageVo<ScrapVo>> deleteDeviceScrap(@MultiRequestBody DeviceScrap deviceScrap, @MultiRequestBody PageBo pageBo) {
        return this.deviceScrapService.delete(deviceScrap, pageBo);
    }
}
