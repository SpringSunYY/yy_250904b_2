package com.ruoyi.repair.mapper;

import java.util.List;
import com.ruoyi.repair.domain.EquipRepairRecord;
import com.ruoyi.repair.domain.EquipRepairDetail;

/**
 * 检维修记录Mapper接口
 * 
 * @author laogao
 * @date 2025-09-30
 */
public interface EquipRepairRecordMapper 
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
     * 删除检维修记录
     * 
     * @param recordId 检维修记录主键
     * @return 结果
     */
    public int deleteEquipRepairRecordByRecordId(Long recordId);

    /**
     * 批量删除检维修记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipRepairRecordByRecordIds(Long[] recordIds);

    /**
     * 批量删除设备维修明细
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipRepairDetailByRecordIds(Long[] recordIds);
    
    /**
     * 批量新增设备维修明细
     * 
     * @param equipRepairDetailList 设备维修明细列表
     * @return 结果
     */
    public int batchEquipRepairDetail(List<EquipRepairDetail> equipRepairDetailList);
    

    /**
     * 通过检维修记录主键删除设备维修明细信息
     * 
     * @param recordId 检维修记录ID
     * @return 结果
     */
    public int deleteEquipRepairDetailByRecordId(Long recordId);
}
