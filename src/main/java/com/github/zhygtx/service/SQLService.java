package com.github.zhygtx.service;

import com.github.zhygtx.pojo.PluginData;
import com.github.zhygtx.pojo.SQL;

import java.util.List;
import java.util.Map;

public interface SQLService {

    int insert(String data);

    int insert(List<String> data);

    int insert(Map<String, String> data);

    int delete(SQL sql);

    int update(SQL sql, String data);

    List<PluginData> select(SQL sql);

}
