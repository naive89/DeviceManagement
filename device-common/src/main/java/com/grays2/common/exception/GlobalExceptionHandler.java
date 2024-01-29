package com.grays2.common.exception;



import com.grays2.common.result.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * SaToken全局异常拦截
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常拦截，鉴权失败不会报错，会返回给前端报错原因
     */
    @ExceptionHandler
    public R<String> handlerException(Exception e) {
        String error = e.toString();
        if (error.startsWith("cn.dev33.satoken.exception.NotLoginException: Token无效：")) {
            //token无效
            return R.token();
        }
        return R.warn(error);
    }

}

