package com.ruoyi.special.service;

import java.util.List;
import com.ruoyi.special.domain.EquipSpecialInspection;

/**
 * 检验检测Service接口
 *
 * @author ruoyi
 * @date 2025-11-04
 */
public interface IEquipSpecialInspectionService
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
     * 批量删除检验检测
     *
     * @param ids 需要删除的检验检测主键集合
     * @return 结果
     */
    public int deleteEquipSpecialInspectionByIds(Long[] ids);

    /**
     * 删除检验检测信息
     *
     * @param id 检验检测主键
     * @return 结果
     */
    public int deleteEquipSpecialInspectionById(Long id);

    /**
     * 自动提醒
     * @param days 天数
     */
    void autoRemindNotice(Integer days);
}
