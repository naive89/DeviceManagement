package com.grays2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@MapperScan(value = {"com.grays2.*.mapper"})
public class DeviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DeviceApplication.class, args);
        System.out.println("     (♥◠‿◠)ﾉﾞ         企业设备管理系统启动成功         ლ(´ڡ`ლ)ﾞ  ");
    }
}
