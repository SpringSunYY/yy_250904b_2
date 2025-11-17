package com.ruoyi.contractor.service.impl;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contractor.mapper.BizSafetyEvaluationMapper;
import com.ruoyi.contractor.domain.BizSafetyEvaluation;
import com.ruoyi.contractor.service.IBizSafetyEvaluationService;

/**
 * 安全业绩评价Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
@Service
public class BizSafetyEvaluationServiceImpl implements IBizSafetyEvaluationService 
{
    @Autowired
    private BizSafetyEvaluationMapper bizSafetyEvaluationMapper;

    /**
     * 查询安全业绩评价
     * 
     * @param id 安全业绩评价主键
     * @return 安全业绩评价
     */
    @Override
    public BizSafetyEvaluation selectBizSafetyEvaluationById(Long id)
    {
        return bizSafetyEvaluationMapper.selectBizSafetyEvaluationById(id);
    }

    /**
     * 查询安全业绩评价列表
     * 
     * @param bizSafetyEvaluation 安全业绩评价
     * @return 安全业绩评价
     */
    @Override
    public List<BizSafetyEvaluation> selectBizSafetyEvaluationList(BizSafetyEvaluation bizSafetyEvaluation)
    {
        return bizSafetyEvaluationMapper.selectBizSafetyEvaluationList(bizSafetyEvaluation);
    }

    /**
     * 新增安全业绩评价
     * 
     * @param bizSafetyEvaluation 安全业绩评价
     * @return 结果
     */
    @Override
    public int insertBizSafetyEvaluation(BizSafetyEvaluation bizSafetyEvaluation)
    {
        // 自动生成评价编号
        if (bizSafetyEvaluation.getEvaluationCode() == null || bizSafetyEvaluation.getEvaluationCode().isEmpty()) {
            String evaluationCode = generateEvaluationCode();
            bizSafetyEvaluation.setEvaluationCode(evaluationCode);
        }
        
        // 设置创建时间
        if (bizSafetyEvaluation.getCreatedAt() == null) {
            bizSafetyEvaluation.setCreatedAt(new Date());
        }
        
        return bizSafetyEvaluationMapper.insertBizSafetyEvaluation(bizSafetyEvaluation);
    }

    /**
     * 修改安全业绩评价
     * 
     * @param bizSafetyEvaluation 安全业绩评价
     * @return 结果
     */
    @Override
    public int updateBizSafetyEvaluation(BizSafetyEvaluation bizSafetyEvaluation)
    {
        // 设置更新时间
        if (bizSafetyEvaluation.getUpdatedAt() == null) {
            bizSafetyEvaluation.setUpdatedAt(new Date());
        }
        
        return bizSafetyEvaluationMapper.updateBizSafetyEvaluation(bizSafetyEvaluation);
    }

    /**
     * 批量删除安全业绩评价
     * 
     * @param ids 需要删除的安全业绩评价主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyEvaluationByIds(Long[] ids)
    {
        return bizSafetyEvaluationMapper.deleteBizSafetyEvaluationByIds(ids);
    }

    /**
     * 删除安全业绩评价信息
     * 
     * @param id 安全业绩评价主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyEvaluationById(Long id)
    {
        return bizSafetyEvaluationMapper.deleteBizSafetyEvaluationById(id);
    }
    
    /**
     * 生成评价编号
     * 编号格式: PJ + 年份 + 三位序号 (例如: PJ2025102)
     * 每年最大编号为PJ + 年份 + 999
     * 
     * @return 评价编号
     */
    private String generateEvaluationCode() {
        // 获取当前年份
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String yearPrefix = "PJ" + year;
        
        // 查询当年最大的编号
        String maxNo = bizSafetyEvaluationMapper.selectMaxEvaluationCodeByYearPrefix(yearPrefix);
        
        int seq = 1;
        if (maxNo != null && !maxNo.isEmpty()) {
            // 从最大编号中提取序号部分并加1
            try {
                String seqStr = maxNo.substring(6); // "PJ" + "2025" = 6位
                seq = Integer.parseInt(seqStr) + 1;
            } catch (Exception e) {
                // 如果解析失败，使用默认序号1
                seq = 1;
            }
        }
        
        // 检查序号是否超过最大值999
        if (seq > 999) {
            throw new RuntimeException("当年编号已达到最大值PJ" + year + "999，请联系系统管理员");
        }
        
        // 格式化为3位数字，不足的前面补0
        return yearPrefix + String.format("%03d", seq);
    }
}
