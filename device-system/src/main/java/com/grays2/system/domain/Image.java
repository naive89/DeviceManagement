

package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("sys_image")
@Api(value = "图片实体", tags = {"图片实体"})
public class Image {
    @TableId

    @ApiModelProperty("编号")
    private Integer Id;
    @ApiModelProperty("图片数据流")
    private String image;
}
