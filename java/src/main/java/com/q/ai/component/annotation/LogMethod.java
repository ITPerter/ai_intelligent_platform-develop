package com.q.ai.component.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

//存储级别
@Retention(RetentionPolicy.RUNTIME)
//标注目标
@Target(ElementType.METHOD)
//所标注内容，可以出现在javadoc中。
@Documented
public @interface LogMethod {
    @AliasFor("logTime")
    boolean value() default false;

    @AliasFor("value")
    boolean logTime() default false;
}
