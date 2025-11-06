package com.ruoyi.equip.service;

import java.util.List;
import com.ruoyi.equip.domain.EquipScrapLedger;

/**
 * 设备报废管理Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipScrapLedgerService 
{
    /**
     * 查询设备报废管理
     * 
     * @param scrapId 设备报废管理主键
     * @return 设备报废管理
     */
    public EquipScrapLedger selectEquipScrapLedgerByScrapId(Long scrapId);

    /**
     * 查询设备报废管理列表
     * 
     * @param equipScrapLedger 设备报废管理
     * @return 设备报废管理集合
     */
    public List<EquipScrapLedger> selectEquipScrapLedgerList(EquipScrapLedger equipScrapLedger);

    /**
     * 新增设备报废管理
     * 
     * @param equipScrapLedger 设备报废管理
     * @return 结果
     */
    public int insertEquipScrapLedger(EquipScrapLedger equipScrapLedger);

    /**
     * 修改设备报废管理
     * 
     * @param equipScrapLedger 设备报废管理
     * @return 结果
     */
    public int updateEquipScrapLedger(EquipScrapLedger equipScrapLedger);

    /**
     * 批量删除设备报废管理
     * 
     * @param scrapIds 需要删除的设备报废管理主键集合
     * @return 结果
     */
    public int deleteEquipScrapLedgerByScrapIds(Long[] scrapIds);

    /**
     * 删除设备报废管理信息
     * 
     * @param scrapId 设备报废管理主键
     * @return 结果
     */
    public int deleteEquipScrapLedgerByScrapId(Long scrapId);
}
