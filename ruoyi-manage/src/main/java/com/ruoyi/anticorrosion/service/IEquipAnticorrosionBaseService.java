package com.ruoyi.anticorrosion.service;

import java.util.List;
import com.ruoyi.anticorrosion.domain.EquipAnticorrosionBase;

/**
 * 防腐基础信息Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipAnticorrosionBaseService 
{
    /**
     * 查询防腐基础信息
     * 
     * @param id 防腐基础信息主键
     * @return 防腐基础信息
     */
    public EquipAnticorrosionBase selectEquipAnticorrosionBaseById(Long id);

    /**
     * 查询防腐基础信息列表
     * 
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 防腐基础信息集合
     */
    public List<EquipAnticorrosionBase> selectEquipAnticorrosionBaseList(EquipAnticorrosionBase equipAnticorrosionBase);

    /**
     * 新增防腐基础信息
     * 
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 结果
     */
    public int insertEquipAnticorrosionBase(EquipAnticorrosionBase equipAnticorrosionBase);

    /**
     * 修改防腐基础信息
     * 
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 结果
     */
    public int updateEquipAnticorrosionBase(EquipAnticorrosionBase equipAnticorrosionBase);

    /**
     * 批量删除防腐基础信息
     * 
     * @param ids 需要删除的防腐基础信息主键集合
     * @return 结果
     */
    public int deleteEquipAnticorrosionBaseByIds(Long[] ids);

    /**
     * 删除防腐基础信息信息
     * 
     * @param id 防腐基础信息主键
     * @return 结果
     */
    public int deleteEquipAnticorrosionBaseById(Long id);
}
