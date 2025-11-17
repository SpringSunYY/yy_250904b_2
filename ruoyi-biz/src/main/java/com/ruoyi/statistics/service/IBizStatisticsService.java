package com.ruoyi.statistics.service;

import com.ruoyi.statistics.domain.dto.BizStatisticsDto;
import com.ruoyi.statistics.domain.vo.BizPieStatisticsVo;

import java.util.List;

public interface IBizStatisticsService {
    /**
     * 承包商人员入厂培训合格率
     *
     * @param bizStatisticsDto
     * @return List<PieStatisticsVo < Long>>
     * @author: YY
     * @method: trainingRateStatistics
     * @date: 2025/11/14 15:32
     **/
    List<BizPieStatisticsVo<Long>> trainingRateStatistics(BizStatisticsDto bizStatisticsDto);

    /**
     * 承包商工机具检查合格率
     *
     * @param bizStatisticsDto
     * @return List<PieStatisticsVo < Long>>
     * @author: YY
     * @method: toolInventoryRateStatistics
     * @date: 2025/11/14 18:18
     **/
    List<BizPieStatisticsVo<Long>> toolInventoryRateStatistics(BizStatisticsDto bizStatisticsDto);

    /**
     * 承包商安全协议签订完成率
     *
     * @param bizStatisticsDto
     * @return List<PieStatisticsVo < Long>>
     * @author: YY
     * @method: securityAgreementRateStatistics
     * @date: 2025/11/14 20:09
     **/
    List<BizPieStatisticsVo<Long>> securityAgreementRateStatistics(BizStatisticsDto bizStatisticsDto);

    /**
     * 承包商资质审查完成率
     *
     * @param bizStatisticsDto
     * @return List<PieStatisticsVo < Long>>
     * @author: YY
     * @method: qualificationRateStatistics
     * @date: 2025/11/15 16:03
     **/
    List<BizPieStatisticsVo<Long>> qualificationRateStatistics(BizStatisticsDto bizStatisticsDto);
}
