package com.ruoyi.prepare.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 协调会议对象 biz_coordination_meeting
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizCoordinationMeeting extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long meetingId;

    /** 会议编号 */
    @Excel(name = "会议编号")
    private String meetingCode;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 会议主题 */
    @Excel(name = "会议主题")
    private String meetingTopic;

    /** 会议时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会议时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date meetingTime;

    /** 会议地点 */
    @Excel(name = "会议地点")
    private String meetingLocation;

    /** 会议时长（小时） */
    @Excel(name = "会议时长", readConverterExp = "小=时")
    private BigDecimal meetingDuration;

    /** 会议文件 */
    @Excel(name = "会议文件")
    private String meetingDocument;

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

    public void setMeetingId(Long meetingId) 
    {
        this.meetingId = meetingId;
    }

    public Long getMeetingId() 
    {
        return meetingId;
    }

    public void setMeetingCode(String meetingCode) 
    {
        this.meetingCode = meetingCode;
    }

    public String getMeetingCode() 
    {
        return meetingCode;
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

    public void setMeetingTopic(String meetingTopic) 
    {
        this.meetingTopic = meetingTopic;
    }

    public String getMeetingTopic() 
    {
        return meetingTopic;
    }

    public void setMeetingTime(Date meetingTime) 
    {
        this.meetingTime = meetingTime;
    }

    public Date getMeetingTime() 
    {
        return meetingTime;
    }

    public void setMeetingLocation(String meetingLocation) 
    {
        this.meetingLocation = meetingLocation;
    }

    public String getMeetingLocation() 
    {
        return meetingLocation;
    }

    public void setMeetingDuration(BigDecimal meetingDuration) 
    {
        this.meetingDuration = meetingDuration;
    }

    public BigDecimal getMeetingDuration() 
    {
        return meetingDuration;
    }

    public void setMeetingDocument(String meetingDocument) 
    {
        this.meetingDocument = meetingDocument;
    }

    public String getMeetingDocument() 
    {
        return meetingDocument;
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
            .append("meetingId", getMeetingId())
            .append("meetingCode", getMeetingCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("meetingTopic", getMeetingTopic())
            .append("meetingTime", getMeetingTime())
            .append("meetingLocation", getMeetingLocation())
            .append("meetingDuration", getMeetingDuration())
            .append("meetingDocument", getMeetingDocument())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
