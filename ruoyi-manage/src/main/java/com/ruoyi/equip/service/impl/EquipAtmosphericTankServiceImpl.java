package com.ruoyi.equip.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.equip.mapper.EquipAtmosphericTankMapper;
import com.ruoyi.equip.domain.EquipAtmosphericTank;
import com.ruoyi.equip.service.IEquipAtmosphericTankService;

/**
 * 常压储罐检查Service业务层处理
 * 
 * @author Laogao
 * @date 2025-11-05
 */
@Service
public class EquipAtmosphericTankServiceImpl implements IEquipAtmosphericTankService 
{
    @Autowired
    private EquipAtmosphericTankMapper equipAtmosphericTankMapper;

    /**
     * 查询常压储罐检查
     * 
     * @param id 常压储罐检查主键
     * @return 常压储罐检查
     */
    @Override
    public EquipAtmosphericTank selectEquipAtmosphericTankById(Long id)
    {
        return equipAtmosphericTankMapper.selectEquipAtmosphericTankById(id);
    }

    /**
     * 查询常压储罐检查列表
     * 
     * @param equipAtmosphericTank 常压储罐检查
     * @return 常压储罐检查
     */
    @Override
    public List<EquipAtmosphericTank> selectEquipAtmosphericTankList(EquipAtmosphericTank equipAtmosphericTank)
    {
        return equipAtmosphericTankMapper.selectEquipAtmosphericTankList(equipAtmosphericTank);
    }

    /**
     * 新增常压储罐检查
     * 
     * @param equipAtmosphericTank 常压储罐检查
     * @return 结果
     */
    @Override
    public int insertEquipAtmosphericTank(EquipAtmosphericTank equipAtmosphericTank)
    {
        return equipAtmosphericTankMapper.insertEquipAtmosphericTank(equipAtmosphericTank);
    }

    /**
     * 修改常压储罐检查
     * 
     * @param equipAtmosphericTank 常压储罐检查
     * @return 结果
     */
    @Override
    public int updateEquipAtmosphericTank(EquipAtmosphericTank equipAtmosphericTank)
    {
        return equipAtmosphericTankMapper.updateEquipAtmosphericTank(equipAtmosphericTank);
    }

    /**
     * 批量删除常压储罐检查
     * 
     * @param ids 需要删除的常压储罐检查主键
     * @return 结果
     */
    @Override
    public int deleteEquipAtmosphericTankByIds(Long[] ids)
    {
        return equipAtmosphericTankMapper.deleteEquipAtmosphericTankByIds(ids);
    }

    /**
     * 删除常压储罐检查信息
     * 
     * @param id 常压储罐检查主键
     * @return 结果
     */
    @Override
    public int deleteEquipAtmosphericTankById(Long id)
    {
        return equipAtmosphericTankMapper.deleteEquipAtmosphericTankById(id);
    }
}
