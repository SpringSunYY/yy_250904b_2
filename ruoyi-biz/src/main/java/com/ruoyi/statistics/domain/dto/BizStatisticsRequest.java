package com.ruoyi.statistics.domain.dto;

import lombok.Data;

/**
 * 统计请求
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-28  17:53
 * @Version: 1.0
 */
@Data
public class BizStatisticsRequest {
    /**
     * 开始时间
     */
    private String startDate;
    /**
     * 结束时间
     */
    private String endDate;
    /**
     * 时间类型 日、月、季度
     */
    private String dateType;

    private Long choiceId;
}
