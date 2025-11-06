package com.ruoyi.inspe.service;

import java.util.List;
import com.ruoyi.inspe.domain.EquipInspe;

/**
 * 设备检查记录Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipInspeService 
{
    /**
     * 查询设备检查记录
     * 
     * @param inspeId 设备检查记录主键
     * @return 设备检查记录
     */
    public EquipInspe selectEquipInspeByInspeId(Long inspeId);

    /**
     * 查询设备检查记录列表
     * 
     * @param equipInspe 设备检查记录
     * @return 设备检查记录集合
     */
    public List<EquipInspe> selectEquipInspeList(EquipInspe equipInspe);

    /**
     * 新增设备检查记录
     * 
     * @param equipInspe 设备检查记录
     * @return 结果
     */
    public int insertEquipInspe(EquipInspe equipInspe);

    /**
     * 修改设备检查记录
     * 
     * @param equipInspe 设备检查记录
     * @return 结果
     */
    public int updateEquipInspe(EquipInspe equipInspe);

    /**
     * 批量删除设备检查记录
     * 
     * @param inspeIds 需要删除的设备检查记录主键集合
     * @return 结果
     */
    public int deleteEquipInspeByInspeIds(Long[] inspeIds);

    /**
     * 删除设备检查记录信息
     * 
     * @param inspeId 设备检查记录主键
     * @return 结果
     */
    public int deleteEquipInspeByInspeId(Long inspeId);
}
