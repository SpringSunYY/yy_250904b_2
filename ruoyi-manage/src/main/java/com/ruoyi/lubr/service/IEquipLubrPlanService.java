package com.ruoyi.lubr.service;

import java.util.List;
import com.ruoyi.lubr.domain.EquipLubrPlan;

/**
 * 润滑计划Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipLubrPlanService 
{
    /**
     * 查询润滑计划
     * 
     * @param planId 润滑计划主键
     * @return 润滑计划
     */
    public EquipLubrPlan selectEquipLubrPlanByPlanId(Long planId);

    /**
     * 查询润滑计划列表
     * 
     * @param equipLubrPlan 润滑计划
     * @return 润滑计划集合
     */
    public List<EquipLubrPlan> selectEquipLubrPlanList(EquipLubrPlan equipLubrPlan);

    /**
     * 新增润滑计划
     * 
     * @param equipLubrPlan 润滑计划
     * @return 结果
     */
    public int insertEquipLubrPlan(EquipLubrPlan equipLubrPlan);

    /**
     * 修改润滑计划
     * 
     * @param equipLubrPlan 润滑计划
     * @return 结果
     */
    public int updateEquipLubrPlan(EquipLubrPlan equipLubrPlan);

    /**
     * 批量删除润滑计划
     * 
     * @param planIds 需要删除的润滑计划主键集合
     * @return 结果
     */
    public int deleteEquipLubrPlanByPlanIds(Long[] planIds);

    /**
     * 删除润滑计划信息
     * 
     * @param planId 润滑计划主键
     * @return 结果
     */
    public int deleteEquipLubrPlanByPlanId(Long planId);
}
