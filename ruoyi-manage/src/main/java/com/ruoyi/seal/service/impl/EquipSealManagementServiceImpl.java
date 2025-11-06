package com.ruoyi.seal.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.seal.domain.EquipSealManagement;
import com.ruoyi.seal.mapper.EquipSealManagementMapper;
import com.ruoyi.seal.service.IEquipSealManagementService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 泄露处置Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipSealManagementServiceImpl implements IEquipSealManagementService {
    @Autowired
    private EquipSealManagementMapper equipSealManagementMapper;

    @Autowired
    private EquipLedgerMapper equipLedgerMapper;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询泄露处置
     *
     * @param manageId 泄露处置主键
     * @return 泄露处置
     */
    @Override
    public EquipSealManagement selectEquipSealManagementByManageId(Long manageId) {
        return equipSealManagementMapper.selectEquipSealManagementByManageId(manageId);
    }

    /**
     * 查询泄露处置列表
     *
     * @param equipSealManagement 泄露处置
     * @return 泄露处置
     */
    @Override
    public List<EquipSealManagement> selectEquipSealManagementList(EquipSealManagement equipSealManagement) {
        return equipSealManagementMapper.selectEquipSealManagementList(equipSealManagement);
    }

    /**
     * 新增泄露处置
     *
     * @param equipSealManagement 泄露处置
     * @return 结果
     */
    @Override
    public int insertEquipSealManagement(EquipSealManagement equipSealManagement) {
        //如果有设备
        if (StringUtils.isNotNull(equipSealManagement.getEquipId())) {
            EquipLedger equipLedger = equipLedgerMapper.selectEquipLedgerByEquipId(equipSealManagement.getEquipId());
            if (StringUtils.isNotNull(equipLedger)) {
                equipSealManagement.setEquipName(equipLedger.getEquName());
                equipSealManagement.setEquipCode(equipLedger.getEquCode());
                equipSealManagement.setEquipLevels(equipLedger.getEquipLevels());
            }
        }
        //如果有报告人
        if (StringUtils.isNotEmpty(equipSealManagement.getReporterId())) {
            SysUser sysUser = sysUserService.selectUserById(Long.parseLong(equipSealManagement.getReporterId()));
            if (StringUtils.isNotNull(sysUser)) {
                equipSealManagement.setReporterName(sysUser.getUserName());
            }
        }
        equipSealManagement.setCreateTime(DateUtils.getNowDate());
        return equipSealManagementMapper.insertEquipSealManagement(equipSealManagement);
    }

    /**
     * 修改泄露处置
     *
     * @param equipSealManagement 泄露处置
     * @return 结果
     */
    @Override
    public int updateEquipSealManagement(EquipSealManagement equipSealManagement) {
        equipSealManagement.setUpdateTime(DateUtils.getNowDate());
        return equipSealManagementMapper.updateEquipSealManagement(equipSealManagement);
    }

    /**
     * 批量删除泄露处置
     *
     * @param manageIds 需要删除的泄露处置主键
     * @return 结果
     */
    @Override
    public int deleteEquipSealManagementByManageIds(Long[] manageIds) {
        return equipSealManagementMapper.deleteEquipSealManagementByManageIds(manageIds);
    }

    /**
     * 删除泄露处置信息
     *
     * @param manageId 泄露处置主键
     * @return 结果
     */
    @Override
    public int deleteEquipSealManagementByManageId(Long manageId) {
        return equipSealManagementMapper.deleteEquipSealManagementByManageId(manageId);
    }
}
