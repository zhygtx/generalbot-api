package com.github.zhygtx.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SQL {

    private Map<Where,String> indexWhere;

    private Map<Where, LocalDateTime> createTimeWhere;

    private Map<Where, LocalDateTime> updateTimeWhere;

    private List<OrderClause> orderByClauses;


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderClause {
        private OrderBy orderBy;
        private FieldName fieldName;
    }

    private enum FieldName {
        index,
        createTime,
        updateTime
    }
}
