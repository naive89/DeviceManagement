package com.grays2.device.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RepairVo {

    /**
     * 序号
     */
    private Integer id;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 故障时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date faultTime;

    /**
     * 修复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date repairTime;

    /**
     * 维修部位
     */
    private String repairParts;

    /**
     * 维修状态
     */
    private String repairStatus;

    /**
     * 故障原因
     */
    private String failureCause;

    /**
     * 备注
     */
    private String remark;

    /**
     * 维修方式
     */
    private Integer method;

    /**
     * 维修厂家
     */
    private String factory;

    /**
     * 维修费用
     */
    private BigDecimal cost;
    /**
     * 供应商名称
     */
    private String deviceFactoryName;
}
