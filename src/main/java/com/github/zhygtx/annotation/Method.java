package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 方法注解
 * 用于标记插件方法类中的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Method {
    
    /**
     * 方法描述
     */
    String description() default "";
}
