package com.ruoyi.equip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 设备缺陷管理对象 equip_defect
 *
 * @author laogao
 * @date 2025-10-25
 */
public class EquipDefect extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 缺陷ID
     */
    @Excel(name = "缺陷ID")
    private Long defectId;

    /**
     * 缺陷编号
     */
    @Excel(name = "缺陷编号")
    private String defectNo;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipmentName;

    /**
     * 设备位号
     */
    @Excel(name = "设备位号")
    private String equipmentCode;

    /**
     * 设备管理级别
     */
    @Excel(name = "设备管理级别")
    private String equipLevels;

    /**
     * 安装位置
     */
    @Excel(name = "安装位置")
    private String installLocation;

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
    private String reporterEmpNo;

    /**
     * 报告人
     */
    @Excel(name = "报告人")
    private String reporterName;

    /**
     * 缺陷描述
     */
    @Excel(name = "缺陷描述")
    private String defectContent;

    /**
     * 缺陷照片
     */
    @Excel(name = "缺陷照片")
    private String appendix;

    /**
     * 缺陷等级
     */
    @Excel(name = "缺陷等级")
    private String defectLevel;

    /**
     * 是否重复性缺陷
     */
    @Excel(name = "是否重复性缺陷")
    private String isRepeated;

    /**
     * 处理措施
     */
    @Excel(name = "处理措施")
    private String handlingMeasures;

    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handlingTime;

    /**
     * 验收意见
     */
    @Excel(name = "验收意见")
    private String acceptanceOpinion;

    /**
     * 复核人id
     */
    @Excel(name = "复核人id")
    private String reviewerEmpNo;

    /**
     * 复核人
     */
    @Excel(name = "复核人")
    private String reviewerName;

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

    public void setDefectId(Long defectId) {
        this.defectId = defectId;
    }

    public Long getDefectId() {
        return defectId;
    }

    public void setDefectNo(String defectNo) {
        this.defectNo = defectNo;
    }

    public String getDefectNo() {
        return defectNo;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipLevels(String equipLevels) {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() {
        return equipLevels;
    }

    public void setInstallLocation(String installLocation) {
        this.installLocation = installLocation;
    }

    public String getInstallLocation() {
        return installLocation;
    }

    public void setDiscoveryTime(Date discoveryTime) {
        this.discoveryTime = discoveryTime;
    }

    public Date getDiscoveryTime() {
        return discoveryTime;
    }

    public void setReporterEmpNo(String reporterEmpNo) {
        this.reporterEmpNo = reporterEmpNo;
    }

    public String getReporterEmpNo() {
        return reporterEmpNo;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setDefectContent(String defectContent) {
        this.defectContent = defectContent;
    }

    public String getDefectContent() {
        return defectContent;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setDefectLevel(String defectLevel) {
        this.defectLevel = defectLevel;
    }

    public String getDefectLevel() {
        return defectLevel;
    }

    public void setIsRepeated(String isRepeated) {
        this.isRepeated = isRepeated;
    }

    public String getIsRepeated() {
        return isRepeated;
    }

    public void setHandlingMeasures(String handlingMeasures) {
        this.handlingMeasures = handlingMeasures;
    }

    public String getHandlingMeasures() {
        return handlingMeasures;
    }

    public void setHandlingTime(Date handlingTime) {
        this.handlingTime = handlingTime;
    }

    public Date getHandlingTime() {
        return handlingTime;
    }

    public void setAcceptanceOpinion(String acceptanceOpinion) {
        this.acceptanceOpinion = acceptanceOpinion;
    }

    public String getAcceptanceOpinion() {
        return acceptanceOpinion;
    }

    public void setReviewerEmpNo(String reviewerEmpNo) {
        this.reviewerEmpNo = reviewerEmpNo;
    }

    public String getReviewerEmpNo() {
        return reviewerEmpNo;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerName() {
        return reviewerName;
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
                .append("defectId", getDefectId())
                .append("defectNo", getDefectNo())
                .append("equipmentId", getEquipmentId())
                .append("equipmentName", getEquipmentName())
                .append("equipmentCode", getEquipmentCode())
                .append("equipLevels", getEquipLevels())
                .append("installLocation", getInstallLocation())
                .append("discoveryTime", getDiscoveryTime())
                .append("reporterEmpNo", getReporterEmpNo())
                .append("reporterName", getReporterName())
                .append("defectContent", getDefectContent())
                .append("appendix", getAppendix())
                .append("defectLevel", getDefectLevel())
                .append("isRepeated", getIsRepeated())
                .append("handlingMeasures", getHandlingMeasures())
                .append("handlingTime", getHandlingTime())
                .append("acceptanceOpinion", getAcceptanceOpinion())
                .append("reviewerEmpNo", getReviewerEmpNo())
                .append("reviewerName", getReviewerName())
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
