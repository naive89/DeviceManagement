package com.grays2.system.domain.vo;


import com.github.pagehelper.Page;
import com.grays2.system.domain.bo.PageBo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@Api(value = "分页实体", tags = {"分页实体"})
public class PageVo<E> {
    @ApiModelProperty("数据集合")
    private List<?> list;
    @ApiModelProperty("总页数")
    private long total;

    public PageVo(Page<?> page) {
        this.list = page.getResult();
        this.total = page.getTotal();
    }

    public PageVo(List<?> list) {
        this.list = list;
        this.total = list.size();
    }

    public PageVo(PageBo pageBo, List<?> list) {
        this.total = list.size();
        int begin = (pageBo.getPageNum() - 1) * pageBo.getPageSize();
        int end = Math.min(pageBo.getPageNum() * pageBo.getPageSize(), list.size());
        while (begin >= end && begin != 0) {
            pageBo.setPageNum(pageBo.getPageNum() - 1);
            begin = (pageBo.getPageNum() - 1) * pageBo.getPageSize();
            end = Math.min(pageBo.getPageNum() * pageBo.getPageSize(), list.size());
        }
        this.list = list.subList(begin, end);
    }

    public PageVo() {
    }
}
