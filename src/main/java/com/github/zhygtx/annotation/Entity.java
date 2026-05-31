package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 实体类注解
 * 用于标记插件中的实体类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Entity {
    
    /**
     * 实体类描述
     */
    String description() default "";
}
