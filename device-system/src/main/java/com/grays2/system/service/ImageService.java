package com.grays2.system.service;


import com.grays2.system.domain.Image;
import com.grays2.system.domain.vo.UserVo;

public interface ImageService {
    Image getLast();

    void changeImage(UserVo user);
}
