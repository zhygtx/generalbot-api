# GeneralBot API

GeneralBot 插件开发 API，供插件反向调用主项目功能、声明方法元数据，并提供基于 SQLite 的本地数据持久化能力。

> 本项目是主项目 [bot](https://github.com/zhygtx/bot) 的辅助 SDK。

## 特性

- **方法元数据声明** —— 通过注解为插件的方法、参数、实体类声明描述信息，主项目可基于这些元数据反向发现和调用插件功能
- **参数可空标注** —— 通过 `@Param(nullable = true)` 标记参数是否允许为 null
- **数据持久化** —— 插件继承 `PluginServiceBase` 即可获得 数据持久化 能力，内置 SQLite 便于本地调试

## 环境要求

- Java 21+

## 快速开始

### 添加 JitPack 仓库

本 SDK 通过 [JitPack](https://jitpack.io) 发布，需先在 `pom.xml` 中添加仓库：

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

### Maven 依赖

```xml
<dependency>
    <groupId>com.github.zhygtx</groupId>
    <artifactId>generalbot-api</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 注解体系

本 SDK 提供了一套运行时注解，用于声明式地描述插件的实体、方法和参数。主项目可以通过反射读取这些注解，获取插件的完整元数据。

### `@Entity` — 实体类

标记插件中的实体类，可填写类描述。

```java
@Entity(description = "用户实体")
public class User {
    // ...
}
```

### `@Attribute` — 属性/字段

标记实体类的字段，可填写字段描述。

```java
@Entity(description = "用户实体")
public class User {

    @Attribute(description = "用户唯一标识")
    private Long id;

    @Attribute(description = "用户昵称")
    private String name;
}
```

### `@MethodClass` — 方法类

标记包含可调用方法的服务类，可填写类的描述。

```java
@MethodClass(description = "用户管理服务")
public class UserService {
    // ...
}
```

### `@Method` — 方法

标记类中的方法，可填写方法描述和返回值描述。

```java
@MethodClass(description = "用户管理服务")
public class UserService {

    @Method(description = "根据ID查找用户", returnDescription = "查找到的用户对象，未找到返回 null")
    public User findById(Long id) {
        // ...
    }
}
```

### `@Param` — 参数

标记方法的参数，可填写参数描述以及是否可空。

```java
@MethodClass(description = "用户管理服务")
public class UserService {

    @Method(description = "根据昵称搜索用户", returnDescription = "匹配的用户列表")
    public List<User> search(
        @Param(description = "搜索关键词") String keyword,
        @Param(description = "分页大小，传 null 使用默认值", nullable = true) Integer pageSize) {
        // ...
    }
}
```

注解一览：

| 注解 | 作用目标 | 用途 |
|------|---------|------|
| `@Entity` | 类 (TYPE) | 标记实体类，声明类描述 |
| `@Attribute` | 字段 (FIELD) | 标记字段，声明字段描述 |
| `@MethodClass` | 类 (TYPE) | 标记方法类，声明类描述 |
| `@Method` | 方法 (METHOD) | 标记方法，声明方法与返回值描述 |
| `@Param` | 参数 (PARAMETER) | 标记参数，声明参数描述与可空性 |

所有注解均为 `@Retention(RUNTIME)`，主项目可在运行时通过反射读取。

## 数据持久化

### 概述

SDK 内置了基于 SQLite 的轻量级数据存储方案，主要用于方便开发者在本地调试插件。插件无需关心数据库连接、建表等细节，继承 `PluginServiceBase` 即可直接使用。

### 数据库表结构

数据存储在 `plugin_data` 表中（自动创建于工作目录下的 `plugin_data.db`）：

| 字段 | 类型 | 说明 |
|------|------|------|
| `id` | INTEGER | 自增主键 |
| `user_id` | VARCHAR | 关联的用户ID |
| `plugin_id` | VARCHAR | 插件唯一标识 |
| `data_index` | VARCHAR | 数据索引（唯一约束） |
| `data` | TEXT | JSON 格式数据内容 |
| `create_time` | TIMESTAMP | 创建时间 |
| `update_time` | TIMESTAMP | 更新时间 |

### 使用方式

让你的 Service 类继承 `PluginServiceBase`，即可通过 `sqlService` 字段使用完整的 CRUD 能力。

```java
import com.github.zhygtx.service.PluginServiceBase;

@MethodClass(description = "我的插件服务")
public class MyPluginService extends PluginServiceBase {

    @Method(description = "保存配置", returnDescription = "影响的记录数")
    public int saveConfig(
        @Param(description = "配置键") String key,
        @Param(description = "JSON 配置内容") String jsonConfig) {

        return sqlService.insert(key, jsonConfig);
    }

    @Method(description = "读取配置", returnDescription = "JSON 配置内容")
    public String getConfig(
        @Param(description = "配置键") String key) {

        PluginData data = sqlService.selectByIndex(key);
        return data != null ? data.getData() : null;
    }
}
```

### SQLService 接口方法

#### 新增

| 方法 | 说明 |
|------|------|
| `int insert(String data)` | 插入单条数据，自动生成索引 |
| `int insert(String index, String data)` | 按指定索引插入单条数据，索引已存在时自动覆盖 |
| `int insert(List<String> data)` | 批量插入 |
| `int insert(String index, List<String> data)` | 按索引批量插入 |
| `int insert(Map<String, String> data)` | 按 Map（key=索引, value=数据）批量插入 |
| `int insertByIndexMap(Map<String, List<String>> data)` | 按 Map（key=索引, value=数据列表）批量插入 |

#### 删除

| 方法 | 说明 |
|------|------|
| `int delete()` | 删除所有数据（按当前 userId+pluginId） |
| `int delete(Integer id)` | 按主键 ID 删除 |
| `int delete(String index)` | 按索引删除 |
| `int delete(List<String> index)` | 按索引列表批量删除 |
| `int deleteByIds(List<Integer> ids)` | 按 ID 列表批量删除 |
| `int delete(Wrapper<PluginData> wrapper)` | 使用 MyBatis-Plus 条件删除 |

#### 修改

| 方法 | 说明 |
|------|------|
| `int update(Integer id, String data)` | 按 ID 更新数据 |
| `int update(String index, String data)` | 按索引更新数据 |
| `int update(List<PluginData> pluginDataList)` | 批量更新 |
| `int update(Wrapper<PluginData> wrapper, PluginData entity)` | 使用 MyBatis-Plus 条件更新 |

#### 查询

| 方法 | 说明 |
|------|------|
| `List<PluginData> select()` | 查询所有数据 |
| `PluginData selectById(Integer id)` | 按 ID 查询 |
| `PluginData selectByIndex(String index)` | 按索引查询单条 |
| `List<PluginData> select(String index)` | 按索引查询列表 |
| `List<PluginData> selectList(Wrapper<PluginData> wrapper)` | 条件查询列表 |
| `PluginData selectOne(Wrapper<PluginData> wrapper)` | 条件查询单条 |
| `int selectCount(Wrapper<PluginData> wrapper)` | 条件查询数量 |
| `IPage<PluginData> selectPage(Page<PluginData> page, Wrapper<PluginData> wrapper)` | 分页查询 |

### PluginData 实体

```java
public class PluginData {
    private Integer id;          // 自增主键
    private String userId;       // 关联用户ID
    private String pluginId;     // 插件ID
    private String dataIndex;    // 数据索引（唯一）
    private String data;         // JSON 数据内容
    private LocalDateTime createTime;  // 创建时间
    private LocalDateTime updateTime;  // 更新时间
}
```

## 完整开发示例

以下示例展示如何使用所有注解，并继承 `PluginServiceBase` 实现一个完整的功能服务。

```java
package com.example.plugin;

import com.github.zhygtx.annotation.*;
import com.github.zhygtx.pojo.PluginData;
import com.github.zhygtx.service.PluginServiceBase;
import java.util.List;

/**
 * 用户数据实体
 */
@Entity(description = "插件用户数据实体")
public class UserData {

    @Attribute(description = "用户唯一ID")
    private String userId;

    @Attribute(description = "用户当前积分")
    private Integer points;

    @Attribute(description = "用户等级")
    private Integer level;

    // getter / setter 省略...
}

/**
 * 积分服务 —— 继承 PluginServiceBase 获得数据库操作能力
 */
@MethodClass(description = "积分管理服务，提供积分查询、增加、排名等功能")
public class PointsService extends PluginServiceBase {

    @Method(description = "获取用户积分", returnDescription = "用户积分值，用户不存在返回 -1")
    public int getPoints(
        @Param(description = "用户ID") String userId) {

        PluginData data = sqlService.selectByIndex("points_" + userId);
        if (data == null) return -1;
        return Integer.parseInt(data.getData());
    }

    @Method(description = "增加用户积分", returnDescription = "增加后的积分值")
    public int addPoints(
        @Param(description = "用户ID") String userId,
        @Param(description = "要增加的积分数量") int amount,
        @Param(description = "备注说明，可为空", nullable = true) String remark) {

        int current = getPoints(userId);
        if (current == -1) current = 0;
        int newPoints = current + amount;
        sqlService.insert("points_" + userId, String.valueOf(newPoints));
        return newPoints;
    }

    @Method(description = "获取积分排行榜", returnDescription = "积分前N名用户ID列表")
    public List<String> getTopRank(
        @Param(description = "获取前N名，传 null 默认取前10", nullable = true) Integer n) {

        int limit = (n != null) ? n : 10;
        // 查询逻辑...使用 sqlService.select() 获取所有数据后排序
        return List.of();
    }
}
```

## License

MIT
