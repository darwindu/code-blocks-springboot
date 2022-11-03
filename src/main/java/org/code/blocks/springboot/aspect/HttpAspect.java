package org.code.blocks.springboot.aspect;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.code.blocks.common.constant.CommonConstant;
import org.code.blocks.common.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统一日志处理
 * @author darwindu
 * @date 2020/8/25
 **/
@Aspect
@Component
@Slf4j
public class HttpAspect {

    @Autowired
    private HttpServletRequest request;

    @Pointcut("execution(public * org.code.blocks.springboot.controller.*.*(..))")
    public void log() {}

    @Around("log()")
    public Object aroundLogCalls(ProceedingJoinPoint jp) throws Throwable {

        String userIdClassMethod = this.getUserIdClassMehtod();

        log.info("### userIdClassMethod:[{}], aop Around start...", userIdClassMethod);
        long startTime = System.currentTimeMillis();
        Object o;
        try {
            o = jp.proceed();
        } catch (Throwable throwable) {
            log.info("### userIdClassMethod:[{}], aop Around end... cost: {} millisecond", userIdClassMethod, this.getCost(startTime));
            throw throwable;
        }
        log.info("### userIdClassMethod:[{}], aop Around end... cost: {} millisecond", userIdClassMethod, this.getCost(startTime));
        return o;
    }

    private long getCost(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    private String getUserIdClassMehtod() {
        String userId = request.getHeader("userId");
        String classMethod = request.getRequestURI();
        return this.getUserIdClassMehtod(userId, classMethod);
    }

    private String getUserIdClassMehtod(String userId, String classMethod) {
        userId = null == userId ? "" : userId;
        classMethod = null == classMethod ? "" : classMethod;
        StringBuffer sb = new StringBuffer(userId).append(CommonConstant.SYMBOL_HORIZONTAL).append(classMethod);
        return sb.toString();
    }

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {

        String userId = request.getHeader("userId");
        // 类方法
        String classMethod = new StringBuffer(joinPoint.getSignature().getDeclaringTypeName()).append(
            CommonConstant.SYMBOL_POINT).append(joinPoint.getSignature().getName()).toString();

        String userIdClassMethod = this.getUserIdClassMehtod(userId, classMethod);

        // 客户端类型: 1=web，2=小程序
        String clientType = request.getHeader("clientType");
        // 客户端类型 User-Agent
        String userAgent = request.getHeader("User-Agent");
        log.info("### userIdClassMethod:[{}] aop before start..., User-Agent:{}", userIdClassMethod, userAgent);
        // url 格式：http://localhost:2020/wsnas/notar/getFlowApplyDetail
        //StringBuffer url = request.getRequestURL();
        // uri 格式：/wsnas/notar/getFlowApplyDetail
        //String uri = request.getRequestURI();
        // method 格式：POST GET
        String method = request.getMethod();
        // ip
        String remoteAddr = request.getRemoteAddr();
        // 客户端主机名
        String remoteHost = request.getRemoteHost();
        // 客户端请求端口
        int remotePort = request.getRemotePort();

        // 参数
        Object[] objects = joinPoint.getArgs();
        log.info("### userIdClassMethod:[{}] method:{} remoteAddr:{} remotePort:{} remoteHost:{}",
            userIdClassMethod, method, remoteAddr, remotePort, remoteHost);
        log.debug("### userIdClassMethod:[{}] args:{}", userIdClassMethod, objects);
    }

    @After("log()")
    public void doAfter(){

        log.info("### userIdClassMethod:[{}], aop After, request end...", this.getUserIdClassMehtod());
    }

    /**
     * 获取请求响应, 无异常会执行
     * @param object
     */
    @AfterReturning(returning = "object",pointcut = "log()")
    public void  doAfterReturning(JoinPoint joinPoint, Object object){
        log.info("### userIdClassMethod:[{}], success response:{}", this.getUserIdClassMehtod(),
            JsonUtils.objToJson(object));
    }

    /**
     * 抛出异常会执行，执行后，再抛出异常，通过统一异常处理
     * @param e
     * @throws Throwable
     */
    @AfterThrowing(throwing = "e", pointcut = "log()")
    public void catchException(Throwable e) throws Throwable {
        log.info("### userIdClassMethod:[{}], exception response:{}", this.getUserIdClassMehtod(), e.getMessage());
        throw e;
    }
}
