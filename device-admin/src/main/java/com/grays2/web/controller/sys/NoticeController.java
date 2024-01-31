package com.grays2.web.controller.sys;

import com.grays2.common.result.R;
import com.grays2.system.domain.Notices;
import com.grays2.system.domain.bo.NoticesBo;
import com.grays2.system.domain.bo.PageBo;
import com.grays2.system.domain.vo.PageVo;
import com.grays2.system.service.NoticeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知操作
 **/
@RestController
@Api(value = "通知管理", tags = {"通知管理"})
@RequestMapping(value = "/notice", method = {RequestMethod.POST, RequestMethod.GET})
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ResponseBody
    @RequestMapping("/getList/Index")
    public R<List<Notices>> getListIndex() {
        return R.ok(noticeService.getListIndex());
    }

    @ResponseBody
    @RequestMapping("/getList")
    public R<PageVo<Notices>> getList(@RequestBody PageBo pageBo) {
        return R.ok(noticeService.getList(pageBo));
    }

    @ResponseBody
    @RequestMapping("/insert")
    public R<String> insert(@RequestBody NoticesBo bo) {
        noticeService.insert(bo);
        return R.ok("添加成功");
    }
}
