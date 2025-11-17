package com.ruoyi.training.service.impl;

import com.ruoyi.choice.domain.BizContractorChoice;
import com.ruoyi.choice.mapper.BizContractorChoiceMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.training.domain.BizTrainingRecords;
import com.ruoyi.training.mapper.BizTrainingRecordsMapper;
import com.ruoyi.training.service.IBizTrainingRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 培训记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-10-24
 */
@Service
public class BizTrainingRecordsServiceImpl implements IBizTrainingRecordsService {
    @Autowired
    private BizTrainingRecordsMapper bizTrainingRecordsMapper;

    @Autowired
    private BizContractorChoiceMapper bizContractorChoiceMapper;

    // 用于存储每年的计数器，key为年份，value为该年份的计数器
    private static final Map<String, AtomicInteger> YEAR_COUNTER_MAP = new ConcurrentHashMap<>();

    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    // 生成培训编号
    private synchronized String generateTrainingCode() {
        String year = getCurrentYear();
        AtomicInteger counter = YEAR_COUNTER_MAP.computeIfAbsent(year, k -> new AtomicInteger(0));
        int count = counter.incrementAndGet();

        // 如果超过999，则重置为1
        if (count > 999) {
            counter.set(1);
            count = 1;
        }

        return String.format("PX%s%03d", year, count);
    }

    /**
     * 查询培训记录
     *
     * @param id 培训记录主键
     * @return 培训记录
     */
    @Override
    public BizTrainingRecords selectBizTrainingRecordsById(Long id) {
        return bizTrainingRecordsMapper.selectBizTrainingRecordsById(id);
    }

    /**
     * 查询培训记录列表
     *
     * @param bizTrainingRecords 培训记录
     * @return 培训记录
     */
    @Override
    public List<BizTrainingRecords> selectBizTrainingRecordsList(BizTrainingRecords bizTrainingRecords) {
        return bizTrainingRecordsMapper.selectBizTrainingRecordsList(bizTrainingRecords);
    }

    /**
     * 新增培训记录
     *
     * @param bizTrainingRecords 培训记录
     * @return 结果
     */
    @Override
    public int insertBizTrainingRecords(BizTrainingRecords bizTrainingRecords) {
        // 如果培训编号为空，则自动生成
        if (bizTrainingRecords.getTrainingCode() == null || bizTrainingRecords.getTrainingCode().isEmpty()) {
            String trainingCode = generateTrainingCode();
            bizTrainingRecords.setTrainingCode(trainingCode);
        }
        initData(bizTrainingRecords);
        return bizTrainingRecordsMapper.insertBizTrainingRecords(bizTrainingRecords);
    }

    private void initData(BizTrainingRecords bizTrainingRecords) {
        BizContractorChoice bizContractorChoice = bizContractorChoiceMapper.selectBizContractorChoiceByChoiceId(bizTrainingRecords.getChoiceId());
        if (StringUtils.isNull(bizContractorChoice)) {
            throw new RuntimeException("请选择项目！！！");
        }
        bizTrainingRecords.setProjectName(bizContractorChoice.getProjectName());
        bizTrainingRecords.setContractorName(bizContractorChoice.getSelectedContractor());
    }

    /**
     * 修改培训记录
     *
     * @param bizTrainingRecords 培训记录
     * @return 结果
     */
    @Override
    public int updateBizTrainingRecords(BizTrainingRecords bizTrainingRecords) {
        initData(bizTrainingRecords);
        return bizTrainingRecordsMapper.updateBizTrainingRecords(bizTrainingRecords);
    }

    /**
     * 批量删除培训记录
     *
     * @param ids 需要删除的培训记录主键
     * @return 结果
     */
    @Override
    public int deleteBizTrainingRecordsByIds(Long[] ids) {
        return bizTrainingRecordsMapper.deleteBizTrainingRecordsByIds(ids);
    }

    /**
     * 删除培训记录信息
     *
     * @param id 培训记录主键
     * @return 结果
     */
    @Override
    public int deleteBizTrainingRecordsById(Long id) {
        return bizTrainingRecordsMapper.deleteBizTrainingRecordsById(id);
    }
}
