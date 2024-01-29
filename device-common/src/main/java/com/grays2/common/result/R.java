package com.grays2.common.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class R<T> implements Serializable {
    /**
     * 成功
     */
    public static final int SUCCESS = 200;
    /**
     * 警告
     */
    public static final int WARN = 400;
    /**
     * 提示
     */
    public static final int INFO = 200;
    /**
     * 失败
     */
    public static final int FAIL = 500;
    /**
     * token失效
     */
    public static final int TOKEN_TIMEOUT = 11012;
    /**
     * 成功
     */
    public static final String TYPE_SUCCESS = "success" ;
    /**
     * 警告
     */
    public static final String TYPE_WARN = "warning" ;
    /**
     * 提示
     */
    public static final String TYPE_Info = "info" ;
    /**
     * 失败
     */
    public static final String TYPE_FAIL = "error" ;

    @ApiModelProperty("返回的信息类型")
    private String type;
    @ApiModelProperty("消息状态码")
    private int code;
    @ApiModelProperty("消息内容")
    private String message;
    @ApiModelProperty("数据对象")
    private T data;

    public static <T> R<T> ok(T data) {
        return restResult(data, SUCCESS, null, TYPE_SUCCESS);
    }

    public static <T> R<T> ok() {
        return restResult(null, SUCCESS, "操作成功", TYPE_SUCCESS);
    }

    public static <T> R<T> ok(String message) {
        return restResult(null, SUCCESS, message, TYPE_SUCCESS);
    }

    public static <T> R<T> ok(String message, T data) {
        return restResult(data, SUCCESS, message, TYPE_SUCCESS);
    }

    public static <T> R<T> fail(String message) {
        return restResult(null, FAIL, message, TYPE_FAIL);
    }

    public static <T> R<T> token() {
        return restResult(null, TOKEN_TIMEOUT, "登录信息已过期，请重新登陆", TYPE_WARN);
    }

    public static <T> R<T> warn(String message) {
        return restResult(null, WARN, message, TYPE_WARN);
    }

    public static <T> R<T> info(String message) {
        return restResult(null, INFO, message, TYPE_Info);
    }

    public static <T> R<T> info(String message, T data) {
        return restResult(data, INFO, message, TYPE_Info);
    }

    public static <T> R<T> error(int code, String msg) {
        return restResult(null, code, msg, TYPE_FAIL);
    }

    private static <T> R<T> restResult(T data, int code, String msg, String type) {
        R<T> r = new R<>();
        r.setData(data);
        r.setCode(code);
        r.setMessage(msg);
        r.setType(type);
        return r;
    }

}
