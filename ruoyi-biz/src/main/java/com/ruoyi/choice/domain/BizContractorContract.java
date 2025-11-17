package com.ruoyi.choice.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 合同与协议对象 biz_contractor_contract
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizContractorContract extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 项目合同 */
    @Excel(name = "项目合同")
    private String projectContract;

    /** 签署时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签署时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signingTime;

    /** 安全管理协议 */
    @Excel(name = "安全管理协议")
    private String safetyManagementAgreement;

    /** 签订日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签订日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date signingDate;

    /** 施工方案 */
    @Excel(name = "施工方案")
    private String constructionPlan;

    /** 审查日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;

    /** 安全作业规程 */
    @Excel(name = "安全作业规程")
    private String safeWorkProcedures;

    /** 现场处置方案 */
    @Excel(name = "现场处置方案")
    private String emergencyResponsePlan;

    /** 审查日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDate;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建者 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新者 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setChoiceId(Long choiceId) 
    {
        this.choiceId = choiceId;
    }

    public Long getChoiceId() 
    {
        return choiceId;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setContractorName(String contractorName) 
    {
        this.contractorName = contractorName;
    }

    public String getContractorName() 
    {
        return contractorName;
    }

    public void setProjectContract(String projectContract) 
    {
        this.projectContract = projectContract;
    }

    public String getProjectContract() 
    {
        return projectContract;
    }

    public void setSigningTime(Date signingTime) 
    {
        this.signingTime = signingTime;
    }

    public Date getSigningTime() 
    {
        return signingTime;
    }

    public void setSafetyManagementAgreement(String safetyManagementAgreement) 
    {
        this.safetyManagementAgreement = safetyManagementAgreement;
    }

    public String getSafetyManagementAgreement() 
    {
        return safetyManagementAgreement;
    }

    public void setSigningDate(Date signingDate) 
    {
        this.signingDate = signingDate;
    }

    public Date getSigningDate() 
    {
        return signingDate;
    }

    public void setConstructionPlan(String constructionPlan) 
    {
        this.constructionPlan = constructionPlan;
    }

    public String getConstructionPlan() 
    {
        return constructionPlan;
    }

    public void setReviewTime(Date reviewTime) 
    {
        this.reviewTime = reviewTime;
    }

    public Date getReviewTime() 
    {
        return reviewTime;
    }

    public void setSafeWorkProcedures(String safeWorkProcedures) 
    {
        this.safeWorkProcedures = safeWorkProcedures;
    }

    public String getSafeWorkProcedures() 
    {
        return safeWorkProcedures;
    }

    public void setEmergencyResponsePlan(String emergencyResponsePlan) 
    {
        this.emergencyResponsePlan = emergencyResponsePlan;
    }

    public String getEmergencyResponsePlan() 
    {
        return emergencyResponsePlan;
    }

    public void setReviewDate(Date reviewDate) 
    {
        this.reviewDate = reviewDate;
    }

    public Date getReviewDate() 
    {
        return reviewDate;
    }

    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedBy(String updatedBy) 
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() 
    {
        return updatedBy;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("projectContract", getProjectContract())
            .append("signingTime", getSigningTime())
            .append("safetyManagementAgreement", getSafetyManagementAgreement())
            .append("signingDate", getSigningDate())
            .append("constructionPlan", getConstructionPlan())
            .append("reviewTime", getReviewTime())
            .append("safeWorkProcedures", getSafeWorkProcedures())
            .append("emergencyResponsePlan", getEmergencyResponsePlan())
            .append("reviewDate", getReviewDate())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
