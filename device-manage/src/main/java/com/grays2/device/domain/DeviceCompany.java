package com.grays2.device.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 设备厂商
 * @TableName device_company
 */
@TableName(value ="device_company")
@Data
public class DeviceCompany implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 型号
     */
    private String type;

    /**
     * 品牌
     */
    private String brand;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}