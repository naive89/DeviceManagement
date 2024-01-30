package com.grays2.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_log")
@Api(value = "日志实体", tags = {"日志实体"})
public class Log {

    @ApiModelProperty("主键")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty("操作账户")
    private String account;
    @ApiModelProperty("请求方式")
    private String method;
    @ApiModelProperty("请求地址")
    private String url;
    @ApiModelProperty("请求接口")
    private String uri;
    @ApiModelProperty("请求参数")
    private String params;
    @ApiModelProperty("执行时长")
    private Double time;
    @ApiModelProperty("ip地址")
    private String ip;
    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;
    @ApiModelProperty("请求映射控制类")
    private String ControlClass;
    @ApiModelProperty("请求来源")
    private String innerIp;
    @ApiModelProperty("返回提示类型")
    private String type;
    @ApiModelProperty("返回状态")
    private Integer code;
    @ApiModelProperty("返回提示")
    private String message;
    @ApiModelProperty("返回结果")
    private String result;

    public Integer getId() {
        return id;
    }

    public Log setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return account;
    }

    public Log setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getMethod() {
        return method;
    }

    public Log setMethod(String method) {
        this.method = method;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Log setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public Log setUri(String uri) {
        this.uri = uri;
        return this;
    }

    public String getParams() {
        return params;
    }

    public Log setParams(String params) {
        this.params = params;
        return this;
    }

    public Double getTime() {
        return time;
    }

    public Log setTime(Double time) {
        this.time = time;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Log setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Log setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public String getControlClass() {
        return ControlClass;
    }

    public Log setControlClass(String controlClass) {
        ControlClass = controlClass;
        return this;
    }

    public String getInnerIp() {
        return innerIp;
    }

    public Log setInnerIp(String innerIp) {
        this.innerIp = innerIp;
        return this;
    }

    public String getType() {
        return type;
    }

    public Log setType(String type) {
        this.type = type;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public Log setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Log setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getResult() {
        return result;
    }

    public Log setResult(String result) {
        this.result = result;
        return this;
    }
}
