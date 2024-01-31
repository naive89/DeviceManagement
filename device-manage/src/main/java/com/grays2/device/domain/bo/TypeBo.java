package com.grays2.device.domain.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeBo {

    /**
     * 设备类型主键
     */
    private Integer id;
    /**
     * 类型名称
     */
    private String name;

    /**
     * 负责人
     */
    private String principal;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDate createTime;
}
