package com.ruoyi.pur.mapper;

import java.util.List;
import com.ruoyi.pur.domain.EquipPurchaseOrder;
import com.ruoyi.pur.domain.EquipPurchaseOrderItem;

/**
 * 采购订单Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface EquipPurchaseOrderMapper 
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
     * 删除采购订单
     * 
     * @param orderId 采购订单主键
     * @return 结果
     */
    public int deleteEquipPurchaseOrderByOrderId(Long orderId);

    /**
     * 批量删除采购订单
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipPurchaseOrderByOrderIds(Long[] orderIds);

    /**
     * 批量删除设备采购订单明细
     * 
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipPurchaseOrderItemByOrderIds(Long[] orderIds);
    
    /**
     * 批量新增设备采购订单明细
     * 
     * @param equipPurchaseOrderItemList 设备采购订单明细列表
     * @return 结果
     */
    public int batchEquipPurchaseOrderItem(List<EquipPurchaseOrderItem> equipPurchaseOrderItemList);
    

    /**
     * 通过采购订单主键删除设备采购订单明细信息
     * 
     * @param orderId 采购订单ID
     * @return 结果
     */
    public int deleteEquipPurchaseOrderItemByOrderId(Long orderId);
}
