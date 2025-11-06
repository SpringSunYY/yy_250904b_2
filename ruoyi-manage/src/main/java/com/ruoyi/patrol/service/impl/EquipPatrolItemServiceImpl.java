package com.ruoyi.patrol.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.patrol.mapper.EquipPatrolItemMapper;
import com.ruoyi.patrol.domain.EquipPatrolItem;
import com.ruoyi.patrol.service.IEquipPatrolItemService;

/**
 * 设备巡检项Service业务层处理
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipPatrolItemServiceImpl implements IEquipPatrolItemService 
{
    @Autowired
    private EquipPatrolItemMapper equipPatrolItemMapper;

    /**
     * 查询设备巡检项
     * 
     * @param itemId 设备巡检项主键
     * @return 设备巡检项
     */
    @Override
    public EquipPatrolItem selectEquipPatrolItemByItemId(Long itemId)
    {
        return equipPatrolItemMapper.selectEquipPatrolItemByItemId(itemId);
    }

    /**
     * 查询设备巡检项列表
     * 
     * @param equipPatrolItem 设备巡检项
     * @return 设备巡检项
     */
    @Override
    public List<EquipPatrolItem> selectEquipPatrolItemList(EquipPatrolItem equipPatrolItem)
    {
        return equipPatrolItemMapper.selectEquipPatrolItemList(equipPatrolItem);
    }

    /**
     * 新增设备巡检项
     * 
     * @param equipPatrolItem 设备巡检项
     * @return 结果
     */
    @Override
    public int insertEquipPatrolItem(EquipPatrolItem equipPatrolItem)
    {
        equipPatrolItem.setCreateTime(DateUtils.getNowDate());
        return equipPatrolItemMapper.insertEquipPatrolItem(equipPatrolItem);
    }

    /**
     * 修改设备巡检项
     * 
     * @param equipPatrolItem 设备巡检项
     * @return 结果
     */
    @Override
    public int updateEquipPatrolItem(EquipPatrolItem equipPatrolItem)
    {
        equipPatrolItem.setUpdateTime(DateUtils.getNowDate());
        return equipPatrolItemMapper.updateEquipPatrolItem(equipPatrolItem);
    }

    /**
     * 批量删除设备巡检项
     * 
     * @param itemIds 需要删除的设备巡检项主键
     * @return 结果
     */
    @Override
    public int deleteEquipPatrolItemByItemIds(Long[] itemIds)
    {
        return equipPatrolItemMapper.deleteEquipPatrolItemByItemIds(itemIds);
    }

    /**
     * 删除设备巡检项信息
     * 
     * @param itemId 设备巡检项主键
     * @return 结果
     */
    @Override
    public int deleteEquipPatrolItemByItemId(Long itemId)
    {
        return equipPatrolItemMapper.deleteEquipPatrolItemByItemId(itemId);
    }
}
