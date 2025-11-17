package com.ruoyi.statistics.domain.ro;

import lombok.Data;

/**
 * 统计返回RO
 */
@Data
public class BizStatisticsRo<T> {
    private T name;
    private Long value;
}
