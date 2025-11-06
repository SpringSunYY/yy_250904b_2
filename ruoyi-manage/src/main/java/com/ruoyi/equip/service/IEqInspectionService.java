package com.ruoyi.equip.service;

import java.util.List;
import com.ruoyi.equip.domain.EqInspection;

/**
 * 设备检验检测Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEqInspectionService 
{
    /**
     * 查询设备检验检测
     * 
     * @param inspectionId 设备检验检测主键
     * @return 设备检验检测
     */
    public EqInspection selectEqInspectionByInspectionId(Long inspectionId);

    /**
     * 查询设备检验检测列表
     * 
     * @param eqInspection 设备检验检测
     * @return 设备检验检测集合
     */
    public List<EqInspection> selectEqInspectionList(EqInspection eqInspection);

    /**
     * 新增设备检验检测
     * 
     * @param eqInspection 设备检验检测
     * @return 结果
     */
    public int insertEqInspection(EqInspection eqInspection);

    /**
     * 修改设备检验检测
     * 
     * @param eqInspection 设备检验检测
     * @return 结果
     */
    public int updateEqInspection(EqInspection eqInspection);

    /**
     * 批量删除设备检验检测
     * 
     * @param inspectionIds 需要删除的设备检验检测主键集合
     * @return 结果
     */
    public int deleteEqInspectionByInspectionIds(Long[] inspectionIds);

    /**
     * 删除设备检验检测信息
     * 
     * @param inspectionId 设备检验检测主键
     * @return 结果
     */
    public int deleteEqInspectionByInspectionId(Long inspectionId);
}
