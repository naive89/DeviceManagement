package com.grays2.web.controller;

import com.grays2.common.result.R;
import com.grays2.system.domain.Log;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(value = "日志管理", tags = {"日志管理"})
@RequestMapping(value = "/log", method = {RequestMethod.POST, RequestMethod.GET})
public class LogController {

    @Autowired
    private LogService logService;


    @ResponseBody
    @ApiOperation("获取日志列表")
    @RequestMapping("/getList")
    public R<PageVo<Log>> getList(@RequestBody PageBo pageBo) {
        PageVo<Log> list = logService.getList(pageBo);
        return R.ok(list);
    }

    @ResponseBody
    @ApiOperation("搜索日志列表")
    @RequestMapping("/getList/search/{name}")
    public R<PageVo<Log>> getListSearch(@PathVariable String name, @RequestBody PageBo pageBo) {
        PageVo<Log> logs = logService.getListSearch(name, pageBo);
        return R.ok(logs);
    }
}
