package com.ruoyi.special.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.special.mapper.EquipSpecialInspectionMapper;
import com.ruoyi.special.domain.EquipSpecialInspection;
import com.ruoyi.special.service.IEquipSpecialInspectionService;

/**
 * 检验检测Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
@Service
public class EquipSpecialInspectionServiceImpl implements IEquipSpecialInspectionService 
{
    @Autowired
    private EquipSpecialInspectionMapper equipSpecialInspectionMapper;

    /**
     * 查询检验检测
     * 
     * @param id 检验检测主键
     * @return 检验检测
     */
    @Override
    public EquipSpecialInspection selectEquipSpecialInspectionById(Long id)
    {
        return equipSpecialInspectionMapper.selectEquipSpecialInspectionById(id);
    }

    /**
     * 查询检验检测列表
     * 
     * @param equipSpecialInspection 检验检测
     * @return 检验检测
     */
    @Override
    public List<EquipSpecialInspection> selectEquipSpecialInspectionList(EquipSpecialInspection equipSpecialInspection)
    {
        return equipSpecialInspectionMapper.selectEquipSpecialInspectionList(equipSpecialInspection);
    }

    /**
     * 新增检验检测
     * 
     * @param equipSpecialInspection 检验检测
     * @return 结果
     */
    @Override
    public int insertEquipSpecialInspection(EquipSpecialInspection equipSpecialInspection)
    {
        return equipSpecialInspectionMapper.insertEquipSpecialInspection(equipSpecialInspection);
    }

    /**
     * 修改检验检测
     * 
     * @param equipSpecialInspection 检验检测
     * @return 结果
     */
    @Override
    public int updateEquipSpecialInspection(EquipSpecialInspection equipSpecialInspection)
    {
        return equipSpecialInspectionMapper.updateEquipSpecialInspection(equipSpecialInspection);
    }

    /**
     * 批量删除检验检测
     * 
     * @param ids 需要删除的检验检测主键
     * @return 结果
     */
    @Override
    public int deleteEquipSpecialInspectionByIds(Long[] ids)
    {
        return equipSpecialInspectionMapper.deleteEquipSpecialInspectionByIds(ids);
    }

    /**
     * 删除检验检测信息
     * 
     * @param id 检验检测主键
     * @return 结果
     */
    @Override
    public int deleteEquipSpecialInspectionById(Long id)
    {
        return equipSpecialInspectionMapper.deleteEquipSpecialInspectionById(id);
    }
}
