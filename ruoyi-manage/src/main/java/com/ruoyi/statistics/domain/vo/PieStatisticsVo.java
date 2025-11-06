package com.ruoyi.statistics.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 统计返回vo
 *
 * @Project: Picture
 * @Author: YY
 * @CreateTime: 2025-09-18  16:01
 * @Version: 1.0
 */
@Data
public class PieStatisticsVo<T> implements Serializable {
    private String name;
    private T value;
}
