package com.ruoyi.equip.service;

import java.util.List;
import com.ruoyi.equip.domain.EquipRuntimeStat;

/**
 * 设备运行管理Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipRuntimeStatService 
{
    /**
     * 查询设备运行管理
     * 
     * @param statId 设备运行管理主键
     * @return 设备运行管理
     */
    public EquipRuntimeStat selectEquipRuntimeStatByStatId(Long statId);

    /**
     * 查询设备运行管理列表
     * 
     * @param equipRuntimeStat 设备运行管理
     * @return 设备运行管理集合
     */
    public List<EquipRuntimeStat> selectEquipRuntimeStatList(EquipRuntimeStat equipRuntimeStat);

    /**
     * 新增设备运行管理
     * 
     * @param equipRuntimeStat 设备运行管理
     * @return 结果
     */
    public int insertEquipRuntimeStat(EquipRuntimeStat equipRuntimeStat);

    /**
     * 修改设备运行管理
     * 
     * @param equipRuntimeStat 设备运行管理
     * @return 结果
     */
    public int updateEquipRuntimeStat(EquipRuntimeStat equipRuntimeStat);

    /**
     * 批量删除设备运行管理
     * 
     * @param statIds 需要删除的设备运行管理主键集合
     * @return 结果
     */
    public int deleteEquipRuntimeStatByStatIds(Long[] statIds);

    /**
     * 删除设备运行管理信息
     * 
     * @param statId 设备运行管理主键
     * @return 结果
     */
    public int deleteEquipRuntimeStatByStatId(Long statId);
}
