package com.github.zhygtx.service;

import com.github.zhygtx.pojo.PluginData;
import com.github.zhygtx.pojo.SQL;

import java.util.List;
import java.util.Map;

public interface SQLService {

    int insert(String data);

    int insert(String index,String data);

    int insert(List<String> data);

    int insert(Map<String, String> data);

    int delete();

    int delete(Integer id);

    int delete(SQL sql);

    int delete(String index);

    int delete(List<String> index);

    int deleteByIds(List<Integer> ids);

    int update(Integer id, String data);

    int update(String index, String data);

    List<PluginData> select();

    PluginData select(Integer id);

    List<PluginData> select(String index);

    List<PluginData> select(SQL sql);
}
