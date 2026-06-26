package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 参数注解
 * 用于标记插件方法类方法中的参数
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Param {
    
    /**
     * 参数描述
     */
    String description() default "";
    
    /**
     * 是否可空
     */
    boolean nullable() default false;
}
