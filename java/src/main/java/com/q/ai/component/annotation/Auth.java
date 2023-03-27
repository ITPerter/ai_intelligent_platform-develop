/**
 *
 */
package com.q.ai.component.annotation;

import com.q.ai.component.enuz.AUTH_TYPE;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * 如果类和方法上都有@auth注解，则方法上的注解会覆盖掉类上的注解意义
 * <p>
 * <p>
 * <p>
 * 1.拦截类
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 2.拦截方法
 * 作用于controller的method上，要求该method第一个参数必须是 ParamJSON 类型的参数，
 * 根据url中的“accessToken”或者 body中的“token”来鉴权，插入skill对象到ParamJSON参数中。
 */

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Auth {
    @AliasFor("authType")
    AUTH_TYPE value() default AUTH_TYPE.SESSION;

    @AliasFor("value")
    AUTH_TYPE authType() default AUTH_TYPE.SESSION;


}
