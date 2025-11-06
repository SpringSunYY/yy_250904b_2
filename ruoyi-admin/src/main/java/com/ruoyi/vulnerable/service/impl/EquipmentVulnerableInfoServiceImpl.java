package com.ruoyi.vulnerable.service.impl;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.vulnerable.mapper.EquipmentVulnerableInfoMapper;
import com.ruoyi.vulnerable.domain.EquipmentVulnerableInfo;
import com.ruoyi.vulnerable.service.IEquipmentVulnerableInfoService;

/**
 * 易损设备信息Service业务层处理
 * 
 * @author Laogao
 * @date 2025-11-05
 */
@Service
public class EquipmentVulnerableInfoServiceImpl implements IEquipmentVulnerableInfoService 
{
    @Autowired
    private EquipmentVulnerableInfoMapper equipmentVulnerableInfoMapper;

    /**
     * 查询易损设备信息
     * 
     * @param id 易损设备信息主键
     * @return 易损设备信息
     */
    @Override
    public EquipmentVulnerableInfo selectEquipmentVulnerableInfoById(Long id)
    {
        return equipmentVulnerableInfoMapper.selectEquipmentVulnerableInfoById(id);
    }

    /**
     * 查询易损设备信息列表
     * 
     * @param equipmentVulnerableInfo 易损设备信息
     * @return 易损设备信息
     */
    @Override
    public List<EquipmentVulnerableInfo> selectEquipmentVulnerableInfoList(EquipmentVulnerableInfo equipmentVulnerableInfo)
    {
        return equipmentVulnerableInfoMapper.selectEquipmentVulnerableInfoList(equipmentVulnerableInfo);
    }

    /**
     * 新增易损设备信息
     * 
     * @param equipmentVulnerableInfo 易损设备信息
     * @return 结果
     */
    @Override
    public int insertEquipmentVulnerableInfo(EquipmentVulnerableInfo equipmentVulnerableInfo)
    {
        // 自动生成易损编号
        if (equipmentVulnerableInfo.getVulnerableNumber() == null || equipmentVulnerableInfo.getVulnerableNumber().isEmpty()) {
            String vulnerableNumber = "VUL-" + System.currentTimeMillis();
            equipmentVulnerableInfo.setVulnerableNumber(vulnerableNumber);
        }
        return equipmentVulnerableInfoMapper.insertEquipmentVulnerableInfo(equipmentVulnerableInfo);
    }

    /**
     * 修改易损设备信息
     * 
     * @param equipmentVulnerableInfo 易损设备信息
     * @return 结果
     */
    @Override
    public int updateEquipmentVulnerableInfo(EquipmentVulnerableInfo equipmentVulnerableInfo)
    {
        return equipmentVulnerableInfoMapper.updateEquipmentVulnerableInfo(equipmentVulnerableInfo);
    }

    /**
     * 批量删除易损设备信息
     * 
     * @param ids 需要删除的易损设备信息主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentVulnerableInfoByIds(Long[] ids)
    {
        return equipmentVulnerableInfoMapper.deleteEquipmentVulnerableInfoByIds(ids);
    }

    /**
     * 删除易损设备信息信息
     * 
     * @param id 易损设备信息主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentVulnerableInfoById(Long id)
    {
        return equipmentVulnerableInfoMapper.deleteEquipmentVulnerableInfoById(id);
    }
}
