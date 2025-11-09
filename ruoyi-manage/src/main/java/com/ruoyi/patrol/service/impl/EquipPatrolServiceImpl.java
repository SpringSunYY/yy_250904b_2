package com.ruoyi.patrol.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.patrol.domain.EquipPatrol;
import com.ruoyi.patrol.domain.EquipPatrolDetail;
import com.ruoyi.patrol.mapper.EquipPatrolMapper;
import com.ruoyi.patrol.service.IEquipPatrolService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 巡检记录Service业务层处理
 *
 * @author ruoyi
 * @date 2025-09-23
 */
@Service
public class EquipPatrolServiceImpl implements IEquipPatrolService {
    @Autowired
    private EquipPatrolMapper equipPatrolMapper;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 查询巡检记录
     *
     * @param patrolId 巡检记录主键
     * @return 巡检记录
     */
    @Override
    public EquipPatrol selectEquipPatrolByPatrolId(Long patrolId) {
        return equipPatrolMapper.selectEquipPatrolByPatrolId(patrolId);
    }

    /**
     * 查询巡检记录列表
     *
     * @param equipPatrol 巡检记录
     * @return 巡检记录
     */
    @Override
    public List<EquipPatrol> selectEquipPatrolList(EquipPatrol equipPatrol) {
        List<EquipPatrol> equipPatrols = equipPatrolMapper.selectEquipPatrolList(equipPatrol);
        for (EquipPatrol info : equipPatrols) {
            SysUser sysUser = sysUserService.selectUserById(info.getProcessUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setProcessUserName(sysUser.getNickName());
            }
        }
        return equipPatrols;
    }

    /**
     * 新增巡检记录
     *
     * @param equipPatrol 巡检记录
     * @return 结果
     */
    @Transactional
    @Override
    public int insertEquipPatrol(EquipPatrol equipPatrol) {
        equipPatrol.setCreateTime(DateUtils.getNowDate());
        int rows = equipPatrolMapper.insertEquipPatrol(equipPatrol);
        insertEquipPatrolDetail(equipPatrol);
        return rows;
    }

    /**
     * 修改巡检记录
     *
     * @param equipPatrol 巡检记录
     * @return 结果
     */
    @Transactional
    @Override
    public int updateEquipPatrol(EquipPatrol equipPatrol) {
        equipPatrol.setUpdateTime(DateUtils.getNowDate());
        equipPatrolMapper.deleteEquipPatrolDetailByPatrolId(equipPatrol.getPatrolId());
        insertEquipPatrolDetail(equipPatrol);
        return equipPatrolMapper.updateEquipPatrol(equipPatrol);
    }

    /**
     * 批量删除巡检记录
     *
     * @param patrolIds 需要删除的巡检记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipPatrolByPatrolIds(Long[] patrolIds) {
        equipPatrolMapper.deleteEquipPatrolDetailByPatrolIds(patrolIds);
        return equipPatrolMapper.deleteEquipPatrolByPatrolIds(patrolIds);
    }

    /**
     * 删除巡检记录信息
     *
     * @param patrolId 巡检记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipPatrolByPatrolId(Long patrolId) {
        equipPatrolMapper.deleteEquipPatrolDetailByPatrolId(patrolId);
        return equipPatrolMapper.deleteEquipPatrolByPatrolId(patrolId);
    }

    /**
     * 新增巡检记录明细信息
     *
     * @param equipPatrol 巡检记录对象
     */
    public void insertEquipPatrolDetail(EquipPatrol equipPatrol) {
        List<EquipPatrolDetail> equipPatrolDetailList = equipPatrol.getEquipPatrolDetailList();
        Long patrolId = equipPatrol.getPatrolId();
        if (StringUtils.isNotNull(equipPatrolDetailList)) {
            List<EquipPatrolDetail> list = new ArrayList<EquipPatrolDetail>();
            for (EquipPatrolDetail equipPatrolDetail : equipPatrolDetailList) {
                equipPatrolDetail.setPatrolId(patrolId);
                list.add(equipPatrolDetail);
            }
            if (list.size() > 0) {
                equipPatrolMapper.batchEquipPatrolDetail(list);
            }
        }
    }
}
