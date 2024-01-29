package com.grays2.system.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统字典(SysDict)表实体类
 */
@Data
@TableName("sys_dict")
@Api(value = "字典实体", tags = {"字典实体"})
public class Dict {
    @ApiModelProperty("主键")
    private Integer id;
    @ApiModelProperty("字典类型")
    private String type;
    @ApiModelProperty("字典名字")
    private String name;
    //    @ApiModelProperty("图标字典")
//    private String icon;
    @ApiModelProperty("字典内容")
    private String dict;

}

