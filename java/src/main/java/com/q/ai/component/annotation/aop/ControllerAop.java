package com.q.ai.component.annotation.aop;

import com.q.ai.component.session.RequestContext;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@Aspect
@Configuration
public class ControllerAop {


    @Autowired
    RequestContext requestContext;

    //2.定义一个切入点
    @Pointcut("execution(public * com.q.ai.mvc.controller.*.*(..))")
    public void controller(){}

    //3.在切入点开始处切入内容
    @Before(value = "controller()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request;

        //获取切面执行的类名
        Class clazz = joinPoint.getTarget().getClass();
        //获取对应类的logger
        Logger clazzLogger = LoggerFactory.getLogger(clazz);
        if (attributes != null) {
            request = attributes.getRequest();
            clazzLogger.info("请求入参:{} {} {}", request.getMethod(),request.getRequestURI(),joinPoint.getArgs());

        }else{
            clazzLogger.info("严重系统错误");
        }

    }

    @Around("controller()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        //获取切面执行的类名
        Class clazz = pjp.getTarget().getClass();
        //获取对应类的logger
        Logger clazzLogger = LoggerFactory.getLogger(clazz);
        //     clazzLogger.info("切面Aspect:方法前执行");
        Object result = pjp.proceed();
        //     clazzLogger.info("切面Aspect:方法后执行");
        return result;
    }



    @AfterReturning(returning = "result",pointcut = "controller()")
    public void doAfterReturning(JoinPoint joinPoint, Object result) {
        //获取切面执行的类名
        Class clazz = joinPoint.getTarget().getClass();
        //获取对应类的logger
        Long time = System.currentTimeMillis() - requestContext.getStartTime();
        Logger clazzLogger = LoggerFactory.getLogger(clazz);
        clazzLogger.info("请求返回({} ms):{}", time, JSON.toJSON(result));
        destroy();

    }


    @AfterThrowing(pointcut = "controller()",throwing = "e")
    public void doThrowing(JoinPoint joinPoint, Exception e){
        Class clazz = joinPoint.getTarget().getClass();
        //获取对应类的logger
        Long time = System.currentTimeMillis() - requestContext.getStartTime();
        Logger clazzLogger = LoggerFactory.getLogger(clazz);

        if (e instanceof RsException) {
            RsException ex = (RsException) e;
            clazzLogger.info("请求返回({} ms): {}", time, Rs.buildErr(ex.getMessage(), ex.getCode()));
        } else {
            clazzLogger.error("异常", e);
            clazzLogger.info("请求({} ms) 耗时:异常{}。", time, e);
        }

        destroy();
    }

    private void destroy() {
        MDC.clear();
        requestContext.destroy();
    }


}
