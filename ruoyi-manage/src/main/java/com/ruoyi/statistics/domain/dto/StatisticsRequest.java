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
public class StatisticsRequest {
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
    /**
     * 设备名称
     */
    private String equName;
    /**
     * 设备编号
     */
    private String equCode;
    /**
     * 设备类型
     */
    private String type;
    /**
     * 设备等级
     */
    private String equipLevels;
    /**
     * 设备安装位置
     */
    private String installLocation;
    /**
     * 设备状态
     */
    private String status;
}
