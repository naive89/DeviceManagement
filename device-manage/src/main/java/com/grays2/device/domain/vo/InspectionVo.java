package com.grays2.device.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class InspectionVo {

    private Integer id;

    /**
     * 设备ID
     */
    private Integer deviceId;
    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 巡检人
     */
    private String userName;

    /**
     * 巡检状态
     */
    private Integer status;

    /**
     * 巡检描述
     */
    private String description;

    /**
     * 巡检时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date patrolTime;

    /**
     * 填写时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;
}
