package com.ruoyi.lubr.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.lubr.domain.EquipLubrPlan;
import com.ruoyi.lubr.mapper.EquipLubrPlanMapper;
import com.ruoyi.lubr.service.IEquipLubrPlanService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 润滑计划Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipLubrPlanServiceImpl implements IEquipLubrPlanService {
    @Autowired
    private EquipLubrPlanMapper equipLubrPlanMapper;

    @Autowired
    private EquipLedgerMapper equipLedgerMapper;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询润滑计划
     *
     * @param planId 润滑计划主键
     * @return 润滑计划
     */
    @Override
    public EquipLubrPlan selectEquipLubrPlanByPlanId(Long planId) {
        return equipLubrPlanMapper.selectEquipLubrPlanByPlanId(planId);
    }

    /**
     * 查询润滑计划列表
     *
     * @param equipLubrPlan 润滑计划
     * @return 润滑计划
     */
    @Override
    public List<EquipLubrPlan> selectEquipLubrPlanList(EquipLubrPlan equipLubrPlan) {
        return equipLubrPlanMapper.selectEquipLubrPlanList(equipLubrPlan);
    }

    /**
     * 新增润滑计划
     *
     * @param equipLubrPlan 润滑计划
     * @return 结果
     */
    @Override
    public int insertEquipLubrPlan(EquipLubrPlan equipLubrPlan) {
        // 生成计划编号
        if (equipLubrPlan.getPlanNo() == null || equipLubrPlan.getPlanNo().isEmpty()) {
            String planNo = "LP" + System.currentTimeMillis();
            equipLubrPlan.setPlanNo(planNo);
        }
        //如果有设备
        if (StringUtils.isNotNull(equipLubrPlan.getEquipId())) {
            EquipLedger equipLedger = equipLedgerMapper.selectEquipLedgerByEquipId(equipLubrPlan.getEquipId());
            if (StringUtils.isNotNull(equipLedger)) {
                equipLubrPlan.setEquipName(equipLedger.getEquName());
                equipLubrPlan.setEquCode(equipLedger.getEquCode());
                equipLubrPlan.setEquipLevels(equipLedger.getEquipLevels());
            }
        }
        //如果有负责人
        if (StringUtils.isNotNull(equipLubrPlan.getResponsibleId())) {
            SysUser sysUser = sysUserService.selectUserById(equipLubrPlan.getResponsibleId());
            if (StringUtils.isNotNull(sysUser)) {
                equipLubrPlan.setResponsibleName(sysUser.getUserName());
            }
        }
        equipLubrPlan.setCreateTime(DateUtils.getNowDate());
        return equipLubrPlanMapper.insertEquipLubrPlan(equipLubrPlan);
    }

    /**
     * 修改润滑计划
     *
     * @param equipLubrPlan 润滑计划
     * @return 结果
     */
    @Override
    public int updateEquipLubrPlan(EquipLubrPlan equipLubrPlan) {
        equipLubrPlan.setUpdateTime(DateUtils.getNowDate());
        return equipLubrPlanMapper.updateEquipLubrPlan(equipLubrPlan);
    }

    /**
     * 批量删除润滑计划
     *
     * @param planIds 需要删除的润滑计划主键
     * @return 结果
     */
    @Override
    public int deleteEquipLubrPlanByPlanIds(Long[] planIds) {
        return equipLubrPlanMapper.deleteEquipLubrPlanByPlanIds(planIds);
    }

    /**
     * 删除润滑计划信息
     *
     * @param planId 润滑计划主键
     * @return 结果
     */
    @Override
    public int deleteEquipLubrPlanByPlanId(Long planId) {
        return equipLubrPlanMapper.deleteEquipLubrPlanByPlanId(planId);
    }
}
