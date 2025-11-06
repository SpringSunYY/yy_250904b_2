package com.ruoyi.repair.service;

import java.util.List;
import com.ruoyi.repair.domain.EquipRepairPlan;

/**
 * 设备检修计划Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipRepairPlanService 
{
    /**
     * 查询设备检修计划
     * 
     * @param planId 设备检修计划主键
     * @return 设备检修计划
     */
    public EquipRepairPlan selectEquipRepairPlanByPlanId(Long planId);

    /**
     * 查询设备检修计划列表
     * 
     * @param equipRepairPlan 设备检修计划
     * @return 设备检修计划集合
     */
    public List<EquipRepairPlan> selectEquipRepairPlanList(EquipRepairPlan equipRepairPlan);

    /**
     * 新增设备检修计划
     * 
     * @param equipRepairPlan 设备检修计划
     * @return 结果
     */
    public int insertEquipRepairPlan(EquipRepairPlan equipRepairPlan);

    /**
     * 修改设备检修计划
     * 
     * @param equipRepairPlan 设备检修计划
     * @return 结果
     */
    public int updateEquipRepairPlan(EquipRepairPlan equipRepairPlan);

    /**
     * 批量删除设备检修计划
     * 
     * @param planIds 需要删除的设备检修计划主键集合
     * @return 结果
     */
    public int deleteEquipRepairPlanByPlanIds(Long[] planIds);

    /**
     * 删除设备检修计划信息
     * 
     * @param planId 设备检修计划主键
     * @return 结果
     */
    public int deleteEquipRepairPlanByPlanId(Long planId);
}
