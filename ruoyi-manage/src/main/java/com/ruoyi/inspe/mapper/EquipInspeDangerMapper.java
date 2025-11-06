package com.ruoyi.inspe.mapper;

import java.util.List;
import com.ruoyi.inspe.domain.EquipInspeDanger;
import org.apache.ibatis.annotations.Mapper;

/**
 * 设备隐患治理Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Mapper
public interface EquipInspeDangerMapper 
{
    /**
     * 查询设备隐患治理
     * 
     * @param dangerId 设备隐患治理主键
     * @return 设备隐患治理
     */
    public EquipInspeDanger selectEquipInspeDangerByDangerId(Long dangerId);

    /**
     * 查询设备隐患治理列表
     * 
     * @param equipInspeDanger 设备隐患治理
     * @return 设备隐患治理集合
     */
    public List<EquipInspeDanger> selectEquipInspeDangerList(EquipInspeDanger equipInspeDanger);

    /**
     * 新增设备隐患治理
     * 
     * @param equipInspeDanger 设备隐患治理
     * @return 结果
     */
    public int insertEquipInspeDanger(EquipInspeDanger equipInspeDanger);

    /**
     * 修改设备隐患治理
     * 
     * @param equipInspeDanger 设备隐患治理
     * @return 结果
     */
    public int updateEquipInspeDanger(EquipInspeDanger equipInspeDanger);

    /**
     * 删除设备隐患治理
     * 
     * @param dangerId 设备隐患治理主键
     * @return 结果
     */
    public int deleteEquipInspeDangerByDangerId(Long dangerId);

    /**
     * 批量删除设备隐患治理
     * 
     * @param dangerIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipInspeDangerByDangerIds(Long[] dangerIds);
}
