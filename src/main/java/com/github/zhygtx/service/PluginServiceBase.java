package com.github.zhygtx.service;

import com.github.zhygtx.service.impl.SQLiteSQLServiceImpl;

public class PluginServiceBase {

    protected SQLService sqlService;

    public PluginServiceBase() {
        this.sqlService = new SQLiteSQLServiceImpl();
    }
}
