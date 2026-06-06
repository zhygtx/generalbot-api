package com.github.zhygtx.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.github.zhygtx.annotation.Attribute;
import com.github.zhygtx.annotation.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(description = "存储插件相关的配置数据")
@TableName("plugin_data")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PluginData {

    @TableId(type = IdType.AUTO)
    @Attribute(description = "数据唯一标识符")
    private Integer id;

    @TableField("user_id")
    @Attribute(description = "关联的用户ID")
    private String userId;

    @TableField("plugin_id")
    @Attribute(description = "插件唯一标识")
    private String pluginId;

    @TableField("data_index")
    @Attribute(description = "数据索引标识")
    private String index;

    @TableField("data")
    @Attribute(description = "JSON格式的数据内容")
    private String data;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @Attribute(description = "数据创建时间")
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @Attribute(description = "数据更新时间")
    private LocalDateTime updateTime;
}
