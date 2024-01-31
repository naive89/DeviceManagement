package com.grays2.device.domain.vo;

import lombok.Data;

import java.util.Date;

@Data
public class DeviceMessageVo {
    /**
     * 巡检周期
     */
    private Integer inspectionCycle;

    /**
     * 类型
     */
    private Integer typeId;

    /**
     * 名称
     */
    private String name;

    /**
     * 设备编号
     */
    private Integer deviceId;

    /**
     * 设备地点
     */
    private String place;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否报废
     */
    private Integer scrap;

    /**
     * 数量
     */
    private Integer amount;

    /**
     * 条形码
     */
    private String barcode;

    /**
     * 是否巡检
     */
    private Integer inspection;

    /**
     * 安装时间
     */
    private Date installTime;

    /**
     * 到期时间
     */
    private Date expiryTime;

    /**
     * 厂商ID
     */
    private Integer companyId;

    /**
     * 出厂日期
     */
    private Date factoryDate;

    /**
     * 质保期
     */
    private String warranty;

    /**
     * 添加时间
     */
    private Date createTime;
}
