package com.ruoyi.statistics.domain.vo;

import lombok.Data;

import java.util.List;

/**
 * 柱形比例图统计返回vo
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-31  15:37
 * @Version: 1.0
 */
@Data
public class BarRateStatisticsVo<T> {
    private List<String> names;
    private List<T> innerValues;
    private List<T> outerValues;
}
