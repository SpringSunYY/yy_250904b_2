package com.ruoyi.prepare.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.prepare.mapper.BizSafetyConditionMapper;
import com.ruoyi.prepare.domain.BizSafetyCondition;
import com.ruoyi.prepare.service.IBizSafetyConditionService;

/**
 * 安全条件检查Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizSafetyConditionServiceImpl implements IBizSafetyConditionService 
{
    @Autowired
    private BizSafetyConditionMapper bizSafetyConditionMapper;

    /**
     * 查询安全条件检查
     * 
     * @param conditionId 安全条件检查主键
     * @return 安全条件检查
     */
    @Override
    public BizSafetyCondition selectBizSafetyConditionByConditionId(Long conditionId)
    {
        return bizSafetyConditionMapper.selectBizSafetyConditionByConditionId(conditionId);
    }

    /**
     * 查询安全条件检查列表
     * 
     * @param bizSafetyCondition 安全条件检查
     * @return 安全条件检查
     */
    @Override
    public List<BizSafetyCondition> selectBizSafetyConditionList(BizSafetyCondition bizSafetyCondition)
    {
        return bizSafetyConditionMapper.selectBizSafetyConditionList(bizSafetyCondition);
    }

    /**
     * 新增安全条件检查
     * 
     * @param bizSafetyCondition 安全条件检查
     * @return 结果
     */
    @Override
    public int insertBizSafetyCondition(BizSafetyCondition bizSafetyCondition)
    {
        return bizSafetyConditionMapper.insertBizSafetyCondition(bizSafetyCondition);
    }

    /**
     * 修改安全条件检查
     * 
     * @param bizSafetyCondition 安全条件检查
     * @return 结果
     */
    @Override
    public int updateBizSafetyCondition(BizSafetyCondition bizSafetyCondition)
    {
        return bizSafetyConditionMapper.updateBizSafetyCondition(bizSafetyCondition);
    }

    /**
     * 批量删除安全条件检查
     * 
     * @param conditionIds 需要删除的安全条件检查主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyConditionByConditionIds(Long[] conditionIds)
    {
        return bizSafetyConditionMapper.deleteBizSafetyConditionByConditionIds(conditionIds);
    }

    /**
     * 删除安全条件检查信息
     * 
     * @param conditionId 安全条件检查主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyConditionByConditionId(Long conditionId)
    {
        return bizSafetyConditionMapper.deleteBizSafetyConditionByConditionId(conditionId);
    }
}
