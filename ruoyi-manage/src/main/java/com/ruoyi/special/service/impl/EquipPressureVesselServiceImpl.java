package com.ruoyi.special.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.special.mapper.EquipPressureVesselMapper;
import com.ruoyi.special.domain.EquipPressureVessel;
import com.ruoyi.special.service.IEquipPressureVesselService;

/**
 * 压力容器Service业务层处理
 * 
 * @author Laogao
 * @date 2025-11-04
 */
@Service
public class EquipPressureVesselServiceImpl implements IEquipPressureVesselService 
{
    @Autowired
    private EquipPressureVesselMapper equipPressureVesselMapper;

    /**
     * 查询压力容器
     * 
     * @param id 压力容器主键
     * @return 压力容器
     */
    @Override
    public EquipPressureVessel selectEquipPressureVesselById(Long id)
    {
        return equipPressureVesselMapper.selectEquipPressureVesselById(id);
    }

    /**
     * 查询压力容器列表
     * 
     * @param equipPressureVessel 压力容器
     * @return 压力容器
     */
    @Override
    public List<EquipPressureVessel> selectEquipPressureVesselList(EquipPressureVessel equipPressureVessel)
    {
        return equipPressureVesselMapper.selectEquipPressureVesselList(equipPressureVessel);
    }

    /**
     * 新增压力容器
     * 
     * @param equipPressureVessel 压力容器
     * @return 结果
     */
    @Override
    public int insertEquipPressureVessel(EquipPressureVessel equipPressureVessel)
    {
        return equipPressureVesselMapper.insertEquipPressureVessel(equipPressureVessel);
    }

    /**
     * 修改压力容器
     * 
     * @param equipPressureVessel 压力容器
     * @return 结果
     */
    @Override
    public int updateEquipPressureVessel(EquipPressureVessel equipPressureVessel)
    {
        return equipPressureVesselMapper.updateEquipPressureVessel(equipPressureVessel);
    }

    /**
     * 批量删除压力容器
     * 
     * @param ids 需要删除的压力容器主键
     * @return 结果
     */
    @Override
    public int deleteEquipPressureVesselByIds(Long[] ids)
    {
        return equipPressureVesselMapper.deleteEquipPressureVesselByIds(ids);
    }

    /**
     * 删除压力容器信息
     * 
     * @param id 压力容器主键
     * @return 结果
     */
    @Override
    public int deleteEquipPressureVesselById(Long id)
    {
        return equipPressureVesselMapper.deleteEquipPressureVesselById(id);
    }
}
