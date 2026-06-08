package com.github.zhygtx.service;

import com.github.zhygtx.service.impl.SQLiteSQLServiceImpl;

public class PluginServiceBase {

    protected SQLService sqlService;

    public PluginServiceBase() {
        try {
            Class.forName("org.springframework.context.ApplicationContext");
            this.sqlService = null;
        } catch (ClassNotFoundException e) {
            this.sqlService = new SQLiteSQLServiceImpl();
        }
    }
}
