package com.ruoyi.special.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.special.mapper.EquipVehicleInventoryMapper;
import com.ruoyi.special.domain.EquipVehicleInventory;
import com.ruoyi.special.service.IEquipVehicleInventoryService;

/**
 * 场(厂)内专用机动车辆Service业务层处理
 * 
 * @author Laogao
 * @date 2025-11-04
 */
@Service
public class EquipVehicleInventoryServiceImpl implements IEquipVehicleInventoryService 
{
    @Autowired
    private EquipVehicleInventoryMapper equipVehicleInventoryMapper;

    /**
     * 查询场(厂)内专用机动车辆
     * 
     * @param id 场(厂)内专用机动车辆主键
     * @return 场(厂)内专用机动车辆
     */
    @Override
    public EquipVehicleInventory selectEquipVehicleInventoryById(Long id)
    {
        return equipVehicleInventoryMapper.selectEquipVehicleInventoryById(id);
    }

    /**
     * 查询场(厂)内专用机动车辆列表
     * 
     * @param equipVehicleInventory 场(厂)内专用机动车辆
     * @return 场(厂)内专用机动车辆
     */
    @Override
    public List<EquipVehicleInventory> selectEquipVehicleInventoryList(EquipVehicleInventory equipVehicleInventory)
    {
        return equipVehicleInventoryMapper.selectEquipVehicleInventoryList(equipVehicleInventory);
    }

    /**
     * 新增场(厂)内专用机动车辆
     * 
     * @param equipVehicleInventory 场(厂)内专用机动车辆
     * @return 结果
     */
    @Override
    public int insertEquipVehicleInventory(EquipVehicleInventory equipVehicleInventory)
    {
        if (equipVehicleInventory.getCreatedBy() == null || equipVehicleInventory.getCreatedBy().isEmpty()) {
            equipVehicleInventory.setCreatedBy(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return equipVehicleInventoryMapper.insertEquipVehicleInventory(equipVehicleInventory);
    }

    /**
     * 修改场(厂)内专用机动车辆
     * 
     * @param equipVehicleInventory 场(厂)内专用机动车辆
     * @return 结果
     */
    @Override
    public int updateEquipVehicleInventory(EquipVehicleInventory equipVehicleInventory)
    {
        if (equipVehicleInventory.getUpdatedBy() == null || equipVehicleInventory.getUpdatedBy().isEmpty()) {
            equipVehicleInventory.setUpdatedBy(org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getName());
        }
        return equipVehicleInventoryMapper.updateEquipVehicleInventory(equipVehicleInventory);
    }

    /**
     * 批量删除场(厂)内专用机动车辆
     * 
     * @param ids 需要删除的场(厂)内专用机动车辆主键
     * @return 结果
     */
    @Override
    public int deleteEquipVehicleInventoryByIds(Long[] ids)
    {
        return equipVehicleInventoryMapper.deleteEquipVehicleInventoryByIds(ids);
    }

    /**
     * 删除场(厂)内专用机动车辆信息
     * 
     * @param id 场(厂)内专用机动车辆主键
     * @return 结果
     */
    @Override
    public int deleteEquipVehicleInventoryById(Long id)
    {
        return equipVehicleInventoryMapper.deleteEquipVehicleInventoryById(id);
    }
}
