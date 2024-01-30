package com.grays2.device.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 巡检信息
 * @TableName device_inspection
 */
@TableName(value ="device_inspection")
@Data
public class DeviceInspection implements Serializable {
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
     * 巡检人
     */
    private Integer userId;

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
    private Date patrolTime;

    /**
     * 填写时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}