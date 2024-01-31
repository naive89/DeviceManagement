package com.grays2.web.controller.sys;

import com.grays2.common.result.R;
import com.grays2.system.domain.Perm;
import com.grays2.system.service.PermService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(value = "权限管理", tags = {"权限管理"})
@RequestMapping(value = "/perm", method = {RequestMethod.POST, RequestMethod.GET})
public class PermController {
    @Autowired
    private PermService permService;

    @ResponseBody
    @ApiOperation("权限列表")
    @RequestMapping("/getList")
    public R<List<Perm>> getList() {
        return R.ok(permService.getList());
    }

}
