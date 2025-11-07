package com.ruoyi.equip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 设备安装验收对象 equip_device
 *
 * @author laogao
 * @date 2025-11-07
 */
public class EquipDevice extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long deviceId;

    /**
     * 验收单号
     */
    @Excel(name = "验收单号")
    private String deviceNo;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
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
     * 设备位号
     */
    @Excel(name = "设备位号")
    private String equipNo;

    /**
     * 设备型号
     */
    @Excel(name = "设备型号")
    private String equipModel;

    /**
     * 入厂日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inDate;

    /**
     * 安装单位
     */
    @Excel(name = "安装单位")
    private String instUnit;

    /**
     * 安装负责人
     */
    @Excel(name = "安装负责人")
    private String instPerson;

    /**
     * 联系电话
     */
    @Excel(name = "联系电话")
    private String instPhone;

    /**
     * 安装日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date instDate;

    /**
     * 安装位置
     */
    @Excel(name = "安装位置")
    private String instLocation;

    /**
     * 安装调试单位
     */
    @Excel(name = "安装调试单位")
    private String instAdjustUnit;

    /**
     * 相关附件
     */
    @Excel(name = "相关附件")
    private String appendix;

    /**
     * 验收结果
     */
    @Excel(name = "验收结果")
    private String acceptanceResult;

    /**
     * 验收意见
     */
    @Excel(name = "验收意见")
    private String acceptanceOpinion;

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
     * 安装验收明细信息
     */
    private List<EquipDeviceItem> equipDeviceItemList;

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

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

    public void setEquipNo(String equipNo) {
        this.equipNo = equipNo;
    }

    public String getEquipNo() {
        return equipNo;
    }

    public void setEquipModel(String equipModel) {
        this.equipModel = equipModel;
    }

    public String getEquipModel() {
        return equipModel;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInstUnit(String instUnit) {
        this.instUnit = instUnit;
    }

    public String getInstUnit() {
        return instUnit;
    }

    public void setInstPerson(String instPerson) {
        this.instPerson = instPerson;
    }

    public String getInstPerson() {
        return instPerson;
    }

    public void setInstPhone(String instPhone) {
        this.instPhone = instPhone;
    }

    public String getInstPhone() {
        return instPhone;
    }

    public void setInstDate(Date instDate) {
        this.instDate = instDate;
    }

    public Date getInstDate() {
        return instDate;
    }

    public void setInstLocation(String instLocation) {
        this.instLocation = instLocation;
    }

    public String getInstLocation() {
        return instLocation;
    }

    public void setInstAdjustUnit(String instAdjustUnit) {
        this.instAdjustUnit = instAdjustUnit;
    }

    public String getInstAdjustUnit() {
        return instAdjustUnit;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setAcceptanceResult(String acceptanceResult) {
        this.acceptanceResult = acceptanceResult;
    }

    public String getAcceptanceResult() {
        return acceptanceResult;
    }

    public void setAcceptanceOpinion(String acceptanceOpinion) {
        this.acceptanceOpinion = acceptanceOpinion;
    }

    public String getAcceptanceOpinion() {
        return acceptanceOpinion;
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

    public List<EquipDeviceItem> getEquipDeviceItemList() {
        return equipDeviceItemList;
    }

    public void setEquipDeviceItemList(List<EquipDeviceItem> equipDeviceItemList) {
        this.equipDeviceItemList = equipDeviceItemList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("deviceId", getDeviceId())
                .append("deviceNo", getDeviceNo())
                .append("orderId", getOrderId())
                .append("orderNo", getOrderNo())
                .append("equipName", getEquipName())
                .append("equipNo", getEquipNo())
                .append("equipModel", getEquipModel())
                .append("inDate", getInDate())
                .append("instUnit", getInstUnit())
                .append("instPerson", getInstPerson())
                .append("instPhone", getInstPhone())
                .append("instDate", getInstDate())
                .append("instLocation", getInstLocation())
                .append("instAdjustUnit", getInstAdjustUnit())
                .append("appendix", getAppendix())
                .append("acceptanceResult", getAcceptanceResult())
                .append("acceptanceOpinion", getAcceptanceOpinion())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("applyUserId", getApplyUserId())
                .append("applyUserName", getApplyUserName())
                .append("taskId", getTaskId())
                .append("processInstanceId", getProcessInstanceId())
                .append("deployId", getDeployId())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("equipDeviceItemList", getEquipDeviceItemList())
                .toString();
    }
}
