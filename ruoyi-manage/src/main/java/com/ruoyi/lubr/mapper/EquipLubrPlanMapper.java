package com.ruoyi.lubr.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import com.ruoyi.lubr.domain.EquipLubrPlan;

/**
 * 润滑计划 数据层
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Mapper
public interface EquipLubrPlanMapper 
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
     * 删除润滑计划
     * 
     * @param planId 润滑计划主键
     * @return 结果
     */
    public int deleteEquipLubrPlanByPlanId(Long planId);

    /**
     * 批量删除润滑计划
     * 
     * @param planIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipLubrPlanByPlanIds(Long[] planIds);
}
