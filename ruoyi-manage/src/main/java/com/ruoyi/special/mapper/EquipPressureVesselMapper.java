package com.ruoyi.special.mapper;

import java.util.List;
import com.ruoyi.special.domain.EquipPressureVessel;

/**
 * 压力容器Mapper接口
 * 
 * @author Laogao
 * @date 2025-11-04
 */
public interface EquipPressureVesselMapper 
{
    /**
     * 查询压力容器
     * 
     * @param id 压力容器主键
     * @return 压力容器
     */
    public EquipPressureVessel selectEquipPressureVesselById(Long id);

    /**
     * 查询压力容器列表
     * 
     * @param equipPressureVessel 压力容器
     * @return 压力容器集合
     */
    public List<EquipPressureVessel> selectEquipPressureVesselList(EquipPressureVessel equipPressureVessel);

    /**
     * 新增压力容器
     * 
     * @param equipPressureVessel 压力容器
     * @return 结果
     */
    public int insertEquipPressureVessel(EquipPressureVessel equipPressureVessel);

    /**
     * 修改压力容器
     * 
     * @param equipPressureVessel 压力容器
     * @return 结果
     */
    public int updateEquipPressureVessel(EquipPressureVessel equipPressureVessel);

    /**
     * 删除压力容器
     * 
     * @param id 压力容器主键
     * @return 结果
     */
    public int deleteEquipPressureVesselById(Long id);

    /**
     * 批量删除压力容器
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipPressureVesselByIds(Long[] ids);
}
