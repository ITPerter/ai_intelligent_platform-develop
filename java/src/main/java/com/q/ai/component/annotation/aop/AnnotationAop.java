package com.q.ai.component.annotation.aop;

import com.mysql.cj.util.StringUtils;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.annotation.LogMethod;
import com.q.ai.component.enuz.APP;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.component.session.RequestContext;
import com.q.ai.biz.entity.Session;
import com.q.ai.component.io.RsException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 说明：@annotation 表示拦截方法  @within 表示拦截类
 */

@Configuration
@Aspect
public class AnnotationAop {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RequestContext requestContext;


    @Around("@within(com.q.ai.component.annotation.Auth)")
    private Object authClass(ProceedingJoinPoint point) throws Throwable {
        return auth(point, true);
    }

    /**
     * 说明：@annotation 表示拦截方法  @within 表示拦截类
     */
    @Around("@annotation(com.q.ai.component.annotation.Auth)")
    private Object authMethod(ProceedingJoinPoint point) throws Throwable {
        return auth(point, false);
    }


    @Around("@annotation(com.q.ai.component.annotation.LogMethod)")
    private Object logMethod(ProceedingJoinPoint point) throws Throwable {
        return logAfter(point);

    }

    private Object logAfter(ProceedingJoinPoint point) throws Throwable {
        Class clazz = point.getTarget().getClass();
        Logger clazzLogger = LoggerFactory.getLogger(clazz);

        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        //method.getAnnotation(LogMethod.class);  这个方法获取的注解里的AliasFor注解还未生效，使用如下方法
        LogMethod logMethod = AnnotationUtils.getAnnotation(method, LogMethod.class);
        Object result;
        assert logMethod != null;
        if (logMethod.logTime()) {
            long start = System.currentTimeMillis();
            result = point.proceed();//该方法将控制权交给被通知的方法
            clazzLogger.info("方法{},入参：{},返回:{}.耗时：{}.", method.getName(), point.getArgs(), result, System.currentTimeMillis() - start);
            return result;
        } else {
            result = point.proceed();//该方法将控制权交给被通知的方法
            clazzLogger.info("方法{},入参：{},返回:{}.", method.toString(), point.getArgs(), result);

        }
        return result;
    }

    private Object auth(ProceedingJoinPoint point, boolean isClass) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        boolean pass = false;
        //method.getAnnotation(AccessTokenAuth.class); 此方法获取的注解中字段的aliasFor不生效
        Method method = signature.getMethod();
        Auth auth = AnnotationUtils.getAnnotation(method, Auth.class);
        //如果类和方法上都有@auth注解，则方法上的注解会覆盖掉类上的注解意义
        if (isClass) {
            if (null != auth) {//方法上有注解，直接忽略类的注解
                return point.proceed();
            }
            Class<?> clazz = point.getTarget().getClass();
            auth = AnnotationUtils.getAnnotation(clazz, Auth.class);
        }
        AUTH_TYPE authType = auth.authType();
        switch (authType) {
            case SESSION:
                pass = sessionAuth();
                break;
            case APP_TOKEN:
                pass = tokenAuth();
                break;
            case USER_TOKEN:
                pass = tokenAuth();
                break;
            case OFF:
            default:
                pass = true;
        }

        if (pass) {
            return point.proceed();
        }

        throw new RsException("权限校验失败，需要登录", -300);
    }


    private boolean sessionAuth() {
        int userId = requestContext.getSession().getUserId();
        return userId != 0;
    }

    private boolean tokenAuth() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getParameter("token");
        if (StringUtils.isNullOrEmpty(token)) {
            throw new RsException("该接口要求提供token作为认证");
        }
        Session session = requestContext.getSession();
        //用户token+应用token
        if (session.getUserId() == 0 && session.getApp()==null) {
            throw new RsException("token校验失败");
        }
        return true;

    }


}

