package com.grays2.system.service.impl;

import cn.hutool.core.codec.Base64;

import com.grays2.common.constant.RedisConstant;
import com.grays2.common.redis.RedisUtils;
import com.grays2.common.result.R;
import com.grays2.common.utils.CodeUtils;
import com.grays2.common.utils.GzipUtils;
import com.grays2.common.utils.IPUtils;
import com.grays2.system.service.CodeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class CodeServiceImpl implements CodeService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private GzipUtils gzipUtils;
    @Autowired
    private HttpServletRequest request;

    @Override
    @SneakyThrows
    public R<Map<String, Object>> getCode() {
        //生成验证码
        CodeUtils code = new CodeUtils();
        BufferedImage image = code.getImage();
        String text = code.getText();
        //获取IP，增加验证码缓存K值的拓展性
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        String ip = IPUtils.getIp(request);
        //将验证码保存到缓存中
        long time = RedisConstant.code_Time;
        redisUtils.set(RedisConstant.code + ":" + ip, text, time);
        //将图片转换陈字符串给前端
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "png", stream);
        String base64 = Base64.encode(stream.toByteArray());
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("code", text);
//        resultMap.put("image", base64);
        resultMap.put("image", gzipUtils.compress(base64));
        return R.ok(resultMap);
    }

    @Override
    public R<String> getCodeMail(String qq) {
        qq = qq.replace("QQ", "qq");
        //检查qq邮箱格式
        if (!(qq.endsWith("@qq.com"))) {
            return R.fail("请输入qq邮箱");
        }
        if (qq.replace("@qq.com", "").length() < 10) {
            return R.fail("qq邮箱格式不正确");
        }
        String text = (String) this.getCode().getData().get("code");
        //TODO 未处理
        return R.ok();
    }
}
