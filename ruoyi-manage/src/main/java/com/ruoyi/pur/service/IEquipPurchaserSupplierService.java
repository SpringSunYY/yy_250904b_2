package com.ruoyi.pur.service;

import java.util.List;
import com.ruoyi.pur.domain.EquipPurchaserSupplier;

/**
 * 供应商管理Service接口
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
public interface IEquipPurchaserSupplierService 
{
    /**
     * 查询供应商管理
     * 
     * @param supplierId 供应商管理主键
     * @return 供应商管理
     */
    public EquipPurchaserSupplier selectEquipPurchaserSupplierBySupplierId(Long supplierId);

    /**
     * 查询供应商管理列表
     * 
     * @param equipPurchaserSupplier 供应商管理
     * @return 供应商管理集合
     */
    public List<EquipPurchaserSupplier> selectEquipPurchaserSupplierList(EquipPurchaserSupplier equipPurchaserSupplier);

    /**
     * 新增供应商管理
     * 
     * @param equipPurchaserSupplier 供应商管理
     * @return 结果
     */
    public int insertEquipPurchaserSupplier(EquipPurchaserSupplier equipPurchaserSupplier);

    /**
     * 修改供应商管理
     * 
     * @param equipPurchaserSupplier 供应商管理
     * @return 结果
     */
    public int updateEquipPurchaserSupplier(EquipPurchaserSupplier equipPurchaserSupplier);

    /**
     * 批量删除供应商管理
     * 
     * @param supplierIds 需要删除的供应商管理主键集合
     * @return 结果
     */
    public int deleteEquipPurchaserSupplierBySupplierIds(Long[] supplierIds);

    /**
     * 删除供应商管理信息
     * 
     * @param supplierId 供应商管理主键
     * @return 结果
     */
    public int deleteEquipPurchaserSupplierBySupplierId(Long supplierId);
}
