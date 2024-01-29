package com.grays2.system.domain.bo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Api(value = "分页实体", tags = {"分页实体"})
public class PageBo {
    @ApiModelProperty("当前页码")
    private Integer pageNum;
    @ApiModelProperty("页码尺寸")
    private Integer pageSize;
}
