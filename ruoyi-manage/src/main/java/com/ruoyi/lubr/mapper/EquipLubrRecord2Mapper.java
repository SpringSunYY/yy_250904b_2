package com.ruoyi.lubr.mapper;

import java.util.List;
import com.ruoyi.lubr.domain.EquipLubrRecord2;

/**
 * 设备润滑记录Mapper接口
 * 
 * @author laogao
 * @date 2025-09-30
 */
public interface EquipLubrRecord2Mapper 
{
    /**
     * 查询设备润滑记录
     * 
     * @param recordId 设备润滑记录主键
     * @return 设备润滑记录
     */
    public EquipLubrRecord2 selectEquipLubrRecord2ByRecordId(Long recordId);

    /**
     * 查询设备润滑记录列表
     * 
     * @param equipLubrRecord2 设备润滑记录
     * @return 设备润滑记录集合
     */
    public List<EquipLubrRecord2> selectEquipLubrRecord2List(EquipLubrRecord2 equipLubrRecord2);

    /**
     * 新增设备润滑记录
     * 
     * @param equipLubrRecord2 设备润滑记录
     * @return 结果
     */
    public int insertEquipLubrRecord2(EquipLubrRecord2 equipLubrRecord2);

    /**
     * 修改设备润滑记录
     * 
     * @param equipLubrRecord2 设备润滑记录
     * @return 结果
     */
    public int updateEquipLubrRecord2(EquipLubrRecord2 equipLubrRecord2);

    /**
     * 删除设备润滑记录
     * 
     * @param recordId 设备润滑记录主键
     * @return 结果
     */
    public int deleteEquipLubrRecord2ByRecordId(Long recordId);

    /**
     * 批量删除设备润滑记录
     * 
     * @param recordIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipLubrRecord2ByRecordIds(Long[] recordIds);
}
