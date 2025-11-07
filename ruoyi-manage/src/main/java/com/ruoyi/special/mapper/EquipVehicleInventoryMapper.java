package com.ruoyi.special.mapper;

import java.util.List;
import com.ruoyi.special.domain.EquipVehicleInventory;

/**
 * 场(厂)内专用机动车辆Mapper接口
 * 
 * @author Laogao
 * @date 2025-11-04
 */
public interface EquipVehicleInventoryMapper 
{
    /**
     * 查询场(厂)内专用机动车辆
     * 
     * @param id 场(厂)内专用机动车辆主键
     * @return 场(厂)内专用机动车辆
     */
    public EquipVehicleInventory selectEquipVehicleInventoryById(Long id);

    /**
     * 查询场(厂)内专用机动车辆列表
     * 
     * @param equipVehicleInventory 场(厂)内专用机动车辆
     * @return 场(厂)内专用机动车辆集合
     */
    public List<EquipVehicleInventory> selectEquipVehicleInventoryList(EquipVehicleInventory equipVehicleInventory);

    /**
     * 新增场(厂)内专用机动车辆
     * 
     * @param equipVehicleInventory 场(厂)内专用机动车辆
     * @return 结果
     */
    public int insertEquipVehicleInventory(EquipVehicleInventory equipVehicleInventory);

    /**
     * 修改场(厂)内专用机动车辆
     * 
     * @param equipVehicleInventory 场(厂)内专用机动车辆
     * @return 结果
     */
    public int updateEquipVehicleInventory(EquipVehicleInventory equipVehicleInventory);

    /**
     * 删除场(厂)内专用机动车辆
     * 
     * @param id 场(厂)内专用机动车辆主键
     * @return 结果
     */
    public int deleteEquipVehicleInventoryById(Long id);

    /**
     * 批量删除场(厂)内专用机动车辆
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipVehicleInventoryByIds(Long[] ids);
}
