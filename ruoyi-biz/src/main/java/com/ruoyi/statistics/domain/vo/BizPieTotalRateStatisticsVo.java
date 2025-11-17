package com.ruoyi.statistics.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 饼图总数统计图
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-31  22:00
 * @Version: 1.0
 */
@Data
public class BizPieTotalRateStatisticsVo<T> {
    private List<BizPieStatisticsVo<T>> data;
    private T total;
}
