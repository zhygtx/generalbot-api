package com.github.zhygtx.pojo;
import lombok.Getter;

@Getter
public enum Where {
    // 等于/不等于
    EQUAL("等于"),
    NOT_EQUAL("不等于"),

    // 比较运算符
    GREATER_THAN("大于"),
    LESS_THAN("小于"),
    GREATER_EQUAL("大于等于"),
    LESS_EQUAL("小于等于"),

    // 模糊匹配
    LIKE("模糊匹配"),
    NOT_LIKE("不匹配"),
    STARTS_WITH("开头是"),
    ENDS_WITH("结尾是"),
    CONTAINS("包含"),

    // 范围查询
    IN("在...范围内"),
    NOT_IN("不在...范围内"),
    BETWEEN("在...之间"),
    NOT_BETWEEN("不在...之间"),

    // 空值判断
    IS_NULL("为空"),
    IS_NOT_NULL("不为空"),

    // 存在性检查
    EXISTS("存在"),
    NOT_EXISTS("不存在"),

    // 正则表达式
    REGEXP("正则匹配"),
    NOT_REGEXP("正则不匹配"),

    // 布尔值
    IS_TRUE("为真"),
    IS_FALSE("为假");

    Where(String string) {}
}