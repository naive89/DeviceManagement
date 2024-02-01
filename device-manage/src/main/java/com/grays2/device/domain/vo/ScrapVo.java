package com.grays2.device.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ScrapVo {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 设备Id
     */
    private Integer deviceId;

    /**
     * 设备名称
     */
    private String deviceName;

    /**
     * 用户名称
     */
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate scrapTime;

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
