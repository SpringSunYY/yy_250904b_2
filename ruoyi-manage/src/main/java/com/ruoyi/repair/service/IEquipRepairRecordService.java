package com.ruoyi.repair.service;

import java.util.List;
import com.ruoyi.repair.domain.EquipRepairRecord;

/**
 * 检维修记录Service接口
 * 
 * @author laogao
 * @date 2025-09-30
 */
public interface IEquipRepairRecordService 
{
    /**
     * 查询检维修记录
     * 
     * @param recordId 检维修记录主键
     * @return 检维修记录
     */
    public EquipRepairRecord selectEquipRepairRecordByRecordId(Long recordId);

    /**
     * 查询检维修记录列表
     * 
     * @param equipRepairRecord 检维修记录
     * @return 检维修记录集合
     */
    public List<EquipRepairRecord> selectEquipRepairRecordList(EquipRepairRecord equipRepairRecord);

    /**
     * 新增检维修记录
     * 
     * @param equipRepairRecord 检维修记录
     * @return 结果
     */
    public int insertEquipRepairRecord(EquipRepairRecord equipRepairRecord);

    /**
     * 修改检维修记录
     * 
     * @param equipRepairRecord 检维修记录
     * @return 结果
     */
    public int updateEquipRepairRecord(EquipRepairRecord equipRepairRecord);

    /**
     * 批量删除检维修记录
     * 
     * @param recordIds 需要删除的检维修记录主键集合
     * @return 结果
     */
    public int deleteEquipRepairRecordByRecordIds(Long[] recordIds);

    /**
     * 删除检维修记录信息
     * 
     * @param recordId 检维修记录主键
     * @return 结果
     */
    public int deleteEquipRepairRecordByRecordId(Long recordId);
}
