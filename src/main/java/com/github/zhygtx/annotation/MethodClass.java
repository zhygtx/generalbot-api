package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 方法类注解
 * 用于标记插件中的方法类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodClass {
    
    /**
     * 方法类描述
     */
    String description() default "";
}
