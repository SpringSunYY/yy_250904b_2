package com.ruoyi.equip.mapper;

import java.util.List;
import com.ruoyi.equip.domain.EquipRuntimeStat;
import com.ruoyi.equip.domain.EquipRuntimeDatail;

/**
 * 设备运行管理Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface EquipRuntimeStatMapper 
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
     * 查询设备最近的开机记录
     * 
     * @param deviceId 设备ID
     * @return 设备运行管理
     */
    public EquipRuntimeStat selectLatestStartRecordByDeviceId(Long deviceId);

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
     * 删除设备运行管理
     * 
     * @param statId 设备运行管理主键
     * @return 结果
     */
    public int deleteEquipRuntimeStatByStatId(Long statId);

    /**
     * 批量删除设备运行管理
     * 
     * @param statIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipRuntimeStatByStatIds(Long[] statIds);

    /**
     * 批量删除设备运行明细
     * 
     * @param statIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipRuntimeDatailByStatIds(Long[] statIds);
    
    /**
     * 批量新增设备运行明细
     * 
     * @param equipRuntimeDatailList 设备运行明细列表
     * @return 结果
     */
    public int batchEquipRuntimeDatail(List<EquipRuntimeDatail> equipRuntimeDatailList);
    

    /**
     * 通过设备运行管理主键删除设备运行明细信息
     * 
     * @param statId 设备运行管理ID
     * @return 结果
     */
    public int deleteEquipRuntimeDatailByStatId(Long statId);
}
