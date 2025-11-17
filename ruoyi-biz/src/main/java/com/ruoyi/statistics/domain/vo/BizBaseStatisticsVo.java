package com.ruoyi.statistics.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
* 主线图返回VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BizBaseStatisticsVo<T> {
   private List<String> names;

    private List<T> values;
}
