package com.github.zhygtx.pojo;

import com.github.zhygtx.annotation.Attribute;
import com.github.zhygtx.annotation.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(description = "存储插件相关的配置数据")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PluginData {

    @Attribute(description = "数据唯一标识符")
    private Integer id;

    @Attribute(description = "关联的用户ID")
    private String userId;

    @Attribute(description = "插件唯一标识")
    private String pluginId;

    @Attribute(description = "数据索引标识")
    private String index;

    @Attribute(description = "JSON格式的数据内容")
    private String data;

    @Attribute(description = "数据创建时间")
    private LocalDateTime createTime;

    @Attribute(description = "数据更新时间")
    private LocalDateTime updateTime;
}
