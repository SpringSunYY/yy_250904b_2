package com.ruoyi.statistics.service.imp;

import com.ruoyi.choice.domain.BizContractorChoice;
import com.ruoyi.choice.mapper.BizContractorChoiceMapper;
import com.ruoyi.choice.mapper.BizContractorContractMapper;
import com.ruoyi.common.annotation.CustomCacheable;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.contents.mapper.BizContractorQualifiedMapper;
import com.ruoyi.contractor.domain.BizQualificationAudit;
import com.ruoyi.contractor.mapper.BizQualificationAuditMapper;
import com.ruoyi.prepare.mapper.BizToolInventoryMapper;
import com.ruoyi.statistics.constants.DateTypeConstants;
import com.ruoyi.statistics.domain.dto.BizStatisticsDto;
import com.ruoyi.statistics.domain.jo.BizStatisticsTempJo;
import com.ruoyi.statistics.domain.ro.BizStatisticsRo;
import com.ruoyi.statistics.domain.ro.BizStatisticsToolRo;
import com.ruoyi.statistics.domain.vo.BizPieStatisticsVo;
import com.ruoyi.statistics.service.IBizStatisticsService;
import com.ruoyi.training.domain.BizTrainingRecords;
import com.ruoyi.training.mapper.BizTrainingAttendanceMapper;
import com.ruoyi.training.mapper.BizTrainingRecordsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static com.ruoyi.statistics.constants.RedisCacheConstants.*;

/**
 * 统计
 *
 * @Project: RuoYi-biz
 * @Author: YY
 * @CreateTime: 2025-11-13  22:33
 * @Version: 1.0
 */
@Service
public class BizStatisticsServiceImpl implements IBizStatisticsService {
    @Resource
    private BizTrainingRecordsMapper trainingRecordsMapper;

    @Resource
    private BizTrainingAttendanceMapper trainingAttendanceMapper;

    @Resource
    private BizToolInventoryMapper toolInventoryMapper;

    @Resource
    private BizContractorChoiceMapper bizContractorChoiceMapper;

    @Resource
    private BizContractorContractMapper bizContractorContractMapper;

    @Resource
    private BizQualificationAuditMapper bizQualificationAuditMapper;

    @Resource
    private BizContractorQualifiedMapper bizContractorQualifiedMapper;

