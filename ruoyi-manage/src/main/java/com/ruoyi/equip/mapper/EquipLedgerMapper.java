package com.ruoyi.equip.mapper;

import java.util.List;
import com.ruoyi.equip.domain.EquipLedger;

/**
 * 设备台账Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface EquipLedgerMapper 
{
    /**
     * 查询设备台账
     * 
     * @param equipId 设备台账主键
     * @return 设备台账
     */
    public EquipLedger selectEquipLedgerByEquipId(Long equipId);

    /**
     * 查询设备台账列表
     * 
     * @param equipLedger 设备台账
     * @return 设备台账集合
     */
    public List<EquipLedger> selectEquipLedgerList(EquipLedger equipLedger);

    /**
     * 新增设备台账
     * 
     * @param equipLedger 设备台账
     * @return 结果
     */
    public int insertEquipLedger(EquipLedger equipLedger);

    /**
     * 修改设备台账
     * 
     * @param equipLedger 设备台账
     * @return 结果
     */
    public int updateEquipLedger(EquipLedger equipLedger);

    /**
     * 删除设备台账
     * 
     * @param equipId 设备台账主键
     * @return 结果
     */
    public int deleteEquipLedgerByEquipId(Long equipId);

    /**
     * 批量删除设备台账
     * 
     * @param equipIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipLedgerByEquipIds(Long[] equipIds);
}
