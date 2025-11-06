package com.ruoyi.patrol.mapper;

import java.util.List;
import com.ruoyi.patrol.domain.EquipPatrol;
import com.ruoyi.patrol.domain.EquipPatrolDetail;

/**
 * 巡检记录Mapper接口
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
public interface EquipPatrolMapper 
{
    /**
     * 查询巡检记录
     * 
     * @param patrolId 巡检记录主键
     * @return 巡检记录
     */
    public EquipPatrol selectEquipPatrolByPatrolId(Long patrolId);

    /**
     * 查询巡检记录列表
     * 
     * @param equipPatrol 巡检记录
     * @return 巡检记录集合
     */
    public List<EquipPatrol> selectEquipPatrolList(EquipPatrol equipPatrol);

    /**
     * 新增巡检记录
     * 
     * @param equipPatrol 巡检记录
     * @return 结果
     */
    public int insertEquipPatrol(EquipPatrol equipPatrol);

    /**
     * 修改巡检记录
     * 
     * @param equipPatrol 巡检记录
     * @return 结果
     */
    public int updateEquipPatrol(EquipPatrol equipPatrol);

    /**
     * 删除巡检记录
     * 
     * @param patrolId 巡检记录主键
     * @return 结果
     */
    public int deleteEquipPatrolByPatrolId(Long patrolId);

    /**
     * 批量删除巡检记录
     * 
     * @param patrolIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipPatrolByPatrolIds(Long[] patrolIds);

    /**
     * 批量删除巡检记录明细
     * 
     * @param patrolIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipPatrolDetailByPatrolIds(Long[] patrolIds);
    
    /**
     * 批量新增巡检记录明细
     * 
     * @param equipPatrolDetailList 巡检记录明细列表
     * @return 结果
     */
    public int batchEquipPatrolDetail(List<EquipPatrolDetail> equipPatrolDetailList);
    

    /**
     * 通过巡检记录主键删除巡检记录明细信息
     * 
     * @param patrolId 巡检记录ID
     * @return 结果
     */
    public int deleteEquipPatrolDetailByPatrolId(Long patrolId);
}
