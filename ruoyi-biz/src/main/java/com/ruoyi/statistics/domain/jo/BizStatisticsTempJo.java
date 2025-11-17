package com.ruoyi.statistics.domain.jo;

import lombok.Data;

import java.util.Date;

/**
 * 用于存储临时数据
 *
 * @Project: RuoYi-biz
 * @Author: YY
 * @CreateTime: 2025-11-14  16:00
 * @Version: 1.0
 */
@Data
public class BizStatisticsTempJo<T> {
    private T name;
    private Long value;
    private Long value1;
    private Long value2;
    private Long total;
    private Date date;
}
