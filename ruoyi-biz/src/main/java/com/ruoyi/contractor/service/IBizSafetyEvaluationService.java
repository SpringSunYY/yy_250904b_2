package com.ruoyi.contractor.service;

import java.util.List;
import com.ruoyi.contractor.domain.BizSafetyEvaluation;

/**
 * 安全业绩评价Service接口
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
public interface IBizSafetyEvaluationService 
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
     * 批量删除安全业绩评价
     * 
     * @param ids 需要删除的安全业绩评价主键集合
     * @return 结果
     */
    public int deleteBizSafetyEvaluationByIds(Long[] ids);

    /**
     * 删除安全业绩评价信息
     * 
     * @param id 安全业绩评价主键
     * @return 结果
     */
    public int deleteBizSafetyEvaluationById(Long id);
}
