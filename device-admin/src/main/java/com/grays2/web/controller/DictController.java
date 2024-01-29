package com.grays2.web.controller;

import com.grays2.common.result.R;
import com.grays2.system.domain.Dict;
import com.grays2.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "字典管理", tags = {"字典管理"})
@RequestMapping(value = "/dict", method = {RequestMethod.POST, RequestMethod.GET})
public class DictController {
    @Autowired
    private DictService dictService;

    @ResponseBody
    @ApiOperation("获取字典")
    @RequestMapping("/getDict/{dict}")
    public R<List<Dict>> getDict(@PathVariable("dict") String dict) {
        return R.ok(dictService.getDict(dict));
    }

}