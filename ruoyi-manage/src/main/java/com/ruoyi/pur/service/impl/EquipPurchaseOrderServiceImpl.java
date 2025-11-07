package com.ruoyi.pur.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.pur.domain.EquipPurchaseOrder;
import com.ruoyi.pur.domain.EquipPurchaseOrderItem;
import com.ruoyi.pur.domain.EquipPurchaserSupplier;
import com.ruoyi.pur.mapper.EquipPurchaseOrderMapper;
import com.ruoyi.pur.service.IEquipPurchaseOrderService;
import com.ruoyi.pur.service.IEquipPurchaserSupplierService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购订单Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipPurchaseOrderServiceImpl implements IEquipPurchaseOrderService {
    @Autowired
    private EquipPurchaseOrderMapper equipPurchaseOrderMapper;

    @Resource
    private IEquipPurchaserSupplierService equipPurchaserSupplierService;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 查询采购订单
     *
     * @param orderId 采购订单主键
     * @return 采购订单
     */
    @Override
    public EquipPurchaseOrder selectEquipPurchaseOrderByOrderId(Long orderId) {
        return equipPurchaseOrderMapper.selectEquipPurchaseOrderByOrderId(orderId);
    }

    /**
     * 查询采购订单列表
     *
     * @param equipPurchaseOrder 采购订单
     * @return 采购订单
     */
    @Override
    public List<EquipPurchaseOrder> selectEquipPurchaseOrderList(EquipPurchaseOrder equipPurchaseOrder) {
        List<EquipPurchaseOrder> equipPurchaseOrders = equipPurchaseOrderMapper.selectEquipPurchaseOrderList(equipPurchaseOrder);
        for (EquipPurchaseOrder purchaseOrder : equipPurchaseOrders) {
            if (StringUtils.isNotNull(purchaseOrder.getAuditUserId())) {
                SysUser sysUser = sysUserService.selectUserById(purchaseOrder.getAuditUserId());
                if (StringUtils.isNotNull(sysUser)) {
                    purchaseOrder.setAuditUserName(sysUser.getUserName());
                }
            }
        }
        return equipPurchaseOrders;
    }

    /**
     * 生成订单编号
     *
     * @return 订单编号
     */
    @Override
    public String generateOrderNo() {
        // 生成订单编号：PO + 年月日 + 4位序号（例如：PO202509240001）
        String date = DateUtils.dateTimeNow("yyyyMMdd");
        // 查询当天最大订单编号
        EquipPurchaseOrder order = new EquipPurchaseOrder();
        String prefix = "PO" + date;
        order.setOrderNo(prefix);
        List<EquipPurchaseOrder> list = equipPurchaseOrderMapper.selectEquipPurchaseOrderList(order);

        int maxSeq = 0;
        if (list != null && !list.isEmpty()) {
            for (EquipPurchaseOrder item : list) {
                String orderNo = item.getOrderNo();
                if (orderNo != null && orderNo.startsWith(prefix)) {
                    String seqStr = orderNo.substring(10); // 提取序号部分
                    try {
                        int seq = Integer.parseInt(seqStr);
                        if (seq > maxSeq) {
                            maxSeq = seq;
                        }
                    } catch (NumberFormatException e) {
                        // 忽略解析错误
                    }
                }
            }
        }

        // 序号加1，格式化为4位数字
        maxSeq++;
        return prefix + String.format("%04d", maxSeq);
    }

    /**
     * 新增采购订单
     *
     * @param equipPurchaseOrder 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int insertEquipPurchaseOrder(EquipPurchaseOrder equipPurchaseOrder) {
        // 自动生成订单编号
        if (StringUtils.isEmpty(equipPurchaseOrder.getOrderNo())) {
            equipPurchaseOrder.setOrderNo(generateOrderNo());
        }
        if (StringUtils.isNotNull(equipPurchaseOrder.getSupplierId())) {
            EquipPurchaserSupplier equipPurchaserSupplier = equipPurchaserSupplierService.selectEquipPurchaserSupplierBySupplierId(equipPurchaseOrder.getSupplierId());
            if (StringUtils.isNotNull(equipPurchaserSupplier) && StringUtils.isNotEmpty(equipPurchaserSupplier.getSupplierName())) {
                equipPurchaseOrder.setSupplierName(equipPurchaserSupplier.getSupplierName());
            }
        }
        equipPurchaseOrder.setCreateTime(DateUtils.getNowDate());
        int rows = equipPurchaseOrderMapper.insertEquipPurchaseOrder(equipPurchaseOrder);
        insertEquipPurchaseOrderItem(equipPurchaseOrder);
        return rows;
    }

    /**
     * 修改采购订单
     *
     * @param equipPurchaseOrder 采购订单
     * @return 结果
     */
    @Transactional
    @Override
    public int updateEquipPurchaseOrder(EquipPurchaseOrder equipPurchaseOrder) {
        equipPurchaseOrder.setUpdateTime(DateUtils.getNowDate());
        equipPurchaseOrderMapper.deleteEquipPurchaseOrderItemByOrderId(equipPurchaseOrder.getOrderId());
        insertEquipPurchaseOrderItem(equipPurchaseOrder);
        return equipPurchaseOrderMapper.updateEquipPurchaseOrder(equipPurchaseOrder);
    }

    /**
     * 批量删除采购订单
     *
     * @param orderIds 需要删除的采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipPurchaseOrderByOrderIds(Long[] orderIds) {
        equipPurchaseOrderMapper.deleteEquipPurchaseOrderItemByOrderIds(orderIds);
        return equipPurchaseOrderMapper.deleteEquipPurchaseOrderByOrderIds(orderIds);
    }

    /**
     * 删除采购订单信息
     *
     * @param orderId 采购订单主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipPurchaseOrderByOrderId(Long orderId) {
        equipPurchaseOrderMapper.deleteEquipPurchaseOrderItemByOrderId(orderId);
        return equipPurchaseOrderMapper.deleteEquipPurchaseOrderByOrderId(orderId);
    }

    /**
     * 新增设备采购订单明细信息
     *
     * @param equipPurchaseOrder 采购订单对象
     */
    public void insertEquipPurchaseOrderItem(EquipPurchaseOrder equipPurchaseOrder) {
        List<EquipPurchaseOrderItem> equipPurchaseOrderItemList = equipPurchaseOrder.getEquipPurchaseOrderItemList();
        Long orderId = equipPurchaseOrder.getOrderId();
        if (StringUtils.isNotNull(equipPurchaseOrderItemList)) {
            List<EquipPurchaseOrderItem> list = new ArrayList<EquipPurchaseOrderItem>();
            for (EquipPurchaseOrderItem equipPurchaseOrderItem : equipPurchaseOrderItemList) {
                equipPurchaseOrderItem.setOrderId(orderId);
                list.add(equipPurchaseOrderItem);
            }
            if (list.size() > 0) {
                equipPurchaseOrderMapper.batchEquipPurchaseOrderItem(list);
            }
        }
    }
}
