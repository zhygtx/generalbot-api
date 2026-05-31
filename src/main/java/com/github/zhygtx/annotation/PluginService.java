package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 插件服务类注解
 * 用于标记插件中的服务类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PluginService {
    
    /**
     * 服务类描述
     */
    String description() default "";
}
