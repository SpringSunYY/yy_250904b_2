package com.ruoyi.statistics.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.statistics.domain.dto.BizStatisticsDto;
import com.ruoyi.statistics.service.IBizStatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 统计
 *
 * @Project: RuoYi-biz
 * @Author: YY
 * @CreateTime: 2025-11-13  22:31
 * @Version: 1.0
 */
@RestController
@RequestMapping("/statistics")
public class BizStatisticsController extends BaseController {
    @Resource
    private IBizStatisticsService statisticsService;

    /**
     * 承包商人员入厂培训合格率
     *
     * @param bizStatisticsDto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('contractor:statistics')")
    @GetMapping("/trainingRate")
    public AjaxResult trainingRate(BizStatisticsDto bizStatisticsDto) {
        return success(statisticsService.trainingRateStatistics(bizStatisticsDto));
    }

    /**
     * 承包商工机具检查合格率
     *
     * @param bizStatisticsDto
     * @return
     */
    @PreAuthorize("@ss.hasPermi('contractor:statistics')")
    @GetMapping("/toolInventoryRate")
    public AjaxResult toolInventoryRate(BizStatisticsDto bizStatisticsDto) {
        return success(statisticsService.toolInventoryRateStatistics(bizStatisticsDto));
    }

    /**
     * 承包商安全协议签订完成率
     */
    @PreAuthorize("@ss.hasPermi('contractor:statistics')")
    @GetMapping("/securityAgreementRate")
    public AjaxResult securityAgreementRate(BizStatisticsDto bizStatisticsDto) {
        return success(statisticsService.securityAgreementRateStatistics(bizStatisticsDto));
    }

    /**
     * 承包商资质审查完成率
     */
    @PreAuthorize("@ss.hasPermi('contractor:statistics')")
    @GetMapping("/qualificationRate")
    public AjaxResult qualificationRate(BizStatisticsDto bizStatisticsDto) {
        return success(statisticsService.qualificationRateStatistics(bizStatisticsDto));
    }
}
