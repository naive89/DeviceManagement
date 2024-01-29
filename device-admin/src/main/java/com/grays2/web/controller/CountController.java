package com.grays2.web.controller;

import com.grays2.common.result.R;
import com.grays2.system.service.CountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Api(value = "数据统计管理", tags = {"数据统计管理"})
@RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
public class CountController {

    @Autowired
    private CountService countService;

    @ResponseBody
    @RequestMapping("/project/line")
    @ApiOperation("各种类科研项目的发表-折线图")
    public R<Map<String, Object>> getProjectLine() {
        return R.ok(countService.getProjectLine());
    }


    @ResponseBody
    @ApiOperation("用户注册趋势图")
    @RequestMapping("/UserInsert")
    public R<List<Map<String, Integer>>> getUserInsert() {
        return R.ok(countService.getUserInsert());
    }

    @ResponseBody
    @ApiOperation("项目贡献度")
    @RequestMapping("/Contribution")
    public R<Map<String, Object>> getContribution() {
        return R.ok(countService.getContribution());
    }


}
