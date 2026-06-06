package com.github.zhygtx.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.zhygtx.annotation.Method;
import com.github.zhygtx.annotation.MethodClass;
import com.github.zhygtx.annotation.Param;
import com.github.zhygtx.pojo.PluginData;

import java.util.List;
import java.util.Map;

@MethodClass(description = "提供插件数据的增删改查功能")
public interface SQLService {

    @Method(description = "插入单条数据", returnDescription = "插入时影响的数据量")
    int insert(@Param(description = "JSON格式的数据") String data);

    @Method(description = "插入单条数据到指定索引", returnDescription = "插入时影响的数据量")
    int insert(@Param(description = "数据索引") String index, 
               @Param(description = "JSON格式的数据") String data);

    @Method(description = "批量插入数据", returnDescription = "插入时影响数据量")
    int insert(@Param(description = "JSON数据列表") List<String> data);

    @Method(description = "批量插入数据到指定索引", returnDescription = "插入时影响数据量")
    int insert(@Param(description = "数据索引") String index, 
               @Param(description = "JSON数据列表") List<String> data);

    @Method(description = "按索引映射插入数据", returnDescription = "插入时影响数据量")
    int insert(@Param(description = "索引与数据的映射") Map<String, String> data);

    @Method(description = "按索引批量插入数据", returnDescription = "插入时影响数据量")
    int insertByIndexMap(@Param(description = "索引与数据列表的映射") Map<String, List<String>> data);

    @Method(description = "删除所有数据", returnDescription = "删除时影响数据量")
    int delete();

    @Method(description = "按ID删除数据", returnDescription = "删除时影响数据量")
    int delete(@Param(description = "数据ID") Integer id);

    @Method(description = "按索引删除数据", returnDescription = "删除时影响数据量")
    int delete(@Param(description = "数据索引") String index);

    @Method(description = "按索引列表批量删除", returnDescription = "删除时影响数据量")
    int delete(@Param(description = "索引列表") List<String> index);

    @Method(description = "按ID列表批量删除", returnDescription = "删除时影响数据量")
    int deleteByIds(@Param(description = "数据ID列表") List<Integer> ids);

    @Method(description = "按ID更新数据", returnDescription = "更新时影响数据量")
    int update(@Param(description = "数据ID") Integer id, 
               @Param(description = "新的JSON数据") String data);

    @Method(description = "按索引更新数据", returnDescription = "更新时影响数据量")
    int update(@Param(description = "数据索引") String index, 
               @Param(description = "新的JSON数据") String data);

    @Method(description = "查询所有数据", returnDescription = "查询到的数据列表")
    List<PluginData> select();

    @Method(description = "按ID查询数据", returnDescription = "查询到的数据")
    PluginData selectById(@Param(description = "数据ID") Integer id);

    @Method(description = "按索引查询数据", returnDescription = "查询到的数据")
    PluginData selectByIndex(@Param(description = "数据索引") String index);

    @Method(description = "按索引查询数据", returnDescription = "查询到的数据列表")
    List<PluginData> select(@Param(description = "数据索引") String index);

    @Method(description = "使用Wrapper条件查询", returnDescription = "查询到的数据列表")
    List<PluginData> selectList(@Param(description = "MyBatis-Plus查询条件") Wrapper<PluginData> wrapper);

    @Method(description = "使用Wrapper条件查询单条数据", returnDescription = "查询到的单条数据")
    PluginData selectOne(@Param(description = "MyBatis-Plus查询条件") Wrapper<PluginData> wrapper);

    @Method(description = "使用Wrapper条件查询数量", returnDescription = "查询到的数量")
    int selectCount(@Param(description = "MyBatis-Plus查询条件") Wrapper<PluginData> wrapper);

    @Method(description = "分页查询", returnDescription = "分页查询结果")
    IPage<PluginData> selectPage(@Param(description = "分页参数") Page<PluginData> page, 
                                 @Param(description = "MyBatis-Plus查询条件") Wrapper<PluginData> wrapper);

    @Method(description = "使用Wrapper条件更新", returnDescription = "更新时影响的数据量")
    int update(@Param(description = "更新条件") Wrapper<PluginData> wrapper, 
               @Param(description = "更新内容") PluginData entity);

    @Method(description = "使用Wrapper条件删除", returnDescription = "删除时影响的数据量")
    int delete(@Param(description = "删除条件") Wrapper<PluginData> wrapper);
}
