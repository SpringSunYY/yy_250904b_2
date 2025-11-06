package com.ruoyi.equip.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 设备报废管理对象 equip_scrap_ledger
 *
 * @author laogao
 * @date 2025-10-25
 */
public class EquipScrapLedger extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 报废ID
     */
    @Excel(name = "报废ID")
    private Long scrapId;

    /**
     * 报废编号
     */
    @Excel(name = "报废编号")
    private String scrapNo;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipName;

    /**
     * 设备位号
     */
    @Excel(name = "设备位号")
    private String equCode;

    /**
     * 报废原因
     */
    @Excel(name = "报废原因")
    private String scrapReason;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applicationTime;

    /**
     * 申请人ID
     */
    @Excel(name = "申请人ID")
    private Long applicantId;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    private String applicantName;

    /**
     * 技术鉴定意见
     */
    @Excel(name = "技术鉴定意见")
    private String techAssessment;

    /**
     * 财务审核意见
     */
    @Excel(name = "财务审核意见")
    private String financialReview;

    /**
     * 处理方式
     */
    @Excel(name = "处理方式")
    private Long scrapMethod;

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

    public void setScrapId(Long scrapId) {
        this.scrapId = scrapId;
    }

    public Long getScrapId() {
        return scrapId;
    }

    public void setScrapNo(String scrapNo) {
        this.scrapNo = scrapNo;
    }

    public String getScrapNo() {
        return scrapNo;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquCode(String equCode) {
        this.equCode = equCode;
    }

    public String getEquCode() {
        return equCode;
    }

    public void setScrapReason(String scrapReason) {
        this.scrapReason = scrapReason;
    }

    public String getScrapReason() {
        return scrapReason;
    }

    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    public Date getApplicationTime() {
        return applicationTime;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setTechAssessment(String techAssessment) {
        this.techAssessment = techAssessment;
    }

    public String getTechAssessment() {
        return techAssessment;
    }

    public void setFinancialReview(String financialReview) {
        this.financialReview = financialReview;
    }

    public String getFinancialReview() {
        return financialReview;
    }

    public void setScrapMethod(Long scrapMethod) {
        this.scrapMethod = scrapMethod;
    }

    public Long getScrapMethod() {
        return scrapMethod;
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
                .append("scrapId", getScrapId())
                .append("scrapNo", getScrapNo())
                .append("equipmentId", getEquipmentId())
                .append("equipName", getEquipName())
                .append("equCode", getEquCode())
                .append("scrapReason", getScrapReason())
                .append("applicationTime", getApplicationTime())
                .append("applicantId", getApplicantId())
                .append("applicantName", getApplicantName())
                .append("techAssessment", getTechAssessment())
                .append("financialReview", getFinancialReview())
                .append("scrapMethod", getScrapMethod())
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
