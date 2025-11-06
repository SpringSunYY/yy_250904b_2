package com.ruoyi.equip.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.service.IEquipLedgerService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.common.core.domain.entity.SysDept;

/**
 * 设备台账Service业务层处理
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipLedgerServiceImpl implements IEquipLedgerService 
{
    @Autowired
    private EquipLedgerMapper equipLedgerMapper;
    
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询设备台账
     * 
     * @param equipId 设备台账主键
     * @return 设备台账
     */
    @Override
    public EquipLedger selectEquipLedgerByEquipId(Long equipId)
    {
        return equipLedgerMapper.selectEquipLedgerByEquipId(equipId);
    }

    /**
     * 查询设备台账列表
     * 
     * @param equipLedger 设备台账
     * @return 设备台账
     */
    @Override
    public List<EquipLedger> selectEquipLedgerList(EquipLedger equipLedger)
    {
        return equipLedgerMapper.selectEquipLedgerList(equipLedger);
    }

    /**
     * 新增设备台账
     * 
     * @param equipLedger 设备台账
     * @return 结果
     */
    @Override
    public int insertEquipLedger(EquipLedger equipLedger)
    {
        // 根据部门ID获取部门名称
        if (equipLedger.getDepartmentId() != null) {
            SysDept dept = sysDeptService.selectDeptById(equipLedger.getDepartmentId());
            if (dept != null) {
                equipLedger.setDepartmentName(dept.getDeptName());
            }
        }
        
        equipLedger.setCreateTime(DateUtils.getNowDate());
        return equipLedgerMapper.insertEquipLedger(equipLedger);
    }

    /**
     * 修改设备台账
     * 
     * @param equipLedger 设备台账
     * @return 结果
     */
    @Override
    public int updateEquipLedger(EquipLedger equipLedger)
    {
        // 根据部门ID获取部门名称
        if (equipLedger.getDepartmentId() != null) {
            SysDept dept = sysDeptService.selectDeptById(equipLedger.getDepartmentId());
            if (dept != null) {
                equipLedger.setDepartmentName(dept.getDeptName());
            }
        }
        
        equipLedger.setUpdateTime(DateUtils.getNowDate());
        return equipLedgerMapper.updateEquipLedger(equipLedger);
    }

    /**
     * 批量删除设备台账
     * 
     * @param equipIds 需要删除的设备台账主键
     * @return 结果
     */
    @Override
    public int deleteEquipLedgerByEquipIds(Long[] equipIds)
    {
        return equipLedgerMapper.deleteEquipLedgerByEquipIds(equipIds);
    }

    /**
     * 删除设备台账信息
     * 
     * @param equipId 设备台账主键
     * @return 结果
     */
    @Override
    public int deleteEquipLedgerByEquipId(Long equipId)
    {
        return equipLedgerMapper.deleteEquipLedgerByEquipId(equipId);
    }
}
