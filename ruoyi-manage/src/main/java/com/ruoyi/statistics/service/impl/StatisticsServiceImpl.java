package com.ruoyi.statistics.service.impl;

import com.ruoyi.common.annotation.CustomCacheable;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.bean.BeanUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.domain.EquipRuntimeStat;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.inspe.domain.EquipInspe;
import com.ruoyi.inspe.domain.EquipInspeDanger;
import com.ruoyi.lubr.domain.EquipLubrPlan;
import com.ruoyi.lubr.domain.EquipLubrRecord2;
import com.ruoyi.statistics.constants.DateTypeConstants;
import com.ruoyi.statistics.domain.dto.StatisticsDto;
import com.ruoyi.statistics.domain.dto.StatisticsRequest;
import com.ruoyi.statistics.domain.ro.StatisticsRo;
import com.ruoyi.statistics.domain.vo.BarRateStatisticsVo;
import com.ruoyi.statistics.domain.vo.BaseStatisticsVo;
import com.ruoyi.statistics.domain.vo.PieStatisticsVo;
import com.ruoyi.statistics.domain.vo.PieTotalRateStatisticsVo;
import com.ruoyi.statistics.mapper.StatisticsMapper;
import com.ruoyi.statistics.service.IStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.ruoyi.statistics.constants.RedisCacheConstants.*;

