package com.ruoyi.seal.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 泄露处置对象 equip_seal_management
 *
 * @author laogao
 * @date 2025-10-24
 */
public class EquipSealManagement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Excel(name = "主键ID")
    private Long manageId;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
    private Long equipId;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipName;

    /**
     * 设备位号
     */
    @Excel(name = "设备位号")
    private String equipCode;

    /**
     * 设备管理级别
     */
    @Excel(name = "设备管理级别")
    private String equipLevels;

    /**
     * 泄漏部位
     */
    @Excel(name = "泄漏部位")
    private String leakLocation;

    /**
     * 发现时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发现时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date discoveryTime;

    /**
     * 报告人id
     */
    @Excel(name = "报告人id")
    private String reporterId;

    /**
     * 报告人
     */
    @Excel(name = "报告人")
    private String reporterName;

    /**
     * 泄漏严重程度
     */
    @Excel(name = "泄漏严重程度")
    private String severity;

    /**
     * 泄漏介质
     */
    @Excel(name = "泄漏介质")
    private String leakedMedium;

    /**
     * 现场描述
     */
    @Excel(name = "现场描述")
    private String description;

    /**
     * 现场照片
     */
    @Excel(name = "现场照片")
    private String appendix;

    /**
     * 处理措施
     */
    @Excel(name = "处理措施")
    private String handlingMeasures;

    /**
     * 处理人id
     */
    @Excel(name = "处理人id")
    private String handlerId;

    /**
     * 处理人
     */
    @Excel(name = "处理人")
    private String handlerName;

    /**
     * 完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

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

    public void setManageId(Long manageId) {
        this.manageId = manageId;
    }

    public Long getManageId() {
        return manageId;
    }

    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    public Long getEquipId() {
        return equipId;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipLevels(String equipLevels) {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() {
        return equipLevels;
    }

    public void setLeakLocation(String leakLocation) {
        this.leakLocation = leakLocation;
    }

    public String getLeakLocation() {
        return leakLocation;
    }

    public void setDiscoveryTime(Date discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public Date getDiscoveryTime() {
        return discoveryTime;
    }

    public void setReporterId(String reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterId() {
        return reporterId;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getSeverity() {
        return severity;
    }

    public void setLeakedMedium(String leakedMedium) {
        this.leakedMedium = leakedMedium;
    }

    public String getLeakedMedium() {
        return leakedMedium;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setHandlingMeasures(String handlingMeasures) {
        this.handlingMeasures = handlingMeasures;
    }

    public String getHandlingMeasures() {
        return handlingMeasures;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() {
        return finishTime;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("manageId", getManageId())
                .append("equipId", getEquipId())
                .append("equipName", getEquipName())
                .append("equipCode", getEquipCode())
                .append("equipLevels", getEquipLevels())
                .append("leakLocation", getLeakLocation())
                .append("discoveryTime", getDiscoveryTime())
                .append("reporterId", getReporterId())
                .append("reporterName", getReporterName())
                .append("severity", getSeverity())
                .append("leakedMedium", getLeakedMedium())
                .append("description", getDescription())
                .append("appendix", getAppendix())
                .append("handlingMeasures", getHandlingMeasures())
                .append("handlerId", getHandlerId())
                .append("handlerName", getHandlerName())
                .append("finishTime", getFinishTime())
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
                .toString();
    }
}
