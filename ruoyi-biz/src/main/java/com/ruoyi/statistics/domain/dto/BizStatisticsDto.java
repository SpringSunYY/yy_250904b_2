package com.ruoyi.statistics.domain.dto;

import lombok.Data;

/**
 * 查询请求
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-28  18:18
 * @Version: 1.0
 */
@Data
public class BizStatisticsDto {
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