    @CustomCacheable(
            keyPrefix = STATISTICS_TRAINING_RATE,
            expireTime = REDIS_TIMEOUT,
            useQueryParamsAsKey = true
    )
    @Override
    public List<BizPieStatisticsVo<Long>> trainingRateStatistics(BizStatisticsDto bizStatisticsDto) {
        // 首先拿到这个选择的时间段内所有培训计划
        Long choiceId = bizStatisticsDto.getChoiceId();
        String dateType = bizStatisticsDto.getDateType();
        String startDate = bizStatisticsDto.getStartDate();
        String endDate = bizStatisticsDto.getEndDate();

        List<BizTrainingRecords> records = trainingRecordsMapper.selectBizTrainingRecordsListByStatistics(
                choiceId, "1", dateType, startDate, endDate);

        if (StringUtils.isEmpty(records)) { // 使用 isEmpty 更合适
            return new ArrayList<>();
        }

        List<Long> trainingIds = new ArrayList<>();
        List<BizStatisticsTempJo<Long>> bizStatisticsTempJos = new ArrayList<>();

        for (BizTrainingRecords record : records) {
            trainingIds.add(record.getId());
            BizStatisticsTempJo<Long> longBizStatisticsTempJo = new BizStatisticsTempJo<>();
            longBizStatisticsTempJo.setName(record.getId());
            // 记录应该参加的人数
            longBizStatisticsTempJo.setTotal(record.getPlannedAttendees());
            longBizStatisticsTempJo.setDate(record.getCreatedAt());
            bizStatisticsTempJos.add(longBizStatisticsTempJo);
        }

        // 根据 ids 拿到所有培训计划下的培训记录
        List<BizStatisticsRo<Long>> recordHistoryList = trainingAttendanceMapper.selectTrainingRecordHistory(trainingIds);
        // 转为 map
        Map<Long, BizStatisticsRo<Long>> recordHistoryMap = new HashMap<>();
        recordHistoryList.forEach(recordHistory ->
                recordHistoryMap.put(Long.valueOf(String.valueOf(recordHistory.getName())), recordHistory));

        // 计算实际参加与未参加人数
        for (BizStatisticsTempJo<Long> bizStatisticsTempJo : bizStatisticsTempJos) {
            BizStatisticsRo<Long> bizStatisticsRo = recordHistoryMap.get(bizStatisticsTempJo.getName());
            if (bizStatisticsRo != null) {
                bizStatisticsTempJo.setValue1(bizStatisticsRo.getValue()); // 实际参加人数
                bizStatisticsTempJo.setValue2(bizStatisticsTempJo.getTotal() - bizStatisticsRo.getValue()); // 未参加人数
            } else {
                bizStatisticsTempJo.setValue1(0L);
                bizStatisticsTempJo.setValue2(bizStatisticsTempJo.getTotal());
            }
        }

        // 构建结果
        List<BizPieStatisticsVo<Long>> bizPieStatisticsVos = new ArrayList<>();

        // 如果时间粒度是天，则汇总总参加和未参加人数
        if (DateTypeConstants.DAY.equals(dateType)) {
            builderTrainingRateResult(bizStatisticsTempJos, bizPieStatisticsVos, null);
        } else if (DateTypeConstants.MONTH.equals(dateType)) {
            processTrainingRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY_MM, DateUtils::parseDateToStr);
        } else if (DateTypeConstants.QUARTER.equals(dateType)) {
            processTrainingRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY_QQ, (type, date) -> {
                return DateUtils.formatQuarterDate(date);
            });
        } else {
            processTrainingRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY, DateUtils::parseDateToStr);
        }
        return bizPieStatisticsVos;
    }

    private static void processTrainingRateResult(List<BizStatisticsTempJo<Long>> bizStatisticsTempJos,
                                                  List<BizPieStatisticsVo<Long>> bizPieStatisticsVos,
                                                  String dateType,
                                                  BiFunction<String, Date, String> formatter) {
        //构建一个时间的map
        Map<String, List<BizStatisticsTempJo<Long>>> dateMap = new HashMap<>();
        for (BizStatisticsTempJo<Long> tempJo : bizStatisticsTempJos) {
            String dateToStr = formatter.apply(dateType, tempJo.getDate());
            if (!dateMap.containsKey(dateToStr)) {
                ArrayList<BizStatisticsTempJo<Long>> value = new ArrayList<>();
                value.add(tempJo);
                dateMap.put(dateToStr, value);
            } else {
                dateMap.get(dateToStr).add(tempJo);
            }
        }
        for (Map.Entry<String, List<BizStatisticsTempJo<Long>>> entry : dateMap.entrySet()) {
            String key = entry.getKey();
            List<BizStatisticsTempJo<Long>> value = entry.getValue();
            builderTrainingRateResult(value, bizPieStatisticsVos, key);
        }
    }

    private static void builderTrainingRateResult(List<BizStatisticsTempJo<Long>> bizStatisticsTempJos,
                                                  List<BizPieStatisticsVo<Long>> bizPieStatisticsVos,
                                                  String prefix) {
        Long joinCount = 0L;
        Long notJoinCount = 0L;
        String joinName = "已参加";
        String notJoinName = "未参加";
        if (StringUtils.isNotEmpty(prefix)) {
            joinName = prefix + "-已参加";
            notJoinName = prefix + "-未参加";
        }
        for (BizStatisticsTempJo<Long> tempJo : bizStatisticsTempJos) {
            joinCount += tempJo.getValue1();     // 正确累加参加人数
            notJoinCount += tempJo.getValue2();   // 正确累加未参加人数
        }

        if (joinCount > 0L) {
            BizPieStatisticsVo<Long> join = new BizPieStatisticsVo<>();
            join.setName(joinName);
            join.setValue(joinCount);
            bizPieStatisticsVos.add(join);
        }

        if (notJoinCount > 0L) {
            BizPieStatisticsVo<Long> notJoin = new BizPieStatisticsVo<>();
            notJoin.setName(notJoinName);
            notJoin.setValue(notJoinCount);
            bizPieStatisticsVos.add(notJoin);
        }
    }

    @CustomCacheable(keyPrefix = STATISTICS_TOOL_INVENTORY_RATE,
            expireTime = REDIS_TIMEOUT,
            useQueryParamsAsKey = true)
    @Override
    public List<BizPieStatisticsVo<Long>> toolInventoryRateStatistics(BizStatisticsDto bizStatisticsDto) {
        //查询到工具
        Long choiceId = bizStatisticsDto.getChoiceId();
        String dateType = bizStatisticsDto.getDateType();
        String startDate = bizStatisticsDto.getStartDate();
        String endDate = bizStatisticsDto.getEndDate();
        List<BizStatisticsToolRo> ros = toolInventoryMapper.selectToolInventoryStatistics(choiceId, dateType, startDate, endDate);
        ArrayList<BizPieStatisticsVo<Long>> result = new ArrayList<>();
        //构建结果，如果是天
        if (DateTypeConstants.DAY.equals(dateType)) {
            builderToolInventoryRateResult(null, ros, result);
        } else if (DateTypeConstants.MONTH.equals(dateType)) {
            processToolInventoryRateResult(ros, result, DateUtils.YYYY_MM, (type, date) -> {
                return DateUtils.parseDateToStr(type, DateUtils.parseDate(date));
            });
        } else if (DateTypeConstants.QUARTER.equals(dateType)) {
            processToolInventoryRateResult(ros, result, DateUtils.YYYY_QQ, (type, date) -> {
                return DateUtils.formatQuarterDate(DateUtils.parseDate(date));
            });
        } else {
            processToolInventoryRateResult(ros, result, DateUtils.YYYY, (type, date) -> {
                return DateUtils.parseDateToStr(type, DateUtils.parseDate(date));
            });
        }
        return result;

    }


    private void processToolInventoryRateResult(List<BizStatisticsToolRo> ros,
                                                ArrayList<BizPieStatisticsVo<Long>> result,
                                                String dateType,
                                                BiFunction<String, String, String> formatter) {
        //构建一个时间的map
        Map<String, List<BizStatisticsToolRo>> dateMap = new HashMap<>();
        for (BizStatisticsToolRo ro : ros) {
            String dateToStr = formatter.apply(dateType, ro.getDate());
            if (!dateMap.containsKey(dateToStr)) {
                ArrayList<BizStatisticsToolRo> value = new ArrayList<>();
                value.add(ro);
                dateMap.put(dateToStr, value);
            } else {
                dateMap.get(dateToStr).add(ro);
            }
        }
        for (Map.Entry<String, List<BizStatisticsToolRo>> entry : dateMap.entrySet()) {
            String key = entry.getKey();
            List<BizStatisticsToolRo> value = entry.getValue();
            builderToolInventoryRateResult(key, value, result);
        }
    }

    private static void builderToolInventoryRateResult(String prefix, List<BizStatisticsToolRo> ros, ArrayList<BizPieStatisticsVo<Long>> result) {
        Long totalValue = 0L;
        Long intactValue = 0L;
        for (BizStatisticsToolRo ro : ros) {
            totalValue += ro.getValue();
            if (ro.getIntegrityCheck().equals("1")) {
                intactValue += ro.getValue();
            }
        }
        String intactName = "完好";
        String noIntactName = "非完好";
        if (StringUtils.isNotEmpty(prefix)) {
            intactName = prefix + "-完好";
            noIntactName = prefix + "-非完好";
        }
        BizPieStatisticsVo<Long> intactVo = new BizPieStatisticsVo<>();
        intactVo.setName(intactName);
        intactVo.setValue(intactValue);
        BizPieStatisticsVo<Long> noIntactVo = new BizPieStatisticsVo<>();
        noIntactVo.setName(noIntactName);
        noIntactVo.setValue(totalValue - intactValue);
        result.add(intactVo);
        result.add(noIntactVo);
    }


    @CustomCacheable(keyPrefix = STATISTICS_SECURITY_AGREEMENT_RATE,
            expireTime = REDIS_TIMEOUT,
            useQueryParamsAsKey = true)
    @Override
    public List<BizPieStatisticsVo<Long>> securityAgreementRateStatistics(BizStatisticsDto bizStatisticsDto) {
        //查询到选择承包商总数
        String dateType = bizStatisticsDto.getDateType();
        String startDate = bizStatisticsDto.getStartDate();
        String endDate = bizStatisticsDto.getEndDate();
        List<BizContractorChoice> choiceRos = bizContractorChoiceMapper.selectContractorChoiceStatistics(dateType, startDate, endDate);
        if (StringUtils.isEmpty(choiceRos)) {
            return new ArrayList<>();
        }
        List<Long> choiceIds = choiceRos.stream().map(BizContractorChoice::getChoiceId).collect(Collectors.toList());
        //查询时间范围内的所有协议
        List<BizStatisticsRo<Long>> contractRos = bizContractorContractMapper.selectContractorContractStatistics(choiceIds);
        //创建map，遍历每个结果有多少个
        Map<Long, Long> tempMap = new HashMap<>();
        for (BizStatisticsRo<Long> ro : contractRos) {
            long choiceId = Long.parseLong(String.valueOf(ro.getName()));
            if (!tempMap.containsKey(choiceId)) {
                tempMap.put(choiceId, ro.getValue());
            } else {
                tempMap.put(choiceId, tempMap.get(choiceId) + ro.getValue());
            }
        }
        //创建临时结果
        List<BizStatisticsTempJo<Long>> bizStatisticsTempJos = new ArrayList<>();
        for (BizContractorChoice choiceRo : choiceRos) {
            BizStatisticsTempJo<Long> longBizStatisticsTempJo = new BizStatisticsTempJo<>();
            longBizStatisticsTempJo.setName(choiceRo.getChoiceId());
            longBizStatisticsTempJo.setTotal(1L);
            //value1是值
            longBizStatisticsTempJo.setValue1(1L);
            longBizStatisticsTempJo.setValue2(tempMap.getOrDefault(choiceRo.getChoiceId(), 0L));
            longBizStatisticsTempJo.setDate(choiceRo.getCreatedAt());
            bizStatisticsTempJos.add(longBizStatisticsTempJo);
        }
        List<BizPieStatisticsVo<Long>> bizPieStatisticsVos = new ArrayList<>();
        //遍历结果集，构建结果
        if (dateType.equals(DateTypeConstants.DAY)) {
            //如果是天表示统计所有
            builderSecurityAgreementRateResult(null, bizStatisticsTempJos, bizPieStatisticsVos);
        } else if (dateType.equals(DateTypeConstants.MONTH)) {
            processSecurityAgreementRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY_MM, DateUtils::parseDateToStr);
        } else if (dateType.equals(DateTypeConstants.QUARTER)) {
            processSecurityAgreementRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY_QQ, (type, date) -> {
                return DateUtils.formatQuarterDate(date);
            });
        } else if (dateType.equals(DateTypeConstants.YEAR)) {
            processSecurityAgreementRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY, DateUtils::parseDateToStr);
        }
        return bizPieStatisticsVos;

    }

    private void processSecurityAgreementRateResult(List<BizStatisticsTempJo<Long>> bizStatisticsTempJos,
                                                    List<BizPieStatisticsVo<Long>> bizPieStatisticsVos,
                                                    String dateType,
                                                    BiFunction<String, Date, String> formatter) {
        Map<String, List<BizStatisticsTempJo<Long>>> dateMap = new HashMap<>();
        for (BizStatisticsTempJo<Long> bizStatisticsTempJo : bizStatisticsTempJos) {
            String date = formatter.apply(dateType, bizStatisticsTempJo.getDate());
            if (!dateMap.containsKey(date)) {
                ArrayList<BizStatisticsTempJo<Long>> value = new ArrayList<>();
                value.add(bizStatisticsTempJo);
                dateMap.put(date, value);
            } else {
                dateMap.get(date).add(bizStatisticsTempJo);
            }
        }
        for (Map.Entry<String, List<BizStatisticsTempJo<Long>>> entry : dateMap.entrySet()) {
            builderSecurityAgreementRateResult(entry.getKey(), entry.getValue(), bizPieStatisticsVos);
        }
    }

    private static void builderSecurityAgreementRateResult(String prefix,
                                                           List<BizStatisticsTempJo<Long>> bizStatisticsTempJos,
                                                           List<BizPieStatisticsVo<Long>> bizPieStatisticsVos) {
        Long totalValue = 0L;
        Long contractValue = 0L;
        for (BizStatisticsTempJo<Long> bizStatisticsTempJo : bizStatisticsTempJos) {
            totalValue++;
            contractValue += bizStatisticsTempJo.getValue2();
        }
        String noContractName = "无协议";
        String contractName = "有协议";
        if (StringUtils.isNotEmpty(prefix)) {
            noContractName = prefix + "-无协议";
            contractName = prefix + "-有协议";
        }
        BizPieStatisticsVo<Long> noContractVo = new BizPieStatisticsVo<>();
        noContractVo.setName(noContractName);
        noContractVo.setValue(totalValue - contractValue);
        bizPieStatisticsVos.add(noContractVo);
        BizPieStatisticsVo<Long> contractVo = new BizPieStatisticsVo<>();
        contractVo.setName(contractName);
        contractVo.setValue(contractValue);
        bizPieStatisticsVos.add(contractVo);
    }


    @CustomCacheable(keyPrefix = STATISTICS_QUALIFICATION_RATE,
            expireTime = REDIS_TIMEOUT,
            useQueryParamsAsKey = true
    )
    @Override
    public List<BizPieStatisticsVo<Long>> qualificationRateStatistics(BizStatisticsDto bizStatisticsDto) {
        String dateType = bizStatisticsDto.getDateType();
        String startDate = bizStatisticsDto.getStartDate();
        String endDate = bizStatisticsDto.getEndDate();
        //查询到所有的承包商资格
        List<BizQualificationAudit> audits = bizQualificationAuditMapper.selectBizQualificationAuditByStatistics(dateType, startDate, endDate);
        if (StringUtils.isEmpty(audits)) {
            return new ArrayList<>();
        }
        //拿到所有的auditId
        List<Long> auditIds = audits.stream().map(BizQualificationAudit::getAuditId).collect(Collectors.toList());
        //根据auditId查询统计所有的合格承包商
        List<BizStatisticsRo<Long>> auditRos = bizContractorQualifiedMapper.selectBizContractorQualifiedByAuditIds(auditIds);
        //转为map
        Map<Long, Long> auditMap = new HashMap<>(auditRos.size());
        for (BizStatisticsRo<Long> auditRo : auditRos) {
            auditMap.put(Long.parseLong(String.valueOf(auditRo.getName())), auditRo.getValue());
        }
        //创建临时结果
        List<BizStatisticsTempJo<Long>> bizStatisticsTempJos = new ArrayList<>();
        for (BizQualificationAudit audit : audits) {
            BizStatisticsTempJo<Long> longBizStatisticsTempJo = new BizStatisticsTempJo<>();
            longBizStatisticsTempJo.setName(audit.getAuditId());
            //value1存储1
            longBizStatisticsTempJo.setValue1(1L);
            //value2存储auditId对应的数量
            longBizStatisticsTempJo.setValue2(auditMap.getOrDefault(audit.getAuditId(), 0L));
            longBizStatisticsTempJo.setDate(audit.getApplyDate());
            bizStatisticsTempJos.add(longBizStatisticsTempJo);
        }
        List<BizPieStatisticsVo<Long>> bizPieStatisticsVos = new ArrayList<>();
        if (dateType.equals(DateTypeConstants.DAY)) {
            //如果是天表示统计所有
            builderQualificationRateResult(null, bizStatisticsTempJos, bizPieStatisticsVos);
        } else if (dateType.equals(DateTypeConstants.MONTH)) {
            processQualificationRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY_MM, DateUtils::parseDateToStr);
        } else if (dateType.equals(DateTypeConstants.QUARTER)) {
            processQualificationRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY_QQ, (type, date) -> {
                return DateUtils.formatQuarterDate(date);
            });
        } else {
            processQualificationRateResult(bizStatisticsTempJos, bizPieStatisticsVos, DateUtils.YYYY, DateUtils::parseDateToStr);
        }
        return bizPieStatisticsVos;
    }

    private void processQualificationRateResult(List<BizStatisticsTempJo<Long>> bizStatisticsTempJos,
                                                List<BizPieStatisticsVo<Long>> bizPieStatisticsVos,
                                                String dateType,
                                                BiFunction<String, Date, String> formatter) {
        Map<String, List<BizStatisticsTempJo<Long>>> dateMap = new HashMap<>();
        for (BizStatisticsTempJo<Long> bizStatisticsTempJo : bizStatisticsTempJos) {
            String date = formatter.apply(dateType, bizStatisticsTempJo.getDate());
            if (!dateMap.containsKey(date)) {
                ArrayList<BizStatisticsTempJo<Long>> value = new ArrayList<>();
                value.add(bizStatisticsTempJo);
                dateMap.put(date, value);
            } else {
                dateMap.get(date).add(bizStatisticsTempJo);
            }
        }
        for (Map.Entry<String, List<BizStatisticsTempJo<Long>>> entry : dateMap.entrySet()) {
            builderQualificationRateResult(entry.getKey(), entry.getValue(), bizPieStatisticsVos);
        }
    }

    private void builderQualificationRateResult(String profix,
                                                List<BizStatisticsTempJo<Long>> bizStatisticsTempJos,
                                                List<BizPieStatisticsVo<Long>> bizPieStatisticsVos) {
        Long totalValue = 0L;
        Long qualifiedValue = 0L;
        for (BizStatisticsTempJo<Long> bizStatisticsTempJo : bizStatisticsTempJos) {
            totalValue++;
            qualifiedValue += bizStatisticsTempJo.getValue2();
        }
        String noQualifiedName = "未通过";
        String qualifiedName = "通过";
        if (StringUtils.isNotEmpty(profix)) {
            noQualifiedName = profix + "-未通过";
            qualifiedName = profix + "-通过";
        }
        BizPieStatisticsVo<Long> noQualifiedVo = new BizPieStatisticsVo<>();
        noQualifiedVo.setName(noQualifiedName);
        noQualifiedVo.setValue(totalValue - qualifiedValue);
        bizPieStatisticsVos.add(noQualifiedVo);
        BizPieStatisticsVo<Long> qualifiedVo = new BizPieStatisticsVo<>();
        qualifiedVo.setName(qualifiedName);
        qualifiedVo.setValue(qualifiedValue);
        bizPieStatisticsVos.add(qualifiedVo);
    }
}
