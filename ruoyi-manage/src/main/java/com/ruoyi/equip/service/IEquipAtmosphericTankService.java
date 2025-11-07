package com.ruoyi.equip.service;

import java.util.List;
import com.ruoyi.equip.domain.EquipAtmosphericTank;

/**
 * 常压储罐检查Service接口
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public interface IEquipAtmosphericTankService 
{
    /**
     * 查询常压储罐检查
     * 
     * @param id 常压储罐检查主键
     * @return 常压储罐检查
     */
    public EquipAtmosphericTank selectEquipAtmosphericTankById(Long id);

    /**
     * 查询常压储罐检查列表
     * 
     * @param equipAtmosphericTank 常压储罐检查
     * @return 常压储罐检查集合
     */
    public List<EquipAtmosphericTank> selectEquipAtmosphericTankList(EquipAtmosphericTank equipAtmosphericTank);

    /**
     * 新增常压储罐检查
     * 
     * @param equipAtmosphericTank 常压储罐检查
     * @return 结果
     */
    public int insertEquipAtmosphericTank(EquipAtmosphericTank equipAtmosphericTank);

    /**
     * 修改常压储罐检查
     * 
     * @param equipAtmosphericTank 常压储罐检查
     * @return 结果
     */
    public int updateEquipAtmosphericTank(EquipAtmosphericTank equipAtmosphericTank);

    /**
     * 批量删除常压储罐检查
     * 
     * @param ids 需要删除的常压储罐检查主键集合
     * @return 结果
     */
    public int deleteEquipAtmosphericTankByIds(Long[] ids);

    /**
     * 删除常压储罐检查信息
     * 
     * @param id 常压储罐检查主键
     * @return 结果
     */
    public int deleteEquipAtmosphericTankById(Long id);
}
