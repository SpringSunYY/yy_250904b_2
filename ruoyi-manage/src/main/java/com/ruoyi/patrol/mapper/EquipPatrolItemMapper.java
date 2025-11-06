package com.ruoyi.patrol.mapper;

import java.util.List;
import com.ruoyi.patrol.domain.EquipPatrolItem;

/**
 * 设备巡检项Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface EquipPatrolItemMapper 
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
     * 删除设备巡检项
     * 
     * @param itemId 设备巡检项主键
     * @return 结果
     */
    public int deleteEquipPatrolItemByItemId(Long itemId);

    /**
     * 批量删除设备巡检项
     * 
     * @param itemIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipPatrolItemByItemIds(Long[] itemIds);
}
