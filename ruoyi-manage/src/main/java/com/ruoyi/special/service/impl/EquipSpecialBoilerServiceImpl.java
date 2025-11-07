package com.ruoyi.special.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.special.mapper.EquipSpecialBoilerMapper;
import com.ruoyi.special.domain.EquipSpecialBoiler;
import com.ruoyi.special.service.IEquipSpecialBoilerService;

/**
 * 锅炉Service业务层处理
 * 
 * @author Laogao
 * @date 2025-11-05
 */
@Service
public class EquipSpecialBoilerServiceImpl implements IEquipSpecialBoilerService 
{
    @Autowired
    private EquipSpecialBoilerMapper equipSpecialBoilerMapper;

    /**
     * 查询锅炉
     * 
     * @param boilerId 锅炉主键
     * @return 锅炉
     */
    @Override
    public EquipSpecialBoiler selectEquipSpecialBoilerByBoilerId(Long boilerId)
    {
        return equipSpecialBoilerMapper.selectEquipSpecialBoilerByBoilerId(boilerId);
    }

    /**
     * 查询锅炉列表
     * 
     * @param equipSpecialBoiler 锅炉
     * @return 锅炉
     */
    @Override
    public List<EquipSpecialBoiler> selectEquipSpecialBoilerList(EquipSpecialBoiler equipSpecialBoiler)
    {
        return equipSpecialBoilerMapper.selectEquipSpecialBoilerList(equipSpecialBoiler);
    }

    /**
     * 新增锅炉
     * 
     * @param equipSpecialBoiler 锅炉
     * @return 结果
     */
    @Override
    public int insertEquipSpecialBoiler(EquipSpecialBoiler equipSpecialBoiler)
    {
        return equipSpecialBoilerMapper.insertEquipSpecialBoiler(equipSpecialBoiler);
    }

    /**
     * 修改锅炉
     * 
     * @param equipSpecialBoiler 锅炉
     * @return 结果
     */
    @Override
    public int updateEquipSpecialBoiler(EquipSpecialBoiler equipSpecialBoiler)
    {
        return equipSpecialBoilerMapper.updateEquipSpecialBoiler(equipSpecialBoiler);
    }

    /**
     * 批量删除锅炉
     * 
     * @param boilerIds 需要删除的锅炉主键
     * @return 结果
     */
    @Override
    public int deleteEquipSpecialBoilerByBoilerIds(Long[] boilerIds)
    {
        return equipSpecialBoilerMapper.deleteEquipSpecialBoilerByBoilerIds(boilerIds);
    }

    /**
     * 删除锅炉信息
     * 
     * @param boilerId 锅炉主键
     * @return 结果
     */
    @Override
    public int deleteEquipSpecialBoilerByBoilerId(Long boilerId)
    {
        return equipSpecialBoilerMapper.deleteEquipSpecialBoilerByBoilerId(boilerId);
    }
}
