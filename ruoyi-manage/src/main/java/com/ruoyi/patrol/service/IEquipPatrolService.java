package com.ruoyi.patrol.service;

import java.util.List;
import com.ruoyi.patrol.domain.EquipPatrol;

/**
 * 巡检记录Service接口
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
public interface IEquipPatrolService 
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
     * 批量删除巡检记录
     * 
     * @param patrolIds 需要删除的巡检记录主键集合
     * @return 结果
     */
    public int deleteEquipPatrolByPatrolIds(Long[] patrolIds);

    /**
     * 删除巡检记录信息
     * 
     * @param patrolId 巡检记录主键
     * @return 结果
     */
    public int deleteEquipPatrolByPatrolId(Long patrolId);
}
