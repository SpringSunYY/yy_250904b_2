package com.ruoyi.prepare.service;

import java.util.List;
import com.ruoyi.prepare.domain.BizSafetyCondition;

/**
 * 安全条件检查Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizSafetyConditionService 
{
    /**
     * 查询安全条件检查
     * 
     * @param conditionId 安全条件检查主键
     * @return 安全条件检查
     */
    public BizSafetyCondition selectBizSafetyConditionByConditionId(Long conditionId);

    /**
     * 查询安全条件检查列表
     * 
     * @param bizSafetyCondition 安全条件检查
     * @return 安全条件检查集合
     */
    public List<BizSafetyCondition> selectBizSafetyConditionList(BizSafetyCondition bizSafetyCondition);

    /**
     * 新增安全条件检查
     * 
     * @param bizSafetyCondition 安全条件检查
     * @return 结果
     */
    public int insertBizSafetyCondition(BizSafetyCondition bizSafetyCondition);

    /**
     * 修改安全条件检查
     * 
     * @param bizSafetyCondition 安全条件检查
     * @return 结果
     */
    public int updateBizSafetyCondition(BizSafetyCondition bizSafetyCondition);

    /**
     * 批量删除安全条件检查
     * 
     * @param conditionIds 需要删除的安全条件检查主键集合
     * @return 结果
     */
    public int deleteBizSafetyConditionByConditionIds(Long[] conditionIds);

    /**
     * 删除安全条件检查信息
     * 
     * @param conditionId 安全条件检查主键
     * @return 结果
     */
    public int deleteBizSafetyConditionByConditionId(Long conditionId);
}
