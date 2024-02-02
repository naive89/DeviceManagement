package com.grays2.device.domain.bo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RepairBo {
    /**
     * 设备ID
     */
    private Integer deviceMsgId;

    /**
     * 送修人ID
     */
    private Integer userId;

    /**
     * 故障时间
     */
    private Date faultTime;

    /**
     * 修复时间
     */
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
     * 设备供应商ID
     */
    private Integer deviceFactory;
}
