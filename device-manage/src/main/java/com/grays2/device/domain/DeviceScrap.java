package com.grays2.device.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 设备报废
 * @TableName device_scrap
 */
@TableName(value ="device_scrap")
@Data
public class DeviceScrap implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 设备Id
     */
    private Integer deviceId;

    /**
     * 报废用户Id
     */
    private Integer userId;

    /**
     * 报废日期
     */
    private Date scrapTime;

    /**
     * 报废类型
     */
    private String type;

    /**
     * 废品去向
     */
    private String scrapDestination;

    /**
     * 处理收支
     */
    private String handle;

    /**
     * 备注
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}