package com.ruoyi.vulnerable.mapper;

import java.util.List;
import com.ruoyi.vulnerable.domain.EquipVulnerableInspection;

/**
 * 易损设备检查Mapper接口
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public interface EquipVulnerableInspectionMapper 
{
    /**
     * 查询易损设备检查
     * 
     * @param id 易损设备检查主键
     * @return 易损设备检查
     */
    public EquipVulnerableInspection selectEquipVulnerableInspectionById(Long id);

    /**
     * 查询易损设备检查列表
     * 
     * @param equipVulnerableInspection 易损设备检查
     * @return 易损设备检查集合
     */
    public List<EquipVulnerableInspection> selectEquipVulnerableInspectionList(EquipVulnerableInspection equipVulnerableInspection);

    /**
     * 新增易损设备检查
     * 
     * @param equipVulnerableInspection 易损设备检查
     * @return 结果
     */
    public int insertEquipVulnerableInspection(EquipVulnerableInspection equipVulnerableInspection);

    /**
     * 修改易损设备检查
     * 
     * @param equipVulnerableInspection 易损设备检查
     * @return 结果
     */
    public int updateEquipVulnerableInspection(EquipVulnerableInspection equipVulnerableInspection);

    /**
     * 删除易损设备检查
     * 
     * @param id 易损设备检查主键
     * @return 结果
     */
    public int deleteEquipVulnerableInspectionById(Long id);

    /**
     * 批量删除易损设备检查
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipVulnerableInspectionByIds(Long[] ids);
}
