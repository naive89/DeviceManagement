package com.grays2.device.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 维修记录
 * @TableName device_repair
 */
@TableName(value ="device_repair")
@Data
public class DeviceRepair implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备ID
     */
    private Integer deviceId;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}