package com.github.zhygtx.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PluginData {

    /**
     * 数据ID（UUID）
     */
    private Integer id;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 插件ID
     */
    private String pluginId;

    /**
     * 索引ID
     */
    private String index;

    /**
     * 数据内容(JSON)
     */
    private String data;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
