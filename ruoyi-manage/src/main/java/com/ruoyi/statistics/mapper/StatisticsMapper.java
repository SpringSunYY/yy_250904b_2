package com.ruoyi.statistics.mapper;

import com.ruoyi.equip.domain.EquipRuntimeStat;
import com.ruoyi.inspe.domain.EquipInspe;
import com.ruoyi.inspe.domain.EquipInspeDanger;
import com.ruoyi.lubr.domain.EquipLubrPlan;
import com.ruoyi.lubr.domain.EquipLubrRecord2;
import com.ruoyi.statistics.domain.dto.StatisticsDto;
import com.ruoyi.statistics.domain.ro.StatisticsRo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 统计mapper接口
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-28  17:45
 * @Version: 1.0
 */
public interface StatisticsMapper {
    /**
     * 获取设备检查率
     *
     * @param statisticsDto
     * @return
     */
    public List<StatisticsRo> checkRateStatistics(StatisticsDto statisticsDto);

    /**
     * 获取设备运行时长
     *
     * @param statisticsDto
     * @return
     */
    List<EquipRuntimeStat> runTimeStatistics(StatisticsDto statisticsDto);

    /**
     * 获取设备保养计划
     *
     * @param statisticsDto 查询
     * @return
     */
    List<EquipLubrPlan> queryEquipLubrPlan(StatisticsDto statisticsDto);

    List<EquipLubrRecord2> queryEquipLubrRecord(StatisticsDto statisticsDto);

    /**
     * 获取设备检查记录
     *
     * @param request 查询
     * @return
     */
    List<EquipInspe> queryEquipInspeList(StatisticsDto request);

    /**
     * 获取设备检查隐患治理
     *
     * @param equipInspeIds 检查记录id
     * @return
     */
    List<EquipInspeDanger> queryEquipInspeDangerList(@Param("equipInspeIds") List<Long> equipInspeIds);

    /**
     * 获取密封点总数
     *
     * @return
     */
    Long getSealCount();

    List<StatisticsRo> statisticsSealManagement(StatisticsDto statisticsDto);

    /**
     * 获取流程实例统计
     *
     * @param startDate       开始时间
     * @param endDate         结束时间
     * @param dateType        时间类型
     * @param procDefId       流程
     * @param isActive        是否激活
     * @return
     */
    List<StatisticsRo> actHiProcinstStatistics(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("dateType") String dateType, @Param("procDefId") String procDefId, @Param("isActive") boolean isActive);

    /**
     * 查询设备缺陷
     * @author: YY
     * @method: statisticsEquipDefect
     * @date: 2025/11/1 17:49
     * @param statisticsDto
     * @return List<StatisticsRo>
     **/
    List<StatisticsRo> statisticsEquipDefect(StatisticsDto statisticsDto);
}
