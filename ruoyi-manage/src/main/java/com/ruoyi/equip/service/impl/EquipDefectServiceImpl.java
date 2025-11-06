package com.ruoyi.equip.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipDefect;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.mapper.EquipDefectMapper;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.equip.service.IEquipDefectService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备缺陷管理Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipDefectServiceImpl implements IEquipDefectService {
    @Autowired
    private EquipDefectMapper equipDefectMapper;

    @Autowired
    private EquipLedgerMapper equipLedgerMapper;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询设备缺陷管理
     *
     * @param defectId 设备缺陷管理主键
     * @return 设备缺陷管理
     */
    @Override
    public EquipDefect selectEquipDefectByDefectId(Long defectId) {
        return equipDefectMapper.selectEquipDefectByDefectId(defectId);
    }

    /**
     * 查询设备缺陷管理列表
     *
     * @param equipDefect 设备缺陷管理
     * @return 设备缺陷管理
     */
    @Override
    public List<EquipDefect> selectEquipDefectList(EquipDefect equipDefect) {
        return equipDefectMapper.selectEquipDefectList(equipDefect);
    }

    /**
     * 新增设备缺陷管理
     *
     * @param equipDefect 设备缺陷管理
     * @return 结果
     */
    @Override
    public int insertEquipDefect(EquipDefect equipDefect) {
        // 自动生成缺陷编号
        if (equipDefect.getDefectNo() == null || equipDefect.getDefectNo().isEmpty()) {
            String defectNo = "DEF" + System.currentTimeMillis();
            equipDefect.setDefectNo(defectNo);
        }
        //如果有设备
        if (StringUtils.isNotNull(equipDefect.getEquipmentId())) {
            EquipLedger equipLedger = equipLedgerMapper.selectEquipLedgerByEquipId(equipDefect.getEquipmentId());
            if (StringUtils.isNotNull(equipLedger)) {
                equipDefect.setEquipmentName(equipLedger.getEquName());
                equipDefect.setEquipmentCode(equipLedger.getEquCode());
                equipDefect.setEquipLevels(equipLedger.getEquipLevels());
            }
        }
        //如果有报告人
        if (StringUtils.isNotEmpty(equipDefect.getReporterEmpNo())) {
            SysUser sysUser = sysUserService.selectUserById(Long.parseLong(equipDefect.getReporterEmpNo()));
            if (StringUtils.isNotNull(sysUser)) {
                equipDefect.setReporterName(sysUser.getUserName());
            }
        }
        //如果有复核人
        if (StringUtils.isNotEmpty(equipDefect.getReviewerEmpNo())) {
            SysUser sysUser = sysUserService.selectUserById(Long.parseLong(equipDefect.getReviewerEmpNo()));
            if (StringUtils.isNotNull(sysUser)) {
                equipDefect.setReviewerName(sysUser.getUserName());
            }
        }
        equipDefect.setCreateTime(DateUtils.getNowDate());
        return equipDefectMapper.insertEquipDefect(equipDefect);
    }

    /**
     * 修改设备缺陷管理
     *
     * @param equipDefect 设备缺陷管理
     * @return 结果
     */
    @Override
    public int updateEquipDefect(EquipDefect equipDefect) {
        equipDefect.setUpdateTime(DateUtils.getNowDate());
        return equipDefectMapper.updateEquipDefect(equipDefect);
    }

    /**
     * 批量删除设备缺陷管理
     *
     * @param defectIds 需要删除的设备缺陷管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipDefectByDefectIds(Long[] defectIds) {
        return equipDefectMapper.deleteEquipDefectByDefectIds(defectIds);
    }

    /**
     * 删除设备缺陷管理信息
     *
     * @param defectId 设备缺陷管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipDefectByDefectId(Long defectId) {
        return equipDefectMapper.deleteEquipDefectByDefectId(defectId);
    }
}
