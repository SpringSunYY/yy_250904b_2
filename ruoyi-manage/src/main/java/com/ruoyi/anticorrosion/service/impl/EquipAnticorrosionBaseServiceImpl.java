package com.ruoyi.anticorrosion.service.impl;

import com.ruoyi.anticorrosion.domain.EquipAnticorrosionBase;
import com.ruoyi.anticorrosion.mapper.EquipAnticorrosionBaseMapper;
import com.ruoyi.anticorrosion.service.IEquipAnticorrosionBaseService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.service.IEquipLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 防腐基础信息Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipAnticorrosionBaseServiceImpl implements IEquipAnticorrosionBaseService {
    @Autowired
    private EquipAnticorrosionBaseMapper equipAnticorrosionBaseMapper;

    @Autowired
    private IEquipLedgerService equipLedgerService;

    /**
     * 查询防腐基础信息
     *
     * @param id 防腐基础信息主键
     * @return 防腐基础信息
     */
    @Override
    public EquipAnticorrosionBase selectEquipAnticorrosionBaseById(Long id) {
        return equipAnticorrosionBaseMapper.selectEquipAnticorrosionBaseById(id);
    }

    /**
     * 查询防腐基础信息列表
     *
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 防腐基础信息
     */
    @Override
    public List<EquipAnticorrosionBase> selectEquipAnticorrosionBaseList(EquipAnticorrosionBase equipAnticorrosionBase) {
        return equipAnticorrosionBaseMapper.selectEquipAnticorrosionBaseList(equipAnticorrosionBase);
    }

    /**
     * 新增防腐基础信息
     *
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 结果
     */
    @Override
    public int insertEquipAnticorrosionBase(EquipAnticorrosionBase equipAnticorrosionBase) {
        initData(equipAnticorrosionBase);
        equipAnticorrosionBase.setCreateTime(DateUtils.getNowDate());
        return equipAnticorrosionBaseMapper.insertEquipAnticorrosionBase(equipAnticorrosionBase);
    }

    private void initData(EquipAnticorrosionBase equipAnticorrosionBase) {
        //如果传入设备id，则根据设备id查询设备信息
        if (StringUtils.isNotNull(equipAnticorrosionBase.getEquipmentId())) {
            EquipLedger equipLedger = equipLedgerService.selectEquipLedgerByEquipId(equipAnticorrosionBase.getEquipmentId());
            if (StringUtils.isNotNull(equipLedger)) {
                equipAnticorrosionBase.setEquipmentId(equipLedger.getEquipId());
                equipAnticorrosionBase.setEquipmentName(equipLedger.getEquName());
                equipAnticorrosionBase.setEquipmentCode(equipLedger.getEquCode());
                equipAnticorrosionBase.setEquipLevels(equipLedger.getEquipLevels());
                equipAnticorrosionBase.setSpecification(equipLedger.getSpecification());
            }
        }
    }

    /**
     * 修改防腐基础信息
     *
     * @param equipAnticorrosionBase 防腐基础信息
     * @return 结果
     */
    @Override
    public int updateEquipAnticorrosionBase(EquipAnticorrosionBase equipAnticorrosionBase) {
        initData(equipAnticorrosionBase);
        equipAnticorrosionBase.setUpdateTime(DateUtils.getNowDate());
        return equipAnticorrosionBaseMapper.updateEquipAnticorrosionBase(equipAnticorrosionBase);
    }

    /**
     * 批量删除防腐基础信息
     *
     * @param ids 需要删除的防腐基础信息主键
     * @return 结果
     */
    @Override
    public int deleteEquipAnticorrosionBaseByIds(Long[] ids) {
        return equipAnticorrosionBaseMapper.deleteEquipAnticorrosionBaseByIds(ids);
    }

    /**
     * 删除防腐基础信息信息
     *
     * @param id 防腐基础信息主键
     * @return 结果
     */
    @Override
    public int deleteEquipAnticorrosionBaseById(Long id) {
        return equipAnticorrosionBaseMapper.deleteEquipAnticorrosionBaseById(id);
    }
}
