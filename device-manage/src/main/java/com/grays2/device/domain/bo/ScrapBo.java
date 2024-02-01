package com.grays2.device.domain.bo;

import lombok.Data;


import java.util.Date;

@Data
public class ScrapBo {

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
     * 报废时间
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
}
