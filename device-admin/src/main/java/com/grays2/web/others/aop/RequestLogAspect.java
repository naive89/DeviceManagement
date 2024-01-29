package com.grays2.web.others.aop;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.grays2.common.constant.SystemConstant;
import com.grays2.common.result.R;
import com.grays2.common.utils.IPUtils;
import com.grays2.system.domain.Log;
import com.grays2.system.domain.model.UserPerm;
import com.grays2.system.mapper.UserPermMapper;
import com.grays2.system.others.perm.PermInter;
import com.grays2.system.service.LogService;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
public class RequestLogAspect {
    public final RequestLogAspect requestLogAspect = this;
    private final Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);
    @Autowired
    private LogService logService;
    @Autowired
    private UserPermMapper userPermMapper;

    @Pointcut("execution(* com.grays2.web.controller..*(..))")
    public void requestServer() {
    }

    @SneakyThrows
    @Around("requestServer()")
    public Object doAround(ProceedingJoinPoint pjp) {
        //记录请求开始执行时间：
        long beginTime = System.currentTimeMillis();
        //获取请求信息
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();
        //检查账号权限
        this.preHandle(pjp);
        String ip = IPUtils.getIp(request);
        String innerIp = IPUtils.innerIp(ip);
        String URL = request.getRequestURL().toString();
        String method = request.getMethod();
        String URI = request.getRequestURI();
        String methodName = pjp.getSignature().getName();
        String clazzName = pjp.getTarget().getClass().getSimpleName();
        //获取请求参数：
        MethodSignature ms = (MethodSignature) pjp.getSignature();
        //获取请求参数类型
        String[] parameterNames = ms.getParameterNames();
        //获取请求参数值
        Object[] parameterValues = pjp.getArgs();
        StringBuilder params = new StringBuilder();
        if (!Arrays.asList(SystemConstant.NotOutputAndRecord).contains(URI)) {
            //组合请求参数，进行日志打印
            if (parameterNames != null && parameterNames.length > 0) {
                for (int i = 0; i < parameterNames.length; i++) {
                    if (parameterNames[i].equals("bindingResult")) {
                        break;
                    }
                    if ((parameterValues[i] instanceof HttpServletRequest) || (parameterValues[i] instanceof HttpServletResponse)) {
                        params.append("[").append(parameterNames[i]).append("=").append(parameterValues[i]).append("]");
                    } else {
                        params.append("[").append(parameterNames[i]).append("=").append(JSON.toJSONString(parameterValues[i], SerializerFeature.WriteDateUseDateFormat)).append("]");
                    }
                }
            }
        }
        Object result;
        try {
            result = pjp.proceed();
        } catch (Throwable throwable) {
            //请求操纵失败
            //记录错误日志
            logger.error("ε=ε=ε=ε=ε=ε=┌(;￣◇￣)┘          切面处理请求错误！ IP信息(ง•̀_•́)ง->： 【{}】 " + "URI信息(ง•̀_•́)ง->：【{}】 请求映射控制类(ง•̀_•́)ง->：【{}】 " + "请求方法(ง•̀_•́)ง->：【{}】 请求设备(ง•̀_•́)ง->：【{}】 " + "请求参数列表(ง•̀_•́)ง->：【{}】", ip, URI, clazzName, "", methodName, params);
            throw throwable;
        }
        R<?> resultR = (R<?>) result;
        String type = resultR.getType();
        int code = resultR.getCode();
        Object data = resultR.getData();
        String message = resultR.getMessage();
        //请求操作成功
        String resultJsonString = "";
        if (data != null) {
            if (data instanceof HttpServletResponse) {
                resultJsonString = JSON.toJSONString(data, SerializerFeature.WriteDateUseDateFormat);
            } else {
                resultJsonString = String.valueOf(data);
            }
        }
        //记录请求完成执行时间：
        long endTime = System.currentTimeMillis();
        long time = endTime - beginTime;
        //删除返回结果中的换行
        resultJsonString = resultJsonString.replace("\n", "");
        String[] passMethod = SystemConstant.passMethod;
        if (Arrays.asList(passMethod).contains(methodName)) return result;
        //记录日志
        logger.info("请求操作成功！ 请求耗时：【{}】毫秒  请求方式(◍'౪`◍)ﾉﾞ->： 【{}】 " + "IP信息(◍'౪`◍)ﾉﾞ->： 【{}】  URI信息(◍'౪`◍)ﾉﾞ->：【{}】 " + "请求映射控制类(◍'౪`◍)ﾉﾞ->：【{}】 请求方法(◍'౪`◍)ﾉﾞ->：【{}】 " + "请求设备(ง•̀_•́)ง->：【{}】 请求参数列表(◍'౪`◍)ﾉﾞ->：【{}】 " + "返回值(ฅ´ω`ฅ)->：【{}】 " + "返回状态值(ฅ´ω`ฅ)->：【{}】 " + "返回提示◔ ‸◔->：【{}】 " + "返回提示类型 ≖‿≖✧->：【{}】 ", time, method, ip, URI, clazzName, methodName, "mobile", params, resultJsonString, code, message, type);
        Log log = new Log();
        try {
            //操作用户
            String account = (String) StpUtil.getLoginId();
            log.setAccount(account);
            //请求方式
            log.setMethod(method);
            //URI 请求接口
            log.setUri(URI);
            //URL 请求地址
            log.setUrl(URL);
            //请求来源
            log.setInnerIp(innerIp);
            //请求映射控制类
            log.setControlClass(clazzName);
            //ip
            log.setIp(ip);
            //请求参数
            log.setParams(params.toString());
            //执行时间
            log.setTime((double) time);
            //创建时间
            log.setCreateDate(LocalDateTime.now());
            //返回状态
            log.setCode(code);
            //返回提示
            log.setMessage(message);
            //返回提示类型
            log.setType(type);
            //返回结果
            int maxLen = SystemConstant.resultMaxLen;
            if (resultJsonString.length() > maxLen) {
                resultJsonString = resultJsonString.substring(0, maxLen - 10000) + "..";
            }
            log.setResult(resultJsonString);
            requestLogAspect.logService.insertLog(log);
        } catch (Exception ignored) {
        }
        return result;

    }

    public void preHandle(ProceedingJoinPoint pjp) throws Exception {
        PermInter permInter = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(PermInter.class);
        //如果没有添加权限注解则直接跳过允许访问
        if (permInter == null) {
            return;
        }
        //获取注解中的值
        String perm = permInter.perm();
        String zh = (String) StpUtil.getLoginId();
        LambdaQueryWrapper<UserPerm> lqw = new LambdaQueryWrapper<>();
        lqw.eq(UserPerm::getZh, zh);
        List<String> list = userPermMapper.selectList(lqw).stream().map(UserPerm::getPerm).collect(Collectors.toList());
        if (list.contains(perm)) {
            return;
        }
        throw new Exception("您没有 '" + permInter.name() + "' 权限！");
    }

}