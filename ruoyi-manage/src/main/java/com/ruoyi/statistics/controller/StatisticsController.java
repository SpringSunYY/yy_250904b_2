package com.ruoyi.statistics.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.statistics.domain.dto.StatisticsRequest;
import com.ruoyi.statistics.service.IStatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 统计
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-28  17:42
 * @Version: 1.0
 */
@RestController
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {
    @Resource
    private IStatisticsService statisticsService;

    /**
     * 设备点检完成率
     */
    @PreAuthorize("@ss.hasPermi('statistics')")
    @GetMapping("/checkRate")
    public AjaxResult checkRateStatistics(StatisticsRequest request) {
        return success(statisticsService.checkRateStatistics(request));
    }

    /**
     * 设备运行时间统计
     */
    @PreAuthorize("@ss.hasPermi('statistics')")
    @GetMapping("/runTime")
    public AjaxResult runTimeStatistics(StatisticsRequest request) {
        return success(statisticsService.runTimeStatistics(request));
    }

    /**
     * 设备润环率
     */
    @PreAuthorize("@ss.hasPermi('statistics')")
    @GetMapping("/lubricateRate")
    public AjaxResult lubricateStatistics(StatisticsRequest request) {
        return success(statisticsService.lubricateRateStatistics(request));
    }

    /**
     * 设备隐患整改率
     */
    @PreAuthorize("@ss.hasPermi('statistics')")
    @GetMapping("/defectEliminate")
    public AjaxResult defectEliminateStatistics(StatisticsRequest request) {
        return success(statisticsService.defectEliminateStatistics(request));
    }

    /**
     * 设备泄露率
     */
    @PreAuthorize("@ss.hasPermi('statistics')")
    @GetMapping("/leakageRate")
    public AjaxResult leakageRateStatistics(StatisticsRequest request) {
        return success(statisticsService.leakageRateStatistics(request));
    }

    /**
     * 设备缺陷消除率
     */
    @PreAuthorize("@ss.hasPermi('statistics')")
    @GetMapping("/defectEliminateRate")
    public AjaxResult defectEliminateRateStatistics(StatisticsRequest request) {
        return success(statisticsService.defectEliminateRateStatistics(request));
    }
}
