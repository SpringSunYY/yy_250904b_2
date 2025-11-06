package com.ruoyi.anticorrosion.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.anticorrosion.mapper.EquipAnticorrosionBaseMapper;
import com.ruoyi.anticorrosion.domain.EquipAnticorrosionBase;
import com.ruoyi.anticorrosion.service.IEquipAnticorrosionBaseService;

/**
 * 防腐基础信息Service业务层处理
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipAnticorrosionBaseServiceImpl implements IEquipAnticorrosionBaseService 
{
    @Autowired
    private EquipAnticorrosionBaseMapper equipAnticorrosionBaseMapper;

    /**
     * 查询防腐基础信息
     * 
     * @param id 防腐基础信息主键
     * @return 防腐基础信息
     */
    @Override
    public EquipAnticorrosionBase selectEquipAnticorrosionBaseById(Long id)
    {
        return equipAnticorrosionBaseMapper.selectEquipAnticorrosionBaseById(id);
    }

    /**
     * 查询防腐基础信息列表
     * 
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 防腐基础信息
     */
    @Override
    public List<EquipAnticorrosionBase> selectEquipAnticorrosionBaseList(EquipAnticorrosionBase equipAnticorrosionBase)
    {
        return equipAnticorrosionBaseMapper.selectEquipAnticorrosionBaseList(equipAnticorrosionBase);
    }

    /**
     * 新增防腐基础信息
     * 
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 结果
     */
    @Override
    public int insertEquipAnticorrosionBase(EquipAnticorrosionBase equipAnticorrosionBase)
    {
        equipAnticorrosionBase.setCreateTime(DateUtils.getNowDate());
        return equipAnticorrosionBaseMapper.insertEquipAnticorrosionBase(equipAnticorrosionBase);
    }

    /**
     * 修改防腐基础信息
     * 
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 结果
     */
    @Override
    public int updateEquipAnticorrosionBase(EquipAnticorrosionBase equipAnticorrosionBase)
    {
        equipAnticorrosionBase.setUpdateTime(DateUtils.getNowDate());
        return equipAnticorrosionBaseMapper.updateEquipAnticorrosionBase(equipAnticorrosionBase);
    }

    /**
     * 批量删除防腐基础信息
     * 
     * @param ids 需要删除的防腐基础信息主键
     * @return 结果
     */
    @Override
    public int deleteEquipAnticorrosionBaseByIds(Long[] ids)
    {
        return equipAnticorrosionBaseMapper.deleteEquipAnticorrosionBaseByIds(ids);
    }

    /**
     * 删除防腐基础信息信息
     * 
     * @param id 防腐基础信息主键
     * @return 结果
     */
    @Override
    public int deleteEquipAnticorrosionBaseById(Long id)
    {
        return equipAnticorrosionBaseMapper.deleteEquipAnticorrosionBaseById(id);
    }
}
