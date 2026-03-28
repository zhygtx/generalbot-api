package com.github.zhygtx.pojo;

import lombok.Getter;

@Getter
public enum OrderBy {
    ASC("升序"),
    DESC("降序");

    OrderBy(String string) {}
}
