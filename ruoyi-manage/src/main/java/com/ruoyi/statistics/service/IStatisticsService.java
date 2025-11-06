package com.ruoyi.statistics.service;

import com.ruoyi.statistics.domain.dto.StatisticsRequest;
import com.ruoyi.statistics.domain.vo.BarRateStatisticsVo;
import com.ruoyi.statistics.domain.vo.BaseStatisticsVo;
import com.ruoyi.statistics.domain.vo.PieStatisticsVo;
import com.ruoyi.statistics.domain.vo.PieTotalRateStatisticsVo;

import java.util.ArrayList;
import java.util.List;

public interface IStatisticsService {
    BaseStatisticsVo<Float> checkRateStatistics(StatisticsRequest request);

    /**
     * 运行时长统计
     *
     * @param request
     * @return
     */
    List<PieStatisticsVo<Long>> runTimeStatistics(StatisticsRequest request);

    /**
     * 设备润滑统计
     *
     * @param request 请求
     * @return List<PieStatisticsVo < Float>>
     * @author: YY
     * @method: lubricateStatistics
     * @date: 2025/10/30 16:01
     **/
    ArrayList<PieStatisticsVo<Long>> lubricateRateStatistics(StatisticsRequest request);

    /**
     * 设备隐患整改率
     *
     * @param request 请求
     * @return BarRateStatisticsVo<Long>
     * @author: YY
     * @method: defectEliminateStatistics
     * @date: 2025/10/31 15:38
     **/
    BarRateStatisticsVo<Long> defectEliminateStatistics(StatisticsRequest request);

    /**
     * 设备漏水率
     *
     * @param request 统计请求
     * @return PieTotalRateStatisticsVo<Long>
     * @author: YY
     * @method: leakageRateStatistics
     * @date: 2025/10/31 22：04
     **/
    PieTotalRateStatisticsVo<Long> leakageRateStatistics(StatisticsRequest request);

    /**
     * 设备隐患整改率
     *
     * @param request 统计请求
     * @return BarRateStatisticsVo<Long>
     * @author: YY
     * @method: defectEliminateRateStatistics
     * @date: 2025/11/11 17：04
     **/
    BarRateStatisticsVo<Long> defectEliminateRateStatistics(StatisticsRequest request);
}