/**
 * 统计
 *
 * @Project: RuoYi-8
 * @Author: YY
 * @CreateTime: 2025-10-28  17:44
 * @Version: 1.0
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;
    @Resource
    private EquipLedgerMapper equipLedgerMapper;

    //region 设备点检完成率checkRate
    @CustomCacheable(keyPrefix = STATISTICS_CHECK_RATE,
            expireTime = REDIS_TIMEOUT,
            useQueryParamsAsKey = true)
    @Override
    public BaseStatisticsVo<Float> checkRateStatistics(StatisticsRequest request) {
        List<Long> equipIds = queryEquipLedger(request);
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setEquipIds(equipIds);
        statisticsDto.setStartDate(request.getStartDate());
        statisticsDto.setEndDate(request.getEndDate());
        statisticsDto.setDateType(request.getDateType());
        List<StatisticsRo> checkRates = statisticsMapper.checkRateStatistics(statisticsDto);

        BaseStatisticsVo<Float> baseStatisticsVo = new BaseStatisticsVo<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Float> values = new ArrayList<>();
        LinkedHashMap<String, Float> resultMap = new LinkedHashMap<>(checkRates.size());
        //初始化时间
        //如果传过来的时间类型是日
        if (DateTypeConstants.DAY.equals(request.getDateType())) {
            List<String> dateRanges = DateUtils.getDateRangesDays(request.getStartDate(), request.getEndDate());
            dateRanges.forEach(dateRange -> {
                resultMap.put(dateRange, 0f);
            });
            checkRates.forEach(checkRate -> {
                resultMap.put(checkRate.getName(), 100F);
            });
        }
        //如果传过来的时间类型是月
        else if (DateTypeConstants.MONTH.equals(request.getDateType())) {
            List<String> dateRangesMonths = DateUtils.getDateRangesMonths(request.getStartDate(), request.getEndDate());
            LinkedHashMap<String, Float> tempResultMap = new LinkedHashMap<>(checkRates.size());

            dateRangesMonths.forEach(dateRange -> {
                tempResultMap.put(dateRange, 0f);
            });
            checkRates.forEach(checkRate -> {
                String dateToStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtils.parseDate(checkRate.getName()));
                boolean containsKey = tempResultMap.containsKey(dateToStr);
                if (containsKey) {
                    tempResultMap.put(dateToStr, tempResultMap.get(dateToStr) + 1F);
                } else {
                    tempResultMap.put(dateToStr, 1F);
                }
            });
            //判断所在月共有多少天
            tempResultMap.forEach((date, rate) -> {
                int monthDayNumber = DateUtils.getMonthDayNumber(date);
                float monthRate = (monthDayNumber - rate) / monthDayNumber;
                //保留两位小数
                monthRate = (float) (Math.round(monthRate * 100)) / 100;
                resultMap.put(date, monthRate * 100);
            });
        } else if (DateTypeConstants.QUARTER.equals(request.getDateType())) {
            //是季度
            List<String> dateRangesQuarters = DateUtils.getDateRangesQuarters(request.getStartDate(), request.getEndDate());
            LinkedHashMap<String, Float> tempResultMap = new LinkedHashMap<>(checkRates.size());
            dateRangesQuarters.forEach(dateRange -> {
                tempResultMap.put(dateRange, 0f);
            });
            checkRates.forEach(checkRate -> {
                String dateToStr = DateUtils.formatQuarterDate(DateUtils.parseDate(checkRate.getName()));
                boolean containsKey = tempResultMap.containsKey(dateToStr);
                if (containsKey) {
                    tempResultMap.put(dateToStr, tempResultMap.get(dateToStr) + 1F);
                } else {
                    tempResultMap.put(dateToStr, 1F);
                }
            });
            tempResultMap.forEach((date, rate) -> {
                int quarterDayNumber = DateUtils.getQuarterDayNumber(date);
                float quarterRate = (quarterDayNumber - rate) / quarterDayNumber;
                //保留两位小数
                quarterRate = (float) (Math.round(quarterRate * 100)) / 100;
                resultMap.put(date, quarterRate * 100);
            });
        } else {
            //是年
            List<String> dateRangesYears = DateUtils.getDateRangesYears(request.getStartDate(), request.getEndDate());
            LinkedHashMap<String, Float> tempResultMap = new LinkedHashMap<>(checkRates.size());
            dateRangesYears.forEach(dateRange -> {
                tempResultMap.put(dateRange, 0f);
            });
            checkRates.forEach(checkRate -> {
                String dateToStr = DateUtils.parseDateToStr(DateUtils.YYYY, DateUtils.parseDate(checkRate.getName()));
                boolean containsKey = tempResultMap.containsKey(dateToStr);
                if (containsKey) {
                    tempResultMap.put(dateToStr, tempResultMap.get(dateToStr) + 1F);
                } else {
                    tempResultMap.put(dateToStr, 1F);
                }
            });
            tempResultMap.forEach((date, rate) -> {
                int yearDayNumber = DateUtils.getYearDayNumber(date);
                float yearRate = (yearDayNumber - rate) / yearDayNumber;
                //保留两位小数
                yearRate = (float) (Math.round(yearRate * 100)) / 100;
                resultMap.put(date, yearRate * 100);
            });
        }
        //遍历结果集
        resultMap.forEach((date, rate) -> {
            names.add(date);
            values.add(rate);
        });
        baseStatisticsVo.setNames(names);
        baseStatisticsVo.setValues(values);
        return baseStatisticsVo;
    }

    /**
     * 查询设备
     *
     * @param request
     * @return
     */
    private List<Long> queryEquipLedger(StatisticsRequest request) {
        //首先查询设备
        EquipLedger equipLedger = new EquipLedger();
        BeanUtils.copyBeanProp(equipLedger, request);
        List<EquipLedger> equipLedgers = equipLedgerMapper.selectEquipLedgerList(equipLedger);
        //拿到所有设备的ID
        return equipLedgers.stream().map(EquipLedger::getEquipId).collect(Collectors.toList());
    }
    //endregion

    //region 运行时长统计
    @CustomCacheable(keyPrefix = STATISTICS_RUN_TIME,
            expireTime = REDIS_TIMEOUT, useQueryParamsAsKey = true
    )
    @Override
    public List<PieStatisticsVo<Long>> runTimeStatistics(StatisticsRequest request) {
        //拿到所有的设备
        List<Long> equipIds = queryEquipLedger(request);
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setEquipIds(equipIds);
        String startDate = request.getStartDate();
        statisticsDto.setStartDate(startDate);
        String endDate = request.getEndDate();
        statisticsDto.setEndDate(endDate);
        String dateType = request.getDateType();
        statisticsDto.setDateType(dateType);
        //拿到所有的运行时间
        List<EquipRuntimeStat> equipRuntimeStats = statisticsMapper.runTimeStatistics(statisticsDto);
        ArrayList<PieStatisticsVo<Long>> pieStatisticsVos = new ArrayList<>();
        LinkedHashMap<String, Long> resultMap = new LinkedHashMap<>();
        LinkedHashMap<String, Long> runTimeTempMap = new LinkedHashMap<>();
        LinkedHashMap<String, Long> stopTimeTempMap = new LinkedHashMap<>();
        //如果是不天类型，则需要把时间初始化到本年、季度、月份到今天，防止空指针异常
        Date nowDate = DateUtils.getNowDate();
        if (!DateTypeConstants.DAY.equals(dateType)) {
            endDate = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, nowDate);
        }
        //初始化时间范围，计算未运行时间
        List<String> queryDateRange = DateUtils.getDateRangesDays(startDate, endDate);
        if (queryDateRange == null || queryDateRange.isEmpty()) {
            return pieStatisticsVos;
        }
        //初始化时间范围，计算未运行时间
        initTempRunMap(queryDateRange, stopTimeTempMap, runTimeTempMap, equipIds, nowDate);

        //先处理每一天的运行时间
        for (EquipRuntimeStat equipRuntimeStat : equipRuntimeStats) {
            processEquipRuntime(equipRuntimeStat, nowDate, runTimeTempMap, stopTimeTempMap);
        }
        if (runTimeTempMap.isEmpty()) {
            return pieStatisticsVos;
        }
        //构建运行结果
        return builderRunTimeResult(dateType, runTimeTempMap, stopTimeTempMap, resultMap, pieStatisticsVos);
    }

    /**
     * 处理运行时间
     *
     * @param equipRuntimeStat 运行记录
     * @param nowDate          当前时间
     * @param runTimeTempMap   运行时间
     * @param stopTimeTempMap  停机时间
     */
    private static void processEquipRuntime(EquipRuntimeStat equipRuntimeStat, Date nowDate, LinkedHashMap<String, Long> runTimeTempMap, LinkedHashMap<String, Long> stopTimeTempMap) {
        //如果结束时间为空,设置结束为当前时间
        if (equipRuntimeStat.getStopTime() == null) {
            equipRuntimeStat.setStopTime(nowDate);
        }
        //获取天数范围
        String startDateStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, equipRuntimeStat.getStartTime());
        String stopDateStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, equipRuntimeStat.getStopTime());
        List<String> dateRangesDays = DateUtils.getDateRangesDays(startDateStr,
                stopDateStr);
        if (dateRangesDays == null || dateRangesDays.isEmpty()) {
            return;
        }
        //如果时间范围为1天,则表示只有当天的
        if (dateRangesDays.size() == 1) {
            //直接获取当天的时间差
            processRuntime(equipRuntimeStat.getStartTime(), equipRuntimeStat.getStopTime(), runTimeTempMap, startDateStr, stopTimeTempMap);
        }
        //如果是多天，需要额外处理
        else {
            //先处理第一天
            processRuntime(equipRuntimeStat.getStartTime(), DateUtils.getDay(equipRuntimeStat.getStartTime(), 1), runTimeTempMap, startDateStr, stopTimeTempMap);
            //删除第一天
            dateRangesDays.remove(0);
            //在处理最后一天
            processRuntime(DateUtils.getDay(equipRuntimeStat.getStopTime(), 0), equipRuntimeStat.getStopTime(), runTimeTempMap, dateRangesDays.get(dateRangesDays.size() - 1), stopTimeTempMap);
            dateRangesDays.remove(dateRangesDays.size() - 1);
            if (dateRangesDays.isEmpty()) {
                return;
            }
            for (String dateStr : dateRangesDays) {
                Date date = DateUtils.parseDate(dateStr);
                processRuntime(date, DateUtils.getDay(date, 1), runTimeTempMap, dateStr, stopTimeTempMap);
            }
        }
    }

    /**
     * 构建运行时间返回结果
     *
     * @param dateType         时间类型
     * @param runTimeTempMap   运行时间
     * @param resultMap        运行结果
     * @param stopTimeTempMap  停机时间
     * @param pieStatisticsVos 运行结果
     * @return ArrayList<PieStatisticsVo < Long>>
     * @author: YY
     * @method: builderRunTimeResult
     * @date: 2025/10/30 15:47
     **/
    private static ArrayList<PieStatisticsVo<Long>> builderRunTimeResult(String dateType,
                                                                         LinkedHashMap<String, Long> runTimeTempMap,
                                                                         LinkedHashMap<String, Long> stopTimeTempMap,
                                                                         LinkedHashMap<String, Long> resultMap,
                                                                         ArrayList<PieStatisticsVo<Long>> pieStatisticsVos) {
        //如果传过来的时间是日
        if (DateTypeConstants.DAY.equals(dateType)) {
            runTimeTempMap.forEach((date, time) -> {
                //运行和停机时间
                resultMap.put(date + "-运行时间", time);
                resultMap.put(date + "-停机时间", stopTimeTempMap.get(date));
            });
        }
        //如果传过来的是月
        else if (DateTypeConstants.MONTH.equals(dateType)) {
            runTimeTempMap.forEach((date, time) -> {
                String month = DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtils.parseDate(date));
                //拿到停机时间
                Long stopTime = stopTimeTempMap.get(date);
                processRuntimeResultMap(time, stopTime, month, resultMap);
            });
        }
        //如果传过来是季
        else if (DateTypeConstants.QUARTER.equals(dateType)) {
            runTimeTempMap.forEach((date, time) -> {
                String quarter = DateUtils.formatQuarterDate(DateUtils.parseDate(date));
                Long stopTime = stopTimeTempMap.get(date);
                processRuntimeResultMap(time, stopTime, quarter, resultMap);
            });
        }
        //如果传过来是年
        else {
            runTimeTempMap.forEach((date, time) -> {
                String year = DateUtils.parseDateToStr(DateUtils.YYYY, DateUtils.parseDate(date));
                Long stopTime = stopTimeTempMap.get(date);
                processRuntimeResultMap(time, stopTime, year, resultMap);
            });
        }
        resultMap.forEach((date, time) -> {
            PieStatisticsVo<Long> pieStatisticsVo = new PieStatisticsVo<>();
            //需要运行时间和停机时间
            pieStatisticsVo.setName(date);
            pieStatisticsVo.setValue(time);
            pieStatisticsVos.add(pieStatisticsVo);
        });
        return pieStatisticsVos;
    }

    /**
     * 初始化运行时间和停机时间
     *
     * @param queryDateRange  查询时间的范围
     * @param stopTimeTempMap 停机时间
     * @param runTimeTempMap  运行时间
     * @param equipIds        设备ID
     * @param nowDate         当前时间
     */
    private static void initTempRunMap(List<String> queryDateRange,
                                       LinkedHashMap<String, Long> stopTimeTempMap,
                                       LinkedHashMap<String, Long> runTimeTempMap,
                                       List<Long> equipIds, Date nowDate) {
        queryDateRange.forEach(date -> {
            stopTimeTempMap.put(date, 0L);
            runTimeTempMap.put(date, 0L);
        });
        //遍历查询到的设备数，初始化未运行的时间
        equipIds.forEach(equipId -> {
            int rangeSize = queryDateRange.size();
            // 处理前面的所有完整日期（24小时）
            for (int i = 0; i < rangeSize - 1; i++) {
                String date = queryDateRange.get(i);
                Long currentValue = stopTimeTempMap.get(date);
                stopTimeTempMap.put(date, currentValue + 24L);
            }

            // 处理最后一个日期（到当前时间的部分小时）
            if (rangeSize > 0) {
                String lastDate = queryDateRange.get(rangeSize - 1);
                long hoursBetween = DateUtils.getHoursBetween(
                        DateUtils.parseDate(lastDate), nowDate);
                Long currentValue = stopTimeTempMap.get(lastDate);
                stopTimeTempMap.put(lastDate, currentValue + hoursBetween);
            }
        });
    }

    /**
     * 处理运行结果
     *
     * @param runTime   运行时间
     * @param stopTime  停机时间
     * @param dateStr   时间
     * @param resultMap 运行结果
     */
    private static void processRuntimeResultMap(Long runTime,
                                                Long stopTime, String dateStr,
                                                LinkedHashMap<String, Long> resultMap) {
        String runStr = dateStr + "-运行时间";
        if (resultMap.containsKey(runStr)) {
            resultMap.put(runStr, resultMap.get(runStr) + runTime);
        } else {
            resultMap.put(runStr, runTime);
        }
        String stopStr = dateStr + "-停机时间";
        if (resultMap.containsKey(stopStr)) {
            resultMap.put(stopStr, resultMap.get(stopStr) + stopTime);
        } else {
            resultMap.put(stopStr, stopTime);
        }
    }

    private static void processRuntime(Date startDate, Date endDate, LinkedHashMap<String, Long> runTimeTempMap, String startDateStr, LinkedHashMap<String, Long> stopTimeTempMap) {
        long timeDifference = DateUtils.getHoursBetween(startDate, endDate);
        runTimeTempMap.put(startDateStr, runTimeTempMap.get(startDateStr) + timeDifference);
        //处理停止时间
        stopTimeTempMap.put(startDateStr, stopTimeTempMap.get(startDateStr) - timeDifference);
    }

    //endregion

    //region 润滑率统计
    @CustomCacheable(keyPrefix = STATISTICS_LUBRICATE_RATE,
            expireTime = REDIS_TIMEOUT, useQueryParamsAsKey = true)
    @Override
    public ArrayList<PieStatisticsVo<Long>> lubricateRateStatistics(StatisticsRequest request) {
        //首先查询设备
        EquipLedger equipLedgerQuery = new EquipLedger();
        BeanUtils.copyBeanProp(equipLedgerQuery, request);
        List<EquipLedger> equipLedgers = equipLedgerMapper.selectEquipLedgerList(equipLedgerQuery);
        List<Long> equipIds = new ArrayList<>();
        HashMap<Long, String> equipMap = new HashMap<>();
        equipLedgers.forEach(equipLedger -> {
            equipIds.add(equipLedger.getEquipId());
            if (equipLedger.getEquName() != null) {
                equipMap.put(equipLedger.getEquipId(), equipLedger.getEquName());
            } else {
                equipMap.put(equipLedger.getEquipId(), equipLedger.getEquipId().toString());
            }
        });
        //拿到所有设备的ID
        StatisticsDto statisticsDto = new StatisticsDto();
        statisticsDto.setEquipIds(equipIds);
        String startDate = request.getStartDate();
        statisticsDto.setStartDate(startDate);
        String endDate = request.getEndDate();
        statisticsDto.setEndDate(endDate);
        String dateType = request.getDateType();
        statisticsDto.setDateType(dateType);
        //查询周期内计划
        List<EquipLubrPlan> equipLubrPlans = statisticsMapper.queryEquipLubrPlan(statisticsDto);
        //拿到周期内的执行次数
        List<EquipLubrRecord2> equipLubrRecords = statisticsMapper.queryEquipLubrRecord(statisticsDto);
        //初步处理结果-周期计划，计算每个设备的保养次数
        //处理周期内保养记录-如果同一天有记录只算一次，根据key-设备编号，value-为map，key-时间，value-保养次数
        Map<Long, Map<String, Long>> equipLubrRecordMap = new HashMap<>();
        Map<Long, Long> equipLubrPlanMap = new HashMap<>();
        processInitLubrDate(equipLubrPlans, equipLubrPlanMap, equipIds, equipLubrRecordMap, equipLubrRecords);
        //处理结果
        HashMap<String, Long> resultMap = new HashMap<>();
        //如果是天粒度
        if (DateTypeConstants.DAY.equals(dateType)) {
            //从计划里面获取到设备
            equipLubrPlanMap.forEach((equipId, planCount) -> {
                Map<String, Long> recordMap = equipLubrRecordMap.get(equipId);
                long count = recordMap.size();
                builderLubrResultMap(planCount, count, resultMap, equipId, equipMap, null);
            });
        }
        //如果是月
        if (DateTypeConstants.MONTH.equals(dateType)) {
            processLubrResultMap(equipLubrPlanMap, equipLubrRecordMap, resultMap, equipMap, DateUtils.YYYY_MM, (type, date) -> {
                return DateUtils.parseDateToStr(type, DateUtils.parseDate(date));
            });
        } else if (DateTypeConstants.QUARTER.equals(dateType)) {
            processLubrResultMap(equipLubrPlanMap, equipLubrRecordMap, resultMap, equipMap, DateUtils.YYYY_QQ, (type, date) -> {
                return DateUtils.formatQuarterDate(DateUtils.parseDate(date));
            });
        } else {
            processLubrResultMap(equipLubrPlanMap, equipLubrRecordMap, resultMap, equipMap, DateUtils.YYYY, (type, date) -> {
                return DateUtils.parseDateToStr(type, DateUtils.parseDate(date));
            });
        }
        ArrayList<PieStatisticsVo<Long>> pieStatisticsVos = new ArrayList<>();
        builderLeakageRateResult(resultMap, pieStatisticsVos);
        return pieStatisticsVos;
    }

    /**
     * 处理结果
     *
     * @param equipLubrPlanMap   周期内计划
     * @param equipLubrRecordMap 周期内保养记录
     * @param resultMap          结果
     * @param equipMap           设备名称
     * @param dateType           时间粒度
     * @param formatter          时间格式化
     */
    private static void processLubrResultMap(Map<Long, Long> equipLubrPlanMap,
                                             Map<Long, Map<String, Long>> equipLubrRecordMap,
                                             HashMap<String, Long> resultMap,
                                             HashMap<Long, String> equipMap,
                                             String dateType,
                                             BiFunction<String, String, String> formatter) {
        HashMap<Long, Map<String, Long>> monthTotalMap = new HashMap<>();
        equipLubrPlanMap.forEach((equipId, planCount) -> {
            Map<String, Long> recordMap = equipLubrRecordMap.get(equipId);
            HashMap<String, Long> equipMonthMap = new HashMap<>();
            //计算每个月的保养次数
            recordMap.forEach((date, count) -> {
                String month = formatter.apply(dateType, date);
                processLeakageRateTempMap(count, equipMonthMap, month);
            });
            monthTotalMap.put(equipId, equipMonthMap);
        });
        equipLubrPlanMap.forEach((equipId, planCount) -> {
            Map<String, Long> monthMap = monthTotalMap.get(equipId);
            monthMap.forEach((month, count) -> {
                builderLubrResultMap(planCount, count, resultMap, equipId, equipMap, month);
            });
        });
    }


    /**
     * 初始化数据
     *
     * @param equipLubrPlans     计划-数据库内
     * @param equipLubrPlanMap   计划-计算
     * @param equipIds           设备id
     * @param equipLubrRecordMap 记录-计算
     * @param equipLubrRecords   记录-数据库内
     * @return void
     * @author: YY
     * @method: processInitLubrDate
     * @date: 2025/10/30 21:14
     **/
    private static void processInitLubrDate(List<EquipLubrPlan> equipLubrPlans, Map<Long, Long> equipLubrPlanMap, List<Long> equipIds, Map<Long, Map<String, Long>> equipLubrRecordMap, List<EquipLubrRecord2> equipLubrRecords) {
        for (EquipLubrPlan plan : equipLubrPlans) {
            if (StringUtils.isNull(plan.getLubPoints()) || StringUtils.isNull(plan.getEquipId())) {
                continue;
            }
            Long equipId = plan.getEquipId();
            if (equipLubrPlanMap.containsKey(equipId)) {
                equipLubrPlanMap.put(equipId, equipLubrPlanMap.get(equipId) + plan.getLubPoints());
            } else {
                equipLubrPlanMap.put(equipId, plan.getLubPoints());
            }
        }

        //初始化记录数据
        equipIds.forEach(equipId -> {
            Map<String, Long> recordMap = new HashMap<>();
            equipLubrRecordMap.put(equipId, recordMap);
        });
        for (EquipLubrRecord2 record : equipLubrRecords) {
            if (StringUtils.isNull(record.getEquipId())) {
                continue;
            }
            Map<String, Long> recordMap = equipLubrRecordMap.get(record.getEquipId());
            if (StringUtils.isNull(recordMap)) {
                HashMap<String, Long> map = new HashMap<>();
                recordMap = map;
                equipLubrRecordMap.put(record.getEquipId(), map);
            }
            if (StringUtils.isNull(record.getLubDate())) {
                continue;
            }
            String dateToStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, record.getLubDate());
            if (recordMap.containsKey(dateToStr)) {
                continue;
            }
            recordMap.put(dateToStr, 1L);
        }
    }

    /**
     * 构建润滑统计结果
     *
     * @param planCount 润滑计划数
     * @param count     润滑计划数
     * @param resultMap 结果集
     */
    private static void builderLubrResultMap(Long planCount, long count, HashMap<String, Long> resultMap, Long equipId, Map<Long, String> equipMap, String dateType) {
        String equipName = equipMap.get(equipId);
        if (StringUtils.isNotEmpty(dateType)) {
            equipName = equipName + " " + dateType;
        }
        //已润滑
        String equiplAlready = equipName + "-已润滑";
        resultMap.put(equiplAlready, count);
        //未润滑
        String equiplNot = equipName + "-未润滑";
        resultMap.put(equiplNot, planCount - count);
    }
    //endregion

    //region 设备隐患整改率
    @CustomCacheable(keyPrefix = STATISTICS_DEFECT_ELIMINATE,
            expireTime = REDIS_TIMEOUT, useQueryParamsAsKey = true)
    @Override
    public BarRateStatisticsVo<Long> defectEliminateStatistics(StatisticsRequest request) {
        StatisticsDto statisticsDto = new StatisticsDto();
        BeanUtils.copyBeanProp(statisticsDto, request);
        //先查询周期内设备检查记录
        List<EquipInspe> equipInses = statisticsMapper.queryEquipInspeList(statisticsDto);
        BarRateStatisticsVo<Long> longBarRateStatisticsVo = new BarRateStatisticsVo<>();
        if (equipInses.isEmpty()) {
            return longBarRateStatisticsVo;
        }
        //转换为 map，key为检查记录id，value为时间
        HashMap<Long, String> inspeDateMap = new HashMap<>();
        for (EquipInspe inspe : equipInses) {
            if (StringUtils.isNull(inspe.getInspeDate())) {
                continue;
            }
            inspeDateMap.put(inspe.getInspeId(), DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD, inspe.getInspeDate()));
        }
        //在查询根据周期内检查记录得到设备隐患治理数
        //拿到所有的检查记录id
        List<Long> equipInspeIds = equipInses.stream().map(EquipInspe::getInspeId).collect(Collectors.toList());
        List<EquipInspeDanger> equipInspeDangers = statisticsMapper.queryEquipInspeDangerList(equipInspeIds);
        if (equipInspeDangers.isEmpty()) {
            return longBarRateStatisticsVo;
        }
        //转换为map，key为记录id，value为记录总数
        HashMap<Long, Long> dangerMap = new HashMap<>();
        //记录每个时间有多少个记录
        Map<String, Long> outerValueMap = new LinkedHashMap<>();
        Map<String, Long> innerValueMap = new LinkedHashMap<>();
        //初始化数据
        initDefectEliminateDate(equipInspeDangers, dangerMap, inspeDateMap, outerValueMap, innerValueMap);
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> innerValues = new ArrayList<>();
        ArrayList<Long> outerValues = new ArrayList<>();
        //假如时间类型是天,直接返回所有的比例
        if (DateTypeConstants.DAY.equals(request.getDateType())) {
            builderDefectEliminateResultMap(outerValueMap, names, innerValues, innerValueMap, outerValues);
        } else if (DateTypeConstants.MONTH.equals(request.getDateType())) {
            processDefectEliminateResultMap(outerValueMap, innerValueMap, names, innerValues, outerValues, DateUtils.YYYY_MM,
                    (dateType, date) -> {
                        return DateUtils.parseDateToStr(date, DateUtils.parseDate(date));
                    });
        } else if (DateTypeConstants.QUARTER.equals(request.getDateType())) {
            processDefectEliminateResultMap(outerValueMap, innerValueMap, names, innerValues, outerValues, DateUtils.YYYY_QQ,
                    (dateType, date) -> {
                        return DateUtils.formatQuarterDate(DateUtils.parseDate(date));
                    });
        } else {
            //是年
            processDefectEliminateResultMap(outerValueMap, innerValueMap, names, innerValues, outerValues, DateUtils.YYYY,
                    (dateType, date) -> {
                        return DateUtils.parseDateToStr(dateType, DateUtils.parseDate(date));
                    });
        }
        longBarRateStatisticsVo.setNames(names);
        longBarRateStatisticsVo.setInnerValues(innerValues);
        longBarRateStatisticsVo.setOuterValues(outerValues);
        return longBarRateStatisticsVo;
    }

    /**
     * 初始化数据
     *
     * @param equipInspeDangers 隐患治理数据
     * @param dangerMap         隐患治理数据
     * @param inspeDateMap      检查记录时间
     * @param outerValueMap     外层
     * @param innerValueMap     内层
     */
    private static void initDefectEliminateDate(List<EquipInspeDanger> equipInspeDangers, Map<Long, Long> dangerMap, Map<Long, String> inspeDateMap, Map<String, Long> outerValueMap, Map<String, Long> innerValueMap) {
        for (EquipInspeDanger danger : equipInspeDangers) {
            if (StringUtils.isEmpty(danger.getInspeId())) {
                continue;
            }
            long inspeId = Long.parseLong(danger.getInspeId());
            if (dangerMap.containsKey(inspeId)) {
                dangerMap.put(inspeId, dangerMap.get(inspeId) + 1);
            } else {
                dangerMap.put(inspeId, 1L);
            }
        }
        inspeDateMap.forEach((inspeId, date) -> {
            if (outerValueMap.containsKey(date)) {
                outerValueMap.put(date, outerValueMap.get(date) + 1);
            } else {
                outerValueMap.put(date, 1L);
            }
            //初始化一个数据
            if (!innerValueMap.containsKey(date)) {
                innerValueMap.put(date, 0L);
            }
            //如果存在检查记录，则记录数加对应的次数
            if (dangerMap.containsKey(inspeId)) {
                innerValueMap.put(date, innerValueMap.get(date) + dangerMap.get(inspeId));
            }
        });
    }

    /**
     * 构建结果
     *
     * @param outerValueMap 外层数据
     * @param innerValueMap 内层数据
     * @param names         名称
     * @param innerValues   内层数据
     * @param outerValues   外层数据
     */
    private static void builderDefectEliminateResultMap(Map<String, Long> outerValueMap, List<String> names, List<Long> innerValues, Map<String, Long> innerValueMap, List<Long> outerValues) {
        outerValueMap.forEach((date, count) -> {
            names.add(date);
            innerValues.add(innerValueMap.get(date));
            outerValues.add(count);
        });
    }

    /**
     * 处理结果
     *
     * @param outerValueMap 外层数据
     * @param innerValueMap 内层数据
     * @param names         名称
     * @param innerValues   内层数据
     * @param outerValues   外层数据
     * @param dateType      时间类型
     * @param formatter     时间格式化
     */
    private static void processDefectEliminateResultMap(Map<String, Long> outerValueMap,
                                                        Map<String, Long> innerValueMap,
                                                        List<String> names,
                                                        List<Long> innerValues,
                                                        List<Long> outerValues,
                                                        String dateType,
                                                        BiFunction<String, String, String> formatter) {
        //构建临时的innerValueTempMap、outerValueTempMap
        HashMap<String, Long> innerValueTempMap = new HashMap<>();
        HashMap<String, Long> outerValueTempMap = new HashMap<>();
        outerValueMap.forEach((date, count) -> {
            String dateToStr = formatter.apply(dateType, date);
            if (innerValueTempMap.containsKey(dateToStr)) {
                innerValueTempMap.put(dateToStr, innerValueTempMap.get(dateToStr) + innerValueMap.get(date));
                outerValueTempMap.put(dateToStr, outerValueTempMap.get(dateToStr) + count);
            } else {
                innerValueTempMap.put(dateToStr, innerValueMap.get(date));
                outerValueTempMap.put(dateToStr, count);
            }
        });
        builderDefectEliminateResultMap(outerValueTempMap, names, innerValues, innerValueTempMap, outerValues);
    }
    //endregion

    //region 设备泄露率
    @CustomCacheable(keyPrefix = STATISTICS_LEAKAGE_RATE,
            expireTime = REDIS_TIMEOUT, useQueryParamsAsKey = true)
    @Override
    public PieTotalRateStatisticsVo<Long> leakageRateStatistics(StatisticsRequest request) {
        PieTotalRateStatisticsVo<Long> statisticsVo = new PieTotalRateStatisticsVo<>();
        //拿到总数
        Long total = statisticsMapper.getSealCount();
        statisticsVo.setTotal(total);
        //拿到数据，泄露处置每一天的总数
        StatisticsDto statisticsDto = new StatisticsDto();
        BeanUtils.copyBeanProp(statisticsDto, request);
        List<StatisticsRo> statisticsRos = statisticsMapper.statisticsSealManagement(statisticsDto);
        if (StringUtils.isEmpty(statisticsRos)) {
            return statisticsVo;
        }
        //创建结果
        HashMap<String, Long> resultMap = new HashMap<>();
        ArrayList<PieStatisticsVo<Long>> pieStatisticsVos = new ArrayList<>();
        statisticsRos.forEach(statisticsRo -> {
            resultMap.put(statisticsRo.getName(), statisticsRo.getValue());
        });
        //如果是天
        if (DateTypeConstants.DAY.equals(request.getDateType())) {
            builderLeakageRateResult(resultMap, pieStatisticsVos);
        } else if (DateTypeConstants.MONTH.equals(request.getDateType())) {
            //构建临时的resultMap
            HashMap<String, Long> resultTempMap = new HashMap<>();
            resultMap.forEach((date, count) -> {
                String dateToStr = DateUtils.parseDateToStr(DateUtils.YYYY_MM, DateUtils.parseDate(date));
                //添加进临时resultMap
                processLeakageRateTempMap(count, resultTempMap, dateToStr);
            });
            //构建结果
            builderLeakageRateResult(resultTempMap, pieStatisticsVos);
        } else if (DateTypeConstants.QUARTER.equals(request.getDateType())) {
            HashMap<String, Long> resultTempMap = new HashMap<>();
            resultMap.forEach((date, count) -> {
                String dateToStr = DateUtils.formatQuarterDate(DateUtils.parseDate(date));
                processLeakageRateTempMap(count, resultTempMap, dateToStr);
            });
            builderLeakageRateResult(resultTempMap, pieStatisticsVos);
        } else {
            HashMap<String, Long> resultTempMap = new HashMap<>();
            resultMap.forEach((date, count) -> {
                String dateToStr = DateUtils.parseDateToStr(DateUtils.YYYY, DateUtils.parseDate(date));
                processLeakageRateTempMap(count, resultTempMap, dateToStr);
            });
            builderLeakageRateResult(resultTempMap, pieStatisticsVos);
        }
        statisticsVo.setData(pieStatisticsVos);
        return statisticsVo;
    }

    /**
     * 处理泄露处置结果临时map
     *
     * @param count         泄露处置结果
     * @param resultTempMap 临时结果
     * @param dateToStr     时间
     * @return void
     * @author: YY
     * @method: processLeakageRateTempMap
     * @date: 2025/10/31 22:22
     **/
    private static void processLeakageRateTempMap(Long count, HashMap<String, Long> resultTempMap, String dateToStr) {
        if (resultTempMap.containsKey(dateToStr)) {
            resultTempMap.put(dateToStr, resultTempMap.get(dateToStr) + count);
        } else {
            resultTempMap.put(dateToStr, count);
        }
    }

    /**
     * 构建泄露处置结果
     *
     * @param resultMap        泄露处置结果
     * @param pieStatisticsVos 饼状图结果
     * @return void
     * @author: YY
     * @method: builderLeakageRateResult
     * @date: 2025/10/31 22:22
     **/
    private static void builderLeakageRateResult(HashMap<String, Long> resultMap, ArrayList<PieStatisticsVo<Long>> pieStatisticsVos) {
        resultMap.forEach((date, count) -> {
            PieStatisticsVo<Long> pieStatisticsVo = new PieStatisticsVo<>();
            pieStatisticsVo.setName(date);
            pieStatisticsVo.setValue(count);
            pieStatisticsVos.add(pieStatisticsVo);
        });
    }
    //endregion

    //region 设备缺陷消除率
    @CustomCacheable(keyPrefix = STATISTICS_DEFECT_ELIMINATE_RATE,
    expireTime = REDIS_TIMEOUT,useQueryParamsAsKey = true)
    @Override
    public BarRateStatisticsVo<Long> defectEliminateRateStatistics(StatisticsRequest request) {
        //先查询流程内缺陷的数据
        List<StatisticsRo> formDefectRos = statisticsMapper.actHiProcinstStatistics(request.getStartDate(), request.getEndDate(), request.getDateType(), "flow_equip_defect", true);
        StatisticsDto statisticsDto = new StatisticsDto();
        //查询周期内的缺陷数
        BeanUtils.copyBeanProp(statisticsDto, request);
        List<StatisticsRo> defectRos = statisticsMapper.statisticsEquipDefect(statisticsDto);

        //查询流程内泄露数
        List<StatisticsRo> formLeakageRos = statisticsMapper.actHiProcinstStatistics(request.getStartDate(), request.getEndDate(), request.getDateType(), "flow_equip_seal", true);
        List<StatisticsRo> leakageRos = statisticsMapper.statisticsSealManagement(statisticsDto);
        //构建结果
        //分子
        Map<String, Long> molecularMap = new HashMap<>();
        //分母
        Map<String, Long> denominatorMap = new HashMap<>();
        //依次遍历结果，为数据赋值
        formDefectRos.forEach(statisticsRo -> {
            initDefectEliminateStatisticsData(statisticsRo, molecularMap);
        });
        formLeakageRos.forEach(statisticsRo -> {
            initDefectEliminateStatisticsData(statisticsRo, molecularMap);
        });
        defectRos.forEach(statisticsRo -> {
            initDefectEliminateStatisticsData(statisticsRo, denominatorMap);
            initDefectEliminateStatisticsData(statisticsRo, molecularMap);
        });
        leakageRos.forEach(statisticsRo -> {
            initDefectEliminateStatisticsData(statisticsRo, denominatorMap);
            initDefectEliminateStatisticsData(statisticsRo, molecularMap);
        });
        //相互便利分子和分母，防止数据数量不对
        molecularMap.forEach((date, count) -> {
            if (!denominatorMap.containsKey(date)) {
                denominatorMap.put(date, 0L);
            }
        });
        denominatorMap.forEach((date, count) -> {
            if (!molecularMap.containsKey(date)) {
                molecularMap.put(date, 0L);
            }
        });
        //重新排序分子和分母，按照key升序排序
        Map<String, Long> innerValueMap = new LinkedHashMap<>();
        Map<String, Long> outerValueMap = new LinkedHashMap<>();
        outerValueMap = molecularMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        innerValueMap = denominatorMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Long> innerValues = new ArrayList<>();
        ArrayList<Long> outerValues = new ArrayList<>();
        //假如时间类型是天,直接返回所有的比例
        if (DateTypeConstants.DAY.equals(request.getDateType())) {
            builderDefectEliminateResultMap(outerValueMap, names, innerValues, innerValueMap, outerValues);
        } else if (DateTypeConstants.MONTH.equals(request.getDateType())) {
            processDefectEliminateResultMap(outerValueMap, innerValueMap, names, innerValues, outerValues, DateUtils.YYYY_MM,
                    (dateType, date) -> {
                        return DateUtils.parseDateToStr(date, DateUtils.parseDate(date));
                    });
        } else if (DateTypeConstants.QUARTER.equals(request.getDateType())) {
            processDefectEliminateResultMap(outerValueMap, innerValueMap, names, innerValues, outerValues, DateUtils.YYYY_QQ,
                    (dateType, date) -> {
                        return DateUtils.formatQuarterDate(DateUtils.parseDate(date));
                    });
        } else {
            //是年
            processDefectEliminateResultMap(outerValueMap, innerValueMap, names, innerValues, outerValues, DateUtils.YYYY,
                    (dateType, date) -> {
                        return DateUtils.parseDateToStr(dateType, DateUtils.parseDate(date));
                    });
        }
        BarRateStatisticsVo<Long> longBarRateStatisticsVo = new BarRateStatisticsVo<>();
        longBarRateStatisticsVo.setNames(names);
        longBarRateStatisticsVo.setInnerValues(innerValues);
        longBarRateStatisticsVo.setOuterValues(outerValues);
        return longBarRateStatisticsVo;

    }

    private static void initDefectEliminateStatisticsData(StatisticsRo statisticsRo, Map<String, Long> molecularMap) {
        if (molecularMap.containsKey(statisticsRo.getName())) {
            molecularMap.put(statisticsRo.getName(), molecularMap.get(statisticsRo.getName()) + statisticsRo.getValue());
        } else {
            molecularMap.put(statisticsRo.getName(), statisticsRo.getValue());
        }
    }
    //endregion
}
