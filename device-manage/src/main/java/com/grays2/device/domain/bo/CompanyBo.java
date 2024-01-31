package com.grays2.device.domain.bo;

import lombok.Data;

@Data
public class CompanyBo {
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 电话
     */
    private String phone;

    /**
     * 型号
     */
    private String type;

    /**
     * 品牌
     */
    private String brand;
}
