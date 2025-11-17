package com.ruoyi.contractor.mapper;

import java.util.List;
import com.ruoyi.contractor.domain.BizSafetyEvaluation;

/**
 * 安全业绩评价Mapper接口
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
public interface BizSafetyEvaluationMapper 
{
    /**
     * 查询安全业绩评价
     * 
     * @param id 安全业绩评价主键
     * @return 安全业绩评价
     */
    public BizSafetyEvaluation selectBizSafetyEvaluationById(Long id);

    /**
     * 查询安全业绩评价列表
     * 
     * @param bizSafetyEvaluation 安全业绩评价
     * @return 安全业绩评价集合
     */
    public List<BizSafetyEvaluation> selectBizSafetyEvaluationList(BizSafetyEvaluation bizSafetyEvaluation);

    /**
     * 新增安全业绩评价
     * 
     * @param bizSafetyEvaluation 安全业绩评价
     * @return 结果
     */
    public int insertBizSafetyEvaluation(BizSafetyEvaluation bizSafetyEvaluation);

    /**
     * 修改安全业绩评价
     * 
     * @param bizSafetyEvaluation 安全业绩评价
     * @return 结果
     */
    public int updateBizSafetyEvaluation(BizSafetyEvaluation bizSafetyEvaluation);

    /**
     * 删除安全业绩评价
     * 
     * @param id 安全业绩评价主键
     * @return 结果
     */
    public int deleteBizSafetyEvaluationById(Long id);

    /**
     * 批量删除安全业绩评价
     * 
     * @param ids 需要删除的数据主键数组
     * @return 结果
     */
    public int deleteBizSafetyEvaluationByIds(Long[] ids);
    
    /**
     * 查询指定年份前缀的最大评价编号
     * 
     * @param yearPrefix 年份前缀，格式为"PJyyyy"
     * @return 最大编号
     */
    public String selectMaxEvaluationCodeByYearPrefix(String yearPrefix);
}
