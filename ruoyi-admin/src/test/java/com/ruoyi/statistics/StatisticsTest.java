package com.ruoyi.statistics;

import com.ruoyi.flowable.service.IFlowTaskService;
import com.ruoyi.statistics.constants.DateTypeConstants;
import com.ruoyi.statistics.domain.dto.StatisticsRequest;
import com.ruoyi.statistics.domain.vo.BarRateStatisticsVo;
import com.ruoyi.statistics.domain.vo.BaseStatisticsVo;
import com.ruoyi.statistics.domain.vo.PieStatisticsVo;
import com.ruoyi.statistics.service.IStatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 统计测试类
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-28  18:56
 * @Version: 1.0
 */
@SpringBootTest()
public class StatisticsTest {
    @Resource
    private IStatisticsService statisticsService;

    @Resource
    private IFlowTaskService flowTaskService;

    @Test
    public void checkRate() {
        StatisticsRequest request = new StatisticsRequest();
        request.setStartDate("2025-9-01");
        request.setEndDate("2025-10-31");
        request.setDateType(DateTypeConstants.DAY);
        BaseStatisticsVo<Float> checkRate = statisticsService.checkRateStatistics(request);
        List<String> names = checkRate.getNames();
        List<Float> values = checkRate.getValues();
        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i) + ":" + values.get(i));
        }
        request.setDateType(DateTypeConstants.MONTH);
        BaseStatisticsVo<Float> checkRateMonth = statisticsService.checkRateStatistics(request);
        List<String> namesMonth = checkRateMonth.getNames();
        List<Float> valuesMonth = checkRateMonth.getValues();
        System.out.println(" 月= ");
        for (int i = 0; i < namesMonth.size(); i++) {
            System.out.println(namesMonth.get(i) + ":" + valuesMonth.get(i));
        }
        request.setDateType(DateTypeConstants.QUARTER);
        BaseStatisticsVo<Float> checkRateQuarter = statisticsService.checkRateStatistics(request);
        List<String> namesQuarter = checkRateQuarter.getNames();
        List<Float> valuesQuarter = checkRateQuarter.getValues();
        System.out.println(" 季度= ");
        for (int i = 0; i < namesQuarter.size(); i++) {
            System.out.println(namesQuarter.get(i) + ":" + valuesQuarter.get(i));
        }
        request.setDateType(DateTypeConstants.YEAR);
        BaseStatisticsVo<Float> checkRateYear = statisticsService.checkRateStatistics(request);
        List<String> namesYear = checkRateYear.getNames();
        List<Float> valuesYear = checkRateYear.getValues();
        System.out.println(" 年= ");
        for (int i = 0; i < namesYear.size(); i++) {
            System.out.println(namesYear.get(i) + ":" + valuesYear.get(i));
        }
    }

    @Test
    public void runTime() {
        StatisticsRequest request = new StatisticsRequest();
        request.setStartDate("2025-9-01");
        request.setEndDate("2025-10-31");
        request.setDateType(DateTypeConstants.DAY);
        List<PieStatisticsVo<Long>> pieStatisticsVos = statisticsService.runTimeStatistics(request);
        System.out.println(" 日= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
        request.setDateType(DateTypeConstants.MONTH);
        pieStatisticsVos = statisticsService.runTimeStatistics(request);
        System.out.println(" 月= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
        request.setDateType(DateTypeConstants.QUARTER);
        pieStatisticsVos = statisticsService.runTimeStatistics(request);
        System.out.println(" 季度= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
        request.setDateType(DateTypeConstants.YEAR);
        pieStatisticsVos = statisticsService.runTimeStatistics(request);
        System.out.println(" 年= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
    }

    @Test
    public void lubricate() {
        StatisticsRequest request = new StatisticsRequest();
        request.setStartDate("2025-9-01");
        request.setEndDate("2025-10-31");
        request.setDateType(DateTypeConstants.DAY);
        List<PieStatisticsVo<Long>> pieStatisticsVos = statisticsService.lubricateRateStatistics(request);
        System.out.println(" 日= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
        request.setDateType(DateTypeConstants.MONTH);
        pieStatisticsVos = statisticsService.lubricateRateStatistics(request);
        System.out.println(" 月= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
        request.setDateType(DateTypeConstants.QUARTER);
        pieStatisticsVos = statisticsService.lubricateRateStatistics(request);
        System.out.println(" 季度= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
        request.setDateType(DateTypeConstants.YEAR);
        pieStatisticsVos = statisticsService.lubricateRateStatistics(request);
        System.out.println(" 年= ");
        for (PieStatisticsVo<Long> pieStatisticsVo : pieStatisticsVos) {
            System.out.println(pieStatisticsVo.getName() + ":" + pieStatisticsVo.getValue());
        }
    }

    @Test
    public void defectEliminate() {
        StatisticsRequest request = new StatisticsRequest();
        request.setStartDate("2025-01-01");
        request.setEndDate("2025-10-31");
        request.setDateType(DateTypeConstants.DAY);
        BarRateStatisticsVo<Long> barRateStatisticsVo = statisticsService.defectEliminateStatistics(request);
        System.out.println(" 日= ");
        System.out.println(barRateStatisticsVo);
        request.setDateType(DateTypeConstants.MONTH);
        barRateStatisticsVo = statisticsService.defectEliminateStatistics(request);
        System.out.println(" 月= ");
        System.out.println(barRateStatisticsVo);
        request.setDateType(DateTypeConstants.QUARTER);
        barRateStatisticsVo = statisticsService.defectEliminateStatistics(request);
        System.out.println(" 季度= ");
        System.out.println(barRateStatisticsVo);
        request.setDateType(DateTypeConstants.YEAR);
        barRateStatisticsVo = statisticsService.defectEliminateStatistics(request);
        System.out.println(" 年= ");
        System.out.println(barRateStatisticsVo);
    }

@Test
public void testTaskCount() {
    // 先不加任何过滤条件，查看是否有任务
    Long total = flowTaskService.processCount(null, null, null, null);
    System.out.println("总任务数: " + total);

    // 再单独测试类别过滤
    Long categoryCount = flowTaskService.processCount("1", null, null, null);
    System.out.println("类别1任务数: " + categoryCount);

    // 再单独测试状态过滤（使用正确的日期）
    // 修改为使用 Calendar 或 LocalDate
    Calendar cal = Calendar.getInstance();
    cal.set(2025, Calendar.SEPTEMBER, 1, 0, 0, 0); // 9月1日
    cal.set(Calendar.MILLISECOND, 0);
    Date startDate = cal.getTime();
    Date endDate = new Date(); // 当前时间

    Long activeCount = flowTaskService.processCount(null, "active", startDate, endDate);
    System.out.println("活跃任务数: " + activeCount);

    // 最后测试组合条件
    Long combinedCount = flowTaskService.processCount("1", "active", startDate, endDate);
    System.out.println("类别1且活跃任务数: " + combinedCount);
}

}
