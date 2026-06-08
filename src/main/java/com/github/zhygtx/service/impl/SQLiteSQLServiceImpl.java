package com.github.zhygtx.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.zhygtx.mapper.PluginDataMapper;
import com.github.zhygtx.pojo.PluginData;
import com.github.zhygtx.service.SQLService;
import com.github.zhygtx.util.SQLiteDatabaseUtil;
import org.apache.ibatis.session.SqlSession;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class SQLiteSQLServiceImpl implements SQLService {

    private static final String DEFAULT_USER_ID = "local_test_user";
    private static final String DEFAULT_PLUGIN_ID = "local_test_plugin";

    private SqlSession getSession() {
        return SQLiteDatabaseUtil.openSession();
    }

    @Override
    public int insert(String data) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            PluginData pluginData = new PluginData();
            pluginData.setUserId(DEFAULT_USER_ID);
            pluginData.setPluginId(DEFAULT_PLUGIN_ID);
            pluginData.setDataIndex("auto_" + System.currentTimeMillis());
            pluginData.setData(data);
            pluginData.setCreateTime(LocalDateTime.now());
            pluginData.setUpdateTime(LocalDateTime.now());
            return mapper.insert(pluginData);
        }
    }

    @Override
    public int insert(String index, String data) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            PluginData existing = mapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginData>()
                    .eq(PluginData::getDataIndex, index));
            
            if (existing != null) {
                existing.setData(data);
                existing.setUpdateTime(LocalDateTime.now());
                return mapper.updateById(existing);
            }
            
            PluginData pluginData = new PluginData();
            pluginData.setUserId(DEFAULT_USER_ID);
            pluginData.setPluginId(DEFAULT_PLUGIN_ID);
            pluginData.setDataIndex(index);
            pluginData.setData(data);
            pluginData.setCreateTime(LocalDateTime.now());
            pluginData.setUpdateTime(LocalDateTime.now());
            return mapper.insert(pluginData);
        }
    }

    @Override
    public int insert(List<String> data) {
        int count = 0;
        for (String item : data) {
            count += insert(item);
        }
        return count;
    }

    @Override
    public int insert(String index, List<String> data) {
        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            count += insert(index + "_" + i, data.get(i));
        }
        return count;
    }

    @Override
    public int insert(Map<String, String> data) {
        int count = 0;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            count += insert(entry.getKey(), entry.getValue());
        }
        return count;
    }

    @Override
    public int insertByIndexMap(Map<String, List<String>> data) {
        int count = 0;
        for (Map.Entry<String, List<String>> entry : data.entrySet()) {
            count += insert(entry.getKey(), entry.getValue());
        }
        return count;
    }

    @Override
    public int delete() {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginData>()
                    .eq(PluginData::getUserId, DEFAULT_USER_ID)
                    .eq(PluginData::getPluginId, DEFAULT_PLUGIN_ID));
        }
    }

    @Override
    public int delete(Integer id) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.deleteById(id);
        }
    }

    @Override
    public int delete(String index) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.delete(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginData>()
                    .eq(PluginData::getDataIndex, index));
        }
    }

    @Override
    public int delete(List<String> index) {
        int count = 0;
        for (String idx : index) {
            count += delete(idx);
        }
        return count;
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.deleteByIds(ids);
        }
    }

    @Override
    public int update(Integer id, String data) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            PluginData pluginData = mapper.selectById(id);
            if (pluginData != null) {
                pluginData.setData(data);
                pluginData.setUpdateTime(LocalDateTime.now());
                return mapper.updateById(pluginData);
            }
            return 0;
        }
    }

    @Override
    public int update(String index, String data) {
        return insert(index, data);
    }

    @Override
    public int update(List<PluginData> pluginDataList) {
        if (pluginDataList == null || pluginDataList.isEmpty()) {
            return 0;
        }
        int count = 0;
        for (PluginData pluginData : pluginDataList) {
            pluginData.setUpdateTime(LocalDateTime.now());
            try (SqlSession session = getSession()) {
                PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
                count += mapper.updateById(pluginData);
            }
        }
        return count;
    }

    @Override
    public List<PluginData> select() {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.selectList(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginData>()
                    .eq(PluginData::getUserId, DEFAULT_USER_ID)
                    .eq(PluginData::getPluginId, DEFAULT_PLUGIN_ID));
        }
    }

    @Override
    public PluginData selectById(Integer id) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.selectById(id);
        }
    }

    @Override
    public PluginData selectByIndex(String index) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.selectOne(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginData>()
                    .eq(PluginData::getDataIndex, index));
        }
    }

    @Override
    public List<PluginData> select(String index) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.selectList(new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<PluginData>()
                    .eq(PluginData::getDataIndex, index));
        }
    }

    @Override
    public List<PluginData> selectList(Wrapper<PluginData> wrapper) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.selectList(wrapper);
        }
    }

    @Override
    public PluginData selectOne(Wrapper<PluginData> wrapper) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.selectOne(wrapper);
        }
    }

    @Override
    public int selectCount(Wrapper<PluginData> wrapper) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return Math.toIntExact(mapper.selectCount(wrapper));
        }
    }

    @Override
    public IPage<PluginData> selectPage(Page<PluginData> page, Wrapper<PluginData> wrapper) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.selectPage(page, wrapper);
        }
    }

    @Override
    public int update(Wrapper<PluginData> wrapper, PluginData entity) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.update(entity, wrapper);
        }
    }

    @Override
    public int delete(Wrapper<PluginData> wrapper) {
        try (SqlSession session = getSession()) {
            PluginDataMapper mapper = session.getMapper(PluginDataMapper.class);
            return mapper.delete(wrapper);
        }
    }
}