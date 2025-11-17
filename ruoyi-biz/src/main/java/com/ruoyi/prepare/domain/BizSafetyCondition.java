package com.ruoyi.prepare.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全条件检查对象 biz_safety_condition
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizSafetyCondition extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long conditionId;

    /** 检查编号 */
    @Excel(name = "检查编号")
    private String checkCode;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date checkTime;

    /** 检查地点 */
    @Excel(name = "检查地点")
    private String checkLocation;

    /** 工程技术措施 */
    @Excel(name = "工程技术措施")
    private String engineeringControlsNotes;

    /** 管理措施 */
    @Excel(name = "管理措施")
    private String managementMeasuresNotes;

    /** 培训教育 */
    @Excel(name = "培训教育")
    private String trainingEducationNotes;

    /** 个体防护 */
    @Excel(name = "个体防护")
    private String personalProtectionNotes;

    /** 应急处置 */
    @Excel(name = "应急处置")
    private String emergencyResponseNotes;

    /** 总体评价 */
    @Excel(name = "总体评价")
    private String overallAssessment;

    /** 已识别风险 */
    @Excel(name = "已识别风险")
    private String identifiedRisks;

    /** 整改措施 */
    @Excel(name = "整改措施")
    private String correctiveActions;

    /** 整改期限 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rectificationDeadline;

    /** 后续要求 */
    @Excel(name = "后续要求")
    private String followUpRequirements;

    /** 检查记录文件 */
    @Excel(name = "检查记录文件")
    private String checkDocument;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedAt;

    public void setConditionId(Long conditionId) 
    {
        this.conditionId = conditionId;
    }

    public Long getConditionId() 
    {
        return conditionId;
    }

    public void setCheckCode(String checkCode) 
    {
        this.checkCode = checkCode;
    }

    public String getCheckCode() 
    {
        return checkCode;
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

    public void setCheckTime(Date checkTime) 
    {
        this.checkTime = checkTime;
    }

    public Date getCheckTime() 
    {
        return checkTime;
    }

    public void setCheckLocation(String checkLocation) 
    {
        this.checkLocation = checkLocation;
    }

    public String getCheckLocation() 
    {
        return checkLocation;
    }

    public void setEngineeringControlsNotes(String engineeringControlsNotes) 
    {
        this.engineeringControlsNotes = engineeringControlsNotes;
    }

    public String getEngineeringControlsNotes() 
    {
        return engineeringControlsNotes;
    }

    public void setManagementMeasuresNotes(String managementMeasuresNotes) 
    {
        this.managementMeasuresNotes = managementMeasuresNotes;
    }

    public String getManagementMeasuresNotes() 
    {
        return managementMeasuresNotes;
    }

    public void setTrainingEducationNotes(String trainingEducationNotes) 
    {
        this.trainingEducationNotes = trainingEducationNotes;
    }

    public String getTrainingEducationNotes() 
    {
        return trainingEducationNotes;
    }

    public void setPersonalProtectionNotes(String personalProtectionNotes) 
    {
        this.personalProtectionNotes = personalProtectionNotes;
    }

    public String getPersonalProtectionNotes() 
    {
        return personalProtectionNotes;
    }

    public void setEmergencyResponseNotes(String emergencyResponseNotes) 
    {
        this.emergencyResponseNotes = emergencyResponseNotes;
    }

    public String getEmergencyResponseNotes() 
    {
        return emergencyResponseNotes;
    }

    public void setOverallAssessment(String overallAssessment) 
    {
        this.overallAssessment = overallAssessment;
    }

    public String getOverallAssessment() 
    {
        return overallAssessment;
    }

    public void setIdentifiedRisks(String identifiedRisks) 
    {
        this.identifiedRisks = identifiedRisks;
    }

    public String getIdentifiedRisks() 
    {
        return identifiedRisks;
    }

    public void setCorrectiveActions(String correctiveActions) 
    {
        this.correctiveActions = correctiveActions;
    }

    public String getCorrectiveActions() 
    {
        return correctiveActions;
    }

    public void setRectificationDeadline(Date rectificationDeadline) 
    {
        this.rectificationDeadline = rectificationDeadline;
    }

    public Date getRectificationDeadline() 
    {
        return rectificationDeadline;
    }

    public void setFollowUpRequirements(String followUpRequirements) 
    {
        this.followUpRequirements = followUpRequirements;
    }

    public String getFollowUpRequirements() 
    {
        return followUpRequirements;
    }

    public void setCheckDocument(String checkDocument) 
    {
        this.checkDocument = checkDocument;
    }

    public String getCheckDocument() 
    {
        return checkDocument;
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
            .append("conditionId", getConditionId())
            .append("checkCode", getCheckCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("checkTime", getCheckTime())
            .append("checkLocation", getCheckLocation())
            .append("engineeringControlsNotes", getEngineeringControlsNotes())
            .append("managementMeasuresNotes", getManagementMeasuresNotes())
            .append("trainingEducationNotes", getTrainingEducationNotes())
            .append("personalProtectionNotes", getPersonalProtectionNotes())
            .append("emergencyResponseNotes", getEmergencyResponseNotes())
            .append("overallAssessment", getOverallAssessment())
            .append("identifiedRisks", getIdentifiedRisks())
            .append("correctiveActions", getCorrectiveActions())
            .append("rectificationDeadline", getRectificationDeadline())
            .append("followUpRequirements", getFollowUpRequirements())
            .append("checkDocument", getCheckDocument())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
