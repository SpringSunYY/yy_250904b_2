package com.ruoyi.anticorrosion.mapper;

import java.util.List;
import com.ruoyi.anticorrosion.domain.EquipAnticorrosion;

/**
 * 防腐蚀检测Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface EquipAnticorrosionMapper 
{
    /**
     * 查询防腐蚀检测
     * 
     * @param id 防腐蚀检测主键
     * @return 防腐蚀检测
     */
    public EquipAnticorrosion selectEquipAnticorrosionById(Long id);

    /**
     * 查询防腐蚀检测列表
     * 
     * @param equipAnticorrosion 防腐蚀检测
     * @return 防腐蚀检测集合
     */
    public List<EquipAnticorrosion> selectEquipAnticorrosionList(EquipAnticorrosion equipAnticorrosion);

    /**
     * 新增防腐蚀检测
     * 
     * @param equipAnticorrosion 防腐蚀检测
     * @return 结果
     */
    public int insertEquipAnticorrosion(EquipAnticorrosion equipAnticorrosion);

    /**
     * 修改防腐蚀检测
     * 
     * @param equipAnticorrosion 防腐蚀检测
     * @return 结果
     */
    public int updateEquipAnticorrosion(EquipAnticorrosion equipAnticorrosion);

    /**
     * 删除防腐蚀检测
     * 
     * @param id 防腐蚀检测主键
     * @return 结果
     */
    public int deleteEquipAnticorrosionById(Long id);

    /**
     * 批量删除防腐蚀检测
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipAnticorrosionByIds(Long[] ids);
}
