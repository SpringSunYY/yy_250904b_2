package com.ruoyi.equip.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.domain.EquipScrapLedger;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.equip.mapper.EquipScrapLedgerMapper;
import com.ruoyi.equip.service.IEquipScrapLedgerService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备报废管理Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipScrapLedgerServiceImpl implements IEquipScrapLedgerService {
    @Autowired
    private EquipScrapLedgerMapper equipScrapLedgerMapper;

    @Autowired
    private EquipLedgerMapper equipLedgerMapper;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 查询设备报废管理
     *
     * @param scrapId 设备报废管理主键
     * @return 设备报废管理
     */
    @Override
    public EquipScrapLedger selectEquipScrapLedgerByScrapId(Long scrapId) {
        return equipScrapLedgerMapper.selectEquipScrapLedgerByScrapId(scrapId);
    }

    /**
     * 查询设备报废管理列表
     *
     * @param equipScrapLedger 设备报废管理
     * @return 设备报废管理
     */
    @Override
    public List<EquipScrapLedger> selectEquipScrapLedgerList(EquipScrapLedger equipScrapLedger) {
        return equipScrapLedgerMapper.selectEquipScrapLedgerList(equipScrapLedger);
    }

    /**
     * 新增设备报废管理
     *
     * @param equipScrapLedger 设备报废管理
     * @return 结果
     */
    @Override
    public int insertEquipScrapLedger(EquipScrapLedger equipScrapLedger) {
        // 自动生成报废编号
        if (equipScrapLedger.getScrapNo() == null || equipScrapLedger.getScrapNo().isEmpty()) {
            String scrapNo = "SCR" + System.currentTimeMillis();
            equipScrapLedger.setScrapNo(scrapNo);
        }
        //如果有设备
        if (StringUtils.isNotNull(equipScrapLedger.getEquipmentId())) {
            EquipLedger equipLedger = equipLedgerMapper.selectEquipLedgerByEquipId(equipScrapLedger.getEquipmentId());
            if (StringUtils.isNotNull(equipLedger)) {
                equipScrapLedger.setEquipName(equipLedger.getEquName());
                equipScrapLedger.setEquCode(equipLedger.getEquCode());
            }
        }
        //如果有申请人
        if (StringUtils.isNotNull(equipScrapLedger.getApplicantId())) {
            SysUser sysUser = sysUserService.selectUserById(equipScrapLedger.getApplicantId());
            if (StringUtils.isNotNull(sysUser)) {
                equipScrapLedger.setApplicantName(sysUser.getNickName());
            }
        }
        equipScrapLedger.setCreateTime(DateUtils.getNowDate());
        return equipScrapLedgerMapper.insertEquipScrapLedger(equipScrapLedger);
    }

    /**
     * 修改设备报废管理
     *
     * @param equipScrapLedger 设备报废管理
     * @return 结果
     */
    @Override
    public int updateEquipScrapLedger(EquipScrapLedger equipScrapLedger) {
        equipScrapLedger.setUpdateTime(DateUtils.getNowDate());
        return equipScrapLedgerMapper.updateEquipScrapLedger(equipScrapLedger);
    }

    /**
     * 批量删除设备报废管理
     *
     * @param scrapIds 需要删除的设备报废管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipScrapLedgerByScrapIds(Long[] scrapIds) {
        return equipScrapLedgerMapper.deleteEquipScrapLedgerByScrapIds(scrapIds);
    }

    /**
     * 删除设备报废管理信息
     *
     * @param scrapId 设备报废管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipScrapLedgerByScrapId(Long scrapId) {
        return equipScrapLedgerMapper.deleteEquipScrapLedgerByScrapId(scrapId);
    }
}
