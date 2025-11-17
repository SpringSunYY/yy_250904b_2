package com.ruoyi.contractor.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 承包商资格审查对象 biz_qualification_audit
 *
 * @author Laogao
 * @date 2025-11-13
 */
public class BizQualificationAudit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @Excel(name = "主键id")
    private Long auditId;

    /**
     * 审核编号
     */
    @Excel(name = "审核编号")
    private String auditNumber;

    /**
     * 承包商名称
     */
    @Excel(name = "承包商名称")
    private String companyName;

    /**
     * 公司规模
     */
    @Excel(name = "公司规模")
    private Long companySize;

    /**
     * 统一社会信用代码
     */
    @Excel(name = "统一社会信用代码")
    private String companyShortName;

    /**
     * 注册资本(万元)
     */
    @Excel(name = "注册资本(万元)")
    private String registeredCapital;

    /**
     * 法定代表人
     */
    @Excel(name = "法定代表人")
    private String legalRepresentative;

    /**
     * 经营范围
     */
    @Excel(name = "经营范围")
    private String businessScope;

    /**
     * 经营地址
     */
    @Excel(name = "经营地址")
    private String businessAddress;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "成立日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date establishmentDate;

    /**
     * 邮箱
     */
    @Excel(name = "邮箱")
    private String email;

    /**
     * 审查类型
     */
    @Excel(name = "审查类型")
    private String auditType;

    /**
     * 申请日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyDate;

    /**
     * 审核结果
     */
    @Excel(name = "审核结果")
    private String auditResult;

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

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditNumber(String auditNumber) {
        this.auditNumber = auditNumber;
    }

    public String getAuditNumber() {
        return auditNumber;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanySize(Long companySize) {
        this.companySize = companySize;
    }

    public Long getCompanySize() {
        return companySize;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setEstablishmentDate(Date establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public Date getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setAuditResult(String auditResult) {
        this.auditResult = auditResult;
    }

    public String getAuditResult() {
        return auditResult;
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
                .append("auditId", getAuditId())
                .append("auditNumber", getAuditNumber())
                .append("companyName", getCompanyName())
                .append("companySize", getCompanySize())
                .append("companyShortName", getCompanyShortName())
                .append("registeredCapital", getRegisteredCapital())
                .append("legalRepresentative", getLegalRepresentative())
                .append("businessScope", getBusinessScope())
                .append("businessAddress", getBusinessAddress())
                .append("establishmentDate", getEstablishmentDate())
                .append("email", getEmail())
                .append("auditType", getAuditType())
                .append("applyDate", getApplyDate())
                .append("auditResult", getAuditResult())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("applyUserId", getApplyUserId())
                .append("applyUserName", getApplyUserName())
                .append("taskId", getTaskId())
                .append("processInstanceId", getProcessInstanceId())
                .append("deployId", getDeployId())
                .toString();
    }
}
