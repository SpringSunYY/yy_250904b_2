package com.ruoyi.equip.mapper;

import java.util.List;
import com.ruoyi.equip.domain.EquipDefect;

/**
 * 设备缺陷管理Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface EquipDefectMapper 
{
    /**
     * 查询设备缺陷管理
     * 
     * @param defectId 设备缺陷管理主键
     * @return 设备缺陷管理
     */
    public EquipDefect selectEquipDefectByDefectId(Long defectId);

    /**
     * 查询设备缺陷管理列表
     * 
     * @param equipDefect 设备缺陷管理
     * @return 设备缺陷管理集合
     */
    public List<EquipDefect> selectEquipDefectList(EquipDefect equipDefect);

    /**
     * 新增设备缺陷管理
     * 
     * @param equipDefect 设备缺陷管理
     * @return 结果
     */
    public int insertEquipDefect(EquipDefect equipDefect);

    /**
     * 修改设备缺陷管理
     * 
     * @param equipDefect 设备缺陷管理
     * @return 结果
     */
    public int updateEquipDefect(EquipDefect equipDefect);

    /**
     * 删除设备缺陷管理
     * 
     * @param defectId 设备缺陷管理主键
     * @return 结果
     */
    public int deleteEquipDefectByDefectId(Long defectId);

    /**
     * 批量删除设备缺陷管理
     * 
     * @param defectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipDefectByDefectIds(Long[] defectIds);
}
