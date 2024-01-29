package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 系统公告(SysNotices)表实体类
 */
@Data
@TableName("sys_notices")
@Api(value = "系统公告实体", tags = {"系统公告实体"})
public class Notices {
    @TableId
    @ApiModelProperty("序号")
    private Integer id;
    @ApiModelProperty("公告内容")
    private String title;
    @ApiModelProperty("发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate time;
    @ApiModelProperty("图标")
    private String icon;

}

