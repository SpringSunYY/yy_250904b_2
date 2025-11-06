package com.ruoyi.patrol.service;

import java.util.List;
import com.ruoyi.patrol.domain.EquipPatrolItem;

/**
 * 设备巡检项Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipPatrolItemService 
{
    /**
     * 查询设备巡检项
     * 
     * @param itemId 设备巡检项主键
     * @return 设备巡检项
     */
    public EquipPatrolItem selectEquipPatrolItemByItemId(Long itemId);

    /**
     * 查询设备巡检项列表
     * 
     * @param equipPatrolItem 设备巡检项
     * @return 设备巡检项集合
     */
    public List<EquipPatrolItem> selectEquipPatrolItemList(EquipPatrolItem equipPatrolItem);

    /**
     * 新增设备巡检项
     * 
     * @param equipPatrolItem 设备巡检项
     * @return 结果
     */
    public int insertEquipPatrolItem(EquipPatrolItem equipPatrolItem);

    /**
     * 修改设备巡检项
     * 
     * @param equipPatrolItem 设备巡检项
     * @return 结果
     */
    public int updateEquipPatrolItem(EquipPatrolItem equipPatrolItem);

    /**
     * 批量删除设备巡检项
     * 
     * @param itemIds 需要删除的设备巡检项主键集合
     * @return 结果
     */
    public int deleteEquipPatrolItemByItemIds(Long[] itemIds);

    /**
     * 删除设备巡检项信息
     * 
     * @param itemId 设备巡检项主键
     * @return 结果
     */
    public int deleteEquipPatrolItemByItemId(Long itemId);
}
