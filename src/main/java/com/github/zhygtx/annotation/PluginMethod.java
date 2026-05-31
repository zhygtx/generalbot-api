package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 插件方法注解
 * 用于标记插件服务类中的方法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginMethod {
    
    /**
     * 方法描述
     */
    String description() default "";
}
