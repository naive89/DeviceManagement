package com.grays2.system.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectKind {
    @ApiModelProperty("数量")
    private Integer value;
    @ApiModelProperty("种类名字")
    private String name;
    @ApiModelProperty("颜色")
    private String itemStyle;

}
