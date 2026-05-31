package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 插件参数注解
 * 用于标记插件服务类方法中的参数
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginParam {
    
    /**
     * 参数描述
     */
    String description() default "";
}
