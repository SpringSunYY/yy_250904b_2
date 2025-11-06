package com.ruoyi.equip.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.equip.mapper.EqInspectionMapper;
import com.ruoyi.equip.domain.EqInspection;
import com.ruoyi.equip.service.IEqInspectionService;

/**
 * 设备检验检测Service业务层处理
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EqInspectionServiceImpl implements IEqInspectionService 
{
    @Autowired
    private EqInspectionMapper eqInspectionMapper;

    /**
     * 查询设备检验检测
     * 
     * @param inspectionId 设备检验检测主键
     * @return 设备检验检测
     */
    @Override
    public EqInspection selectEqInspectionByInspectionId(Long inspectionId)
    {
        return eqInspectionMapper.selectEqInspectionByInspectionId(inspectionId);
    }

    /**
     * 查询设备检验检测列表
     * 
     * @param eqInspection 设备检验检测
     * @return 设备检验检测
     */
    @Override
    public List<EqInspection> selectEqInspectionList(EqInspection eqInspection)
    {
        return eqInspectionMapper.selectEqInspectionList(eqInspection);
    }

    /**
     * 新增设备检验检测
     * 
     * @param eqInspection 设备检验检测
     * @return 结果
     */
    @Override
    public int insertEqInspection(EqInspection eqInspection)
    {
        eqInspection.setCreateTime(DateUtils.getNowDate());
        return eqInspectionMapper.insertEqInspection(eqInspection);
    }

    /**
     * 修改设备检验检测
     * 
     * @param eqInspection 设备检验检测
     * @return 结果
     */
    @Override
    public int updateEqInspection(EqInspection eqInspection)
    {
        eqInspection.setUpdateTime(DateUtils.getNowDate());
        return eqInspectionMapper.updateEqInspection(eqInspection);
    }

    /**
     * 批量删除设备检验检测
     * 
     * @param inspectionIds 需要删除的设备检验检测主键
     * @return 结果
     */
    @Override
    public int deleteEqInspectionByInspectionIds(Long[] inspectionIds)
    {
        return eqInspectionMapper.deleteEqInspectionByInspectionIds(inspectionIds);
    }

    /**
     * 删除设备检验检测信息
     * 
     * @param inspectionId 设备检验检测主键
     * @return 结果
     */
    @Override
    public int deleteEqInspectionByInspectionId(Long inspectionId)
    {
        return eqInspectionMapper.deleteEqInspectionByInspectionId(inspectionId);
    }
}
