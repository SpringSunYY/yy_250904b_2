package com.ruoyi.prepare.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全技术交底对象 biz_safety_briefing
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizSafetyBriefing extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 交底编号 */
    @Excel(name = "交底编号")
    private String briefingCode;

    /** 承包商选择 */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 交底类型 */
    @Excel(name = "交底类型")
    private String briefingType;

    /** 交底时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交底时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date briefingTime;

    /** 交底主题 */
    @Excel(name = "交底主题")
    private String briefingTopic;

    /** 交底人 */
    @Excel(name = "交底人")
    private String informer;

    /** 交底时长（小时） */
    @Excel(name = "交底时长", readConverterExp = "小=时")
    private BigDecimal briefingDuration;

    /** 交底会议纪要 */
    @Excel(name = "交底会议纪要")
    private String meetingSummary;

    /** 交底文件 */
    @Excel(name = "交底文件")
    private String briefingDocument;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setBriefingCode(String briefingCode) 
    {
        this.briefingCode = briefingCode;
    }

    public String getBriefingCode() 
    {
        return briefingCode;
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

    public void setBriefingType(String briefingType) 
    {
        this.briefingType = briefingType;
    }

    public String getBriefingType() 
    {
        return briefingType;
    }

    public void setBriefingTime(Date briefingTime) 
    {
        this.briefingTime = briefingTime;
    }

    public Date getBriefingTime() 
    {
        return briefingTime;
    }

    public void setBriefingTopic(String briefingTopic) 
    {
        this.briefingTopic = briefingTopic;
    }

    public String getBriefingTopic() 
    {
        return briefingTopic;
    }

    public void setInformer(String informer) 
    {
        this.informer = informer;
    }

    public String getInformer() 
    {
        return informer;
    }

    public void setBriefingDuration(BigDecimal briefingDuration) 
    {
        this.briefingDuration = briefingDuration;
    }

    public BigDecimal getBriefingDuration() 
    {
        return briefingDuration;
    }

    public void setMeetingSummary(String meetingSummary) 
    {
        this.meetingSummary = meetingSummary;
    }

    public String getMeetingSummary() 
    {
        return meetingSummary;
    }

    public void setBriefingDocument(String briefingDocument) 
    {
        this.briefingDocument = briefingDocument;
    }

    public String getBriefingDocument() 
    {
        return briefingDocument;
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
            .append("briefingCode", getBriefingCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("briefingType", getBriefingType())
            .append("briefingTime", getBriefingTime())
            .append("briefingTopic", getBriefingTopic())
            .append("informer", getInformer())
            .append("briefingDuration", getBriefingDuration())
            .append("meetingSummary", getMeetingSummary())
            .append("briefingDocument", getBriefingDocument())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
