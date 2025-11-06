package com.ruoyi.inspe.service;

import java.util.List;
import com.ruoyi.inspe.domain.EquipInspeDanger;

/**
 * 设备隐患治理Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipInspeDangerService 
{
    /**
     * 查询设备隐患治理
     * 
     * @param dangerId 设备隐患治理主键
     * @return 设备隐患治理
     */
    public EquipInspeDanger selectEquipInspeDangerByDangerId(Long dangerId);

    /**
     * 查询设备隐患治理列表
     * 
     * @param equipInspeDanger 设备隐患治理
     * @return 设备隐患治理集合
     */
    public List<EquipInspeDanger> selectEquipInspeDangerList(EquipInspeDanger equipInspeDanger);

    /**
     * 新增设备隐患治理
     * 
     * @param equipInspeDanger 设备隐患治理
     * @return 结果
     */
    public int insertEquipInspeDanger(EquipInspeDanger equipInspeDanger);

    /**
     * 修改设备隐患治理
     * 
     * @param equipInspeDanger 设备隐患治理
     * @return 结果
     */
    public int updateEquipInspeDanger(EquipInspeDanger equipInspeDanger);

    /**
     * 批量删除设备隐患治理
     * 
     * @param dangerIds 需要删除的设备隐患治理主键集合
     * @return 结果
     */
    public int deleteEquipInspeDangerByDangerIds(Long[] dangerIds);

    /**
     * 删除设备隐患治理信息
     * 
     * @param dangerId 设备隐患治理主键
     * @return 结果
     */
    public int deleteEquipInspeDangerByDangerId(Long dangerId);
}
