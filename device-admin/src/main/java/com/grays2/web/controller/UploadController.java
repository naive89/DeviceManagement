package com.grays2.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.grays2.common.constant.SystemConstant;
import com.grays2.common.result.R;
import com.grays2.system.domain.vo.UserVo;
import com.grays2.system.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传操作
 **/
@RestController
@Api(value = "权限管理", tags = {"权限管理"})
@RequestMapping(value = "/upload", method = {RequestMethod.POST, RequestMethod.GET})
public class UploadController {

    @Autowired
    public ImageService imageService;

    /**
     * 文件上传工具类
     */
    private static void uploadFileUtil(MultipartFile[] files, List<String> stringList) {
        for (MultipartFile file : files) {
            String[] dayTime = LocalDateTimeUtil.formatNormal(LocalDateTime.now()).replace(":", "-").split(" ");
            String day = dayTime[0];
            String time = dayTime[1];
            String name = file.getOriginalFilename();//获取文件名
            String path = SystemConstant.FILE_Upload + day + "\\" + time + "\\" ;
            // 检测是否存在该目录
            if (!new File(path).exists()) {
                new File(path).mkdirs();
            }
            path = path + name;
            try {
                FileOutputStream fos = new FileOutputStream(path);
                fos.write(file.getBytes()); // 写入文件
                stringList.add(path);
                fos.close();
            } catch (Exception e) {
//            e.printStackTrace();
                System.out.println("Exception:" + e);
            }
        }
    }

    /* 图片 */
    @ResponseBody
    @ApiOperation("上传图片")
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public R<String> image(@RequestParam("file") MultipartFile file) {
        String username = (String) StpUtil.getLoginId();
        if (file.isEmpty()) {
            return R.fail("文件为空");
        }
        String uploadFilePath = uploadImageUtil(file);
        if ("上传失败".equals(uploadFilePath)) return R.fail(uploadFilePath);
        UserVo user = new UserVo();
        user.setUsername(username);
        user.setImage(uploadFilePath);
        imageService.changeImage(user);
        return R.ok("头像更换成功");
    }

    /**
     * 图片上传工具类
     */
    private String uploadImageUtil(MultipartFile file) {
        String path = SystemConstant.IMAGE_Upload;//想要存储文件的地址
        // 检测是否存在该目录
        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取日期，拼接到文件名中，避免文件名重复
        String today = DateUtil.today();
        fileName = SystemConstant.IMAGE_Upload + today + fileName;
        try {
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                // 写入文件
                fos.write(file.getBytes());
            }
            return fileName;
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("Exception:" + e);
        }
        return "上传失败" ;
    }

    /* 附件 */
    @ResponseBody
    @ApiOperation("上传附件")
    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public R<List<String>> file(MultipartFile[] files) {
        List<String> stringList = new ArrayList<>();
        uploadFileUtil(files, stringList);
        return R.ok("上传成功", stringList);
    }

}
