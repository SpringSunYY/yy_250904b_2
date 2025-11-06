package com.ruoyi.pur.service;

import java.util.List;
import com.ruoyi.pur.domain.EquipPurchaseOrder;

/**
 * 采购订单Service接口
 *
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipPurchaseOrderService
{
    /**
     * 查询采购订单
     *
     * @param orderId 采购订单主键
     * @return 采购订单
     */
    public EquipPurchaseOrder selectEquipPurchaseOrderByOrderId(Long orderId);

    /**
     * 查询采购订单列表
     *
     * @param equipPurchaseOrder 采购订单
     * @return 采购订单集合
     */
    public List<EquipPurchaseOrder> selectEquipPurchaseOrderList(EquipPurchaseOrder equipPurchaseOrder);

    String generateOrderNo();

    /**
     * 新增采购订单
     *
     * @param equipPurchaseOrder 采购订单
     * @return 结果
     */
    public int insertEquipPurchaseOrder(EquipPurchaseOrder equipPurchaseOrder);

    /**
     * 修改采购订单
     *
     * @param equipPurchaseOrder 采购订单
     * @return 结果
     */
    public int updateEquipPurchaseOrder(EquipPurchaseOrder equipPurchaseOrder);

    /**
     * 批量删除采购订单
     *
     * @param orderIds 需要删除的采购订单主键集合
     * @return 结果
     */
    public int deleteEquipPurchaseOrderByOrderIds(Long[] orderIds);

    /**
     * 删除采购订单信息
     *
     * @param orderId 采购订单主键
     * @return 结果
     */
    public int deleteEquipPurchaseOrderByOrderId(Long orderId);
}
