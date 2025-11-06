package com.ruoyi.lubr.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.lubr.domain.EquipLubrPlan;
import com.ruoyi.lubr.domain.EquipLubrRecord2;
import com.ruoyi.lubr.mapper.EquipLubrRecord2Mapper;
import com.ruoyi.lubr.service.IEquipLubrPlanService;
import com.ruoyi.lubr.service.IEquipLubrRecord2Service;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备润滑记录Service业务层处理
 *
 * @author laogao
 * @date 2025-09-30
 */
@Service
public class EquipLubrRecord2ServiceImpl implements IEquipLubrRecord2Service {
    @Autowired
    private EquipLubrRecord2Mapper equipLubrRecord2Mapper;

    @Autowired
    private IEquipLubrPlanService equipLubrPlanService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询设备润滑记录
     *
     * @param recordId 设备润滑记录主键
     * @return 设备润滑记录
     */
    @Override
    public EquipLubrRecord2 selectEquipLubrRecord2ByRecordId(Long recordId) {
        return equipLubrRecord2Mapper.selectEquipLubrRecord2ByRecordId(recordId);
    }

    /**
     * 查询设备润滑记录列表
     *
     * @param equipLubrRecord2 设备润滑记录
     * @return 设备润滑记录
     */
    @Override
    public List<EquipLubrRecord2> selectEquipLubrRecord2List(EquipLubrRecord2 equipLubrRecord2) {
        return equipLubrRecord2Mapper.selectEquipLubrRecord2List(equipLubrRecord2);
    }

    /**
     * 新增设备润滑记录
     *
     * @param equipLubrRecord2 设备润滑记录
     * @return 结果
     */
    @Override
    public int insertEquipLubrRecord2(EquipLubrRecord2 equipLubrRecord2) {
        initDate(equipLubrRecord2);
        equipLubrRecord2.setCreateTime(DateUtils.getNowDate());
        return equipLubrRecord2Mapper.insertEquipLubrRecord2(equipLubrRecord2);
    }

    private void initDate(EquipLubrRecord2 equipLubrRecord2) {
        //如果有计划编号
        if (StringUtils.isNotNull(equipLubrRecord2.getPlanId())) {
            EquipLubrPlan equipLubrPlan = equipLubrPlanService.selectEquipLubrPlanByPlanId(equipLubrRecord2.getPlanId());
            if (StringUtils.isNotNull(equipLubrPlan)) {
                equipLubrRecord2.setPlanId(equipLubrPlan.getPlanId());
                equipLubrRecord2.setEquipId(equipLubrPlan.getEquipId());
                equipLubrRecord2.setPlanNo(equipLubrPlan.getPlanNo());
                equipLubrRecord2.setEquipName(equipLubrPlan.getEquipName());
                equipLubrRecord2.setEquipCode(equipLubrPlan.getEquCode());
                equipLubrRecord2.setEquipLevels(equipLubrPlan.getEquipLevels());
            }
        }
        //如果有复核人
        if (StringUtils.isNotNull(equipLubrRecord2.getReviewerId())) {
            SysUser sysUser = sysUserService.selectUserById(Long.parseLong(equipLubrRecord2.getReviewerId()));
            if (StringUtils.isNotNull(sysUser)) {
                equipLubrRecord2.setReviewerName(sysUser.getUserName());
            }
        }
    }

    /**
     * 修改设备润滑记录
     *
     * @param equipLubrRecord2 设备润滑记录
     * @return 结果
     */
    @Override
    public int updateEquipLubrRecord2(EquipLubrRecord2 equipLubrRecord2) {
        initDate(equipLubrRecord2);
        equipLubrRecord2.setUpdateTime(DateUtils.getNowDate());
        return equipLubrRecord2Mapper.updateEquipLubrRecord2(equipLubrRecord2);
    }

    /**
     * 批量删除设备润滑记录
     *
     * @param recordIds 需要删除的设备润滑记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipLubrRecord2ByRecordIds(Long[] recordIds) {
        return equipLubrRecord2Mapper.deleteEquipLubrRecord2ByRecordIds(recordIds);
    }

    /**
     * 删除设备润滑记录信息
     *
     * @param recordId 设备润滑记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipLubrRecord2ByRecordId(Long recordId) {
        return equipLubrRecord2Mapper.deleteEquipLubrRecord2ByRecordId(recordId);
    }
}
