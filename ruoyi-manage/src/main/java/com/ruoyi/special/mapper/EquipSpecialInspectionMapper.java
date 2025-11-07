package com.ruoyi.special.mapper;

import java.util.List;
import com.ruoyi.special.domain.EquipSpecialInspection;

/**
 * 检验检测Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
public interface EquipSpecialInspectionMapper 
{
    /**
     * 查询检验检测
     * 
     * @param id 检验检测主键
     * @return 检验检测
     */
    public EquipSpecialInspection selectEquipSpecialInspectionById(Long id);

    /**
     * 查询检验检测列表
     * 
     * @param equipSpecialInspection 检验检测
     * @return 检验检测集合
     */
    public List<EquipSpecialInspection> selectEquipSpecialInspectionList(EquipSpecialInspection equipSpecialInspection);

    /**
     * 新增检验检测
     * 
     * @param equipSpecialInspection 检验检测
     * @return 结果
     */
    public int insertEquipSpecialInspection(EquipSpecialInspection equipSpecialInspection);

    /**
     * 修改检验检测
     * 
     * @param equipSpecialInspection 检验检测
     * @return 结果
     */
    public int updateEquipSpecialInspection(EquipSpecialInspection equipSpecialInspection);

    /**
     * 删除检验检测
     * 
     * @param id 检验检测主键
     * @return 结果
     */
    public int deleteEquipSpecialInspectionById(Long id);

    /**
     * 批量删除检验检测
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipSpecialInspectionByIds(Long[] ids);
}
