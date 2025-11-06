package com.ruoyi.pur.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.pur.mapper.EquipPurchaserSupplierMapper;
import com.ruoyi.pur.domain.EquipPurchaserSupplier;
import com.ruoyi.pur.service.IEquipPurchaserSupplierService;

/**
 * 供应商管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
@Service
public class EquipPurchaserSupplierServiceImpl implements IEquipPurchaserSupplierService 
{
    @Autowired
    private EquipPurchaserSupplierMapper equipPurchaserSupplierMapper;

    /**
     * 查询供应商管理
     * 
     * @param supplierId 供应商管理主键
     * @return 供应商管理
     */
    @Override
    public EquipPurchaserSupplier selectEquipPurchaserSupplierBySupplierId(Long supplierId)
    {
        return equipPurchaserSupplierMapper.selectEquipPurchaserSupplierBySupplierId(supplierId);
    }

    /**
     * 查询供应商管理列表
     * 
     * @param equipPurchaserSupplier 供应商管理
     * @return 供应商管理
     */
    @Override
    public List<EquipPurchaserSupplier> selectEquipPurchaserSupplierList(EquipPurchaserSupplier equipPurchaserSupplier)
    {
        return equipPurchaserSupplierMapper.selectEquipPurchaserSupplierList(equipPurchaserSupplier);
    }

    /**
     * 新增供应商管理
     * 
     * @param equipPurchaserSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int insertEquipPurchaserSupplier(EquipPurchaserSupplier equipPurchaserSupplier)
    {
        equipPurchaserSupplier.setCreateTime(DateUtils.getNowDate());
        return equipPurchaserSupplierMapper.insertEquipPurchaserSupplier(equipPurchaserSupplier);
    }

    /**
     * 修改供应商管理
     * 
     * @param equipPurchaserSupplier 供应商管理
     * @return 结果
     */
    @Override
    public int updateEquipPurchaserSupplier(EquipPurchaserSupplier equipPurchaserSupplier)
    {
        equipPurchaserSupplier.setUpdateTime(DateUtils.getNowDate());
        return equipPurchaserSupplierMapper.updateEquipPurchaserSupplier(equipPurchaserSupplier);
    }

    /**
     * 批量删除供应商管理
     * 
     * @param supplierIds 需要删除的供应商管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipPurchaserSupplierBySupplierIds(Long[] supplierIds)
    {
        return equipPurchaserSupplierMapper.deleteEquipPurchaserSupplierBySupplierIds(supplierIds);
    }

    /**
     * 删除供应商管理信息
     * 
     * @param supplierId 供应商管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipPurchaserSupplierBySupplierId(Long supplierId)
    {
        return equipPurchaserSupplierMapper.deleteEquipPurchaserSupplierBySupplierId(supplierId);
    }
}
