package com.ruoyi.process.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目变更管理对象 biz_project_change
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizProjectChange extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long changeId;

    /** 变更记录编号 */
    @Excel(name = "变更记录编号")
    private String changeNo;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 变更申请日期 */
    @Excel(name = "变更申请日期")
    private String changeTime;

    /** 变更类型 */
    @Excel(name = "变更类型")
    private String changeType;

    /** 变更原因 */
    @Excel(name = "变更原因")
    private String changeReason;

    /** 变更记录 */
    @Excel(name = "变更记录")
    private String changeRecord;

    /** 安全管理协议是否修改 */
    @Excel(name = "安全管理协议是否修改", width = 30)
    private String safetyProtocolUpdated;

    /** 安全管理协议修订文本 */
    @Excel(name = "安全管理协议修订文本")
    private String safetyProtocolAttachment;

    /** 施工方案是否修改 */
    @Excel(name = "施工方案是否修改", width = 30)
    private String constructionPlanUpdated;

    /** 施工方案修订文本 */
    @Excel(name = "施工方案修订文本")
    private String constructionPlanAttachment;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdTime;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedTime;

    public void setChangeId(Long changeId) 
    {
        this.changeId = changeId;
    }

    public Long getChangeId() 
    {
        return changeId;
    }

    public void setChangeNo(String changeNo) 
    {
        this.changeNo = changeNo;
    }

    public String getChangeNo() 
    {
        return changeNo;
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

    public void setChangeTime(String changeTime) 
    {
        this.changeTime = changeTime;
    }

    public String getChangeTime() 
    {
        return changeTime;
    }

    public void setChangeType(String changeType) 
    {
        this.changeType = changeType;
    }

    public String getChangeType() 
    {
        return changeType;
    }

    public void setChangeReason(String changeReason) 
    {
        this.changeReason = changeReason;
    }

    public String getChangeReason() 
    {
        return changeReason;
    }

    public void setChangeRecord(String changeRecord) 
    {
        this.changeRecord = changeRecord;
    }

    public String getChangeRecord() 
    {
        return changeRecord;
    }

    public void setSafetyProtocolUpdated(String safetyProtocolUpdated) 
    {
        this.safetyProtocolUpdated = safetyProtocolUpdated;
    }

    public String getSafetyProtocolUpdated() 
    {
        return safetyProtocolUpdated;
    }

    public void setSafetyProtocolAttachment(String safetyProtocolAttachment) 
    {
        this.safetyProtocolAttachment = safetyProtocolAttachment;
    }

    public String getSafetyProtocolAttachment() 
    {
        return safetyProtocolAttachment;
    }

    public void setConstructionPlanUpdated(String constructionPlanUpdated) 
    {
        this.constructionPlanUpdated = constructionPlanUpdated;
    }

    public String getConstructionPlanUpdated() 
    {
        return constructionPlanUpdated;
    }

    public void setConstructionPlanAttachment(String constructionPlanAttachment) 
    {
        this.constructionPlanAttachment = constructionPlanAttachment;
    }

    public String getConstructionPlanAttachment() 
    {
        return constructionPlanAttachment;
    }

    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }

    public void setCreatedTime(Date createdTime) 
    {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() 
    {
        return createdTime;
    }

    public void setUpdatedBy(String updatedBy) 
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() 
    {
        return updatedBy;
    }

    public void setUpdatedTime(Date updatedTime) 
    {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() 
    {
        return updatedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("changeId", getChangeId())
            .append("changeNo", getChangeNo())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("changeTime", getChangeTime())
            .append("changeType", getChangeType())
            .append("changeReason", getChangeReason())
            .append("changeRecord", getChangeRecord())
            .append("safetyProtocolUpdated", getSafetyProtocolUpdated())
            .append("safetyProtocolAttachment", getSafetyProtocolAttachment())
            .append("constructionPlanUpdated", getConstructionPlanUpdated())
            .append("constructionPlanAttachment", getConstructionPlanAttachment())
            .append("remark", getRemark())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
