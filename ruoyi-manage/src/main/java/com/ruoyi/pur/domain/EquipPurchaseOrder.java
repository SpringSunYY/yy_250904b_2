package com.ruoyi.pur.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 采购订单对象 equip_purchase_order
 *
 * @author laogao
 * @date 2025-11-06
 */
public class EquipPurchaseOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long orderId;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    private String orderNo;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipName;

    /**
     * 供应商ID
     */
    @Excel(name = "供应商ID")
    private Long supplierId;

    /**
     * 供应商
     */
    @Excel(name = "供应商")
    private String supplierName;

    /**
     * 申请部门ID
     */
    @Excel(name = "申请部门ID")
    private Long deptId;

    /**
     * 申请部门
     */
    @Excel(name = "申请部门")
    private String deptName;

    /**
     * 申请人ID
     */
    @Excel(name = "申请人ID")
    private Long applyUserId;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    private String applyUserName;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /**
     * 预计到货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "预计到货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arriveTime;

    /**
     * 预算单价
     */
    @Excel(name = "预算单价")
    private BigDecimal unitAmount;

    /**
     * 预算金额
     */
    @Excel(name = "预算金额")
    private BigDecimal budgetAmount;

    /**
     * 采购原因
     */
    @Excel(name = "采购原因")
    private String purchasingReason;

    /**
     * 相关附件
     */
    @Excel(name = "相关附件")
    private String appendix;

    /**
     * 流程状态
     */
    @Excel(name = "流程状态")
    private String processStatus;

    /**
     * 任务ID
     */
    @Excel(name = "任务ID")
    private String taskId;

    /**
     * 流程实例ID
     */
    @Excel(name = "流程实例ID")
    private String processInstanceId;

    /**
     * 流程自定义ID
     */
    @Excel(name = "流程自定义ID")
    private String deployId;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private String orderStatus;

    /**
     * 设备采购订单明细信息
     */
    private List<EquipPurchaseOrderItem> equipPurchaseOrderItemList;

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public void setPurchasingReason(String purchasingReason) {
        this.purchasingReason = purchasingReason;
    }

    public String getPurchasingReason() {
        return purchasingReason;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getDeployId() {
        return deployId;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public List<EquipPurchaseOrderItem> getEquipPurchaseOrderItemList() {
        return equipPurchaseOrderItemList;
    }

    public void setEquipPurchaseOrderItemList(List<EquipPurchaseOrderItem> equipPurchaseOrderItemList) {
        this.equipPurchaseOrderItemList = equipPurchaseOrderItemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("orderId", getOrderId())
                .append("orderNo", getOrderNo())
                .append("equipName", getEquipName())
                .append("supplierId", getSupplierId())
                .append("supplierName", getSupplierName())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("applyUserId", getApplyUserId())
                .append("applyUserName", getApplyUserName())
                .append("applyTime", getApplyTime())
                .append("arriveTime", getArriveTime())
                .append("unitAmount", getUnitAmount())
                .append("budgetAmount", getBudgetAmount())
                .append("purchasingReason", getPurchasingReason())
                .append("appendix", getAppendix())
                .append("processStatus", getProcessStatus())
                .append("taskId", getTaskId())
                .append("processInstanceId", getProcessInstanceId())
                .append("deployId", getDeployId())
                .append("orderStatus", getOrderStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("equipPurchaseOrderItemList", getEquipPurchaseOrderItemList())
                .toString();
    }
}
