package com.ruoyi.vulnerable.service;

import java.util.List;
import com.ruoyi.vulnerable.domain.EquipmentVulnerableInfo;

/**
 * 易损设备信息Service接口
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public interface IEquipmentVulnerableInfoService 
{
    /**
     * 查询易损设备信息
     * 
     * @param id 易损设备信息主键
     * @return 易损设备信息
     */
    public EquipmentVulnerableInfo selectEquipmentVulnerableInfoById(Long id);

    /**
     * 查询易损设备信息列表
     * 
     * @param equipmentVulnerableInfo 易损设备信息
     * @return 易损设备信息集合
     */
    public List<EquipmentVulnerableInfo> selectEquipmentVulnerableInfoList(EquipmentVulnerableInfo equipmentVulnerableInfo);

    /**
     * 新增易损设备信息
     * 
     * @param equipmentVulnerableInfo 易损设备信息
     * @return 结果
     */
    public int insertEquipmentVulnerableInfo(EquipmentVulnerableInfo equipmentVulnerableInfo);

    /**
     * 修改易损设备信息
     * 
     * @param equipmentVulnerableInfo 易损设备信息
     * @return 结果
     */
    public int updateEquipmentVulnerableInfo(EquipmentVulnerableInfo equipmentVulnerableInfo);

    /**
     * 批量删除易损设备信息
     * 
     * @param ids 需要删除的易损设备信息主键集合
     * @return 结果
     */
    public int deleteEquipmentVulnerableInfoByIds(Long[] ids);

    /**
     * 删除易损设备信息信息
     * 
     * @param id 易损设备信息主键
     * @return 结果
     */
    public int deleteEquipmentVulnerableInfoById(Long id);
}
