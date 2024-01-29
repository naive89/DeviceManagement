package com.grays2.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import com.grays2.system.domain.Image;
import com.grays2.system.domain.vo.UserVo;
import com.grays2.system.mapper.ImageMapper;
import com.grays2.system.mapper.UserMapper;
import com.grays2.system.service.ImageService;
import lombok.SneakyThrows;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 文件File类型转byte[]
     */
    public static byte[] fileToByte(File file) {
        //        byte[] fileBytes = null;
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(file);
//            fileBytes = new byte[(int) file.length()];
//            fis.read(fileBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            fis.close();
//        }
//        return fileBytes;
        return new byte[(int) file.length()];
    }

    @Override
    public Image getLast() {
        return imageMapper.getLast();
    }

    @SneakyThrows
    @Override
    public void changeImage(UserVo vo) {
        //照片地址
        String uploadFilePath = vo.getImage();
        //调用第三方服务类，将照片转码为base64
        File imgFile = new File(uploadFilePath);//待处理的图片地址
        String imageBase64 = Base64.encodeBase64String(fileToByte(imgFile));
        //查看图片表是否有该数据流
        int image_id;
        Image image = imageMapper.selectOne(new LambdaQueryWrapper<Image>().in(Image::getImage, imageBase64));
        if (image == null) {
            //todo 获取头像表最大的iD
            //数据库中不存在此图片，向表格中添加照片数据流
            Image last = getLast();
            image_id = last.getId() + 1;
            last.setId(image_id);
            last.setImage(imageBase64);
            imageMapper.insert(last);
            //获取Id
        } else {
            //存在照片,获取ID
            image_id = image.getId();
        }
        //删除生成的图片
//        imgFile.delete();
        userMapper.updateImage(vo.getUsername(), image_id);
    }
}
