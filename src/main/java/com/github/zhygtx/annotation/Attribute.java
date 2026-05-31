package com.github.zhygtx.annotation;

import java.lang.annotation.*;

/**
 * 属性注解
 * 用于标记插件实体类中的属性
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Attribute {
    
    /**
     * 属性描述
     */
    String description() default "";
}
