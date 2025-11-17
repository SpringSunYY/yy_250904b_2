package com.ruoyi.process.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全日志对象 biz_safety_logs
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizSafetyLogs extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long logId;

    /** 安全日志编号 */
    @Excel(name = "安全日志编号")
    private String logCode;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 安全日志日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安全日志日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logDate;

    /** 今日工作内容 */
    @Excel(name = "今日工作内容")
    private String todayWorkContent;

    /** 公司现场负责人 */
    @Excel(name = "公司现场负责人")
    private String companySiteManager = "";

    /** 承包商作业人员数量 */
    @Excel(name = "承包商作业人员数量")
    private Long workerCount;

    /** 人员是否经过审查 */
    @Excel(name = "人员是否经过审查")
    private String workersReviewed;

    /** 作业内容是否变化 */
    @Excel(name = "作业内容是否变化")
    private String workContentChanged;

    /** 作业地点是否变化 */
    @Excel(name = "作业地点是否变化")
    private String workLocationChanged;

    /** 是否涉及特殊作业 */
    @Excel(name = "是否涉及特殊作业")
    private String hasSpecialWork;

    /** 特殊作业类型 */
    private String specialWorkType;

    /** 特殊作业内容 */
    private String specialWorkContent;

    /** 有无事故发生 */
    @Excel(name = "有无事故发生")
    private String hasAccident;

    /** 事故简要情况 */
    @Excel(name = "事故简要情况")
    private String accidentBrief;

    /** 与承包商的沟通协调 */
    @Excel(name = "与承包商的沟通协调")
    private String communicationCoordination;

    /** 相关附件 */
    @Excel(name = "相关附件")
    private String attachments;

    /** 备注 */
    @Excel(name = "备注")
    private String notes;

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

    public void setLogId(Long logId) 
    {
        this.logId = logId;
    }

    public Long getLogId() 
    {
        return logId;
    }

    public void setLogCode(String logCode) 
    {
        this.logCode = logCode;
    }

    public String getLogCode() 
    {
        return logCode;
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

    public void setLogDate(Date logDate) 
    {
        this.logDate = logDate;
    }

    public Date getLogDate() 
    {
        return logDate;
    }

    public void setTodayWorkContent(String todayWorkContent) 
    {
        this.todayWorkContent = todayWorkContent;
    }

    public String getTodayWorkContent() 
    {
        return todayWorkContent;
    }

    public void setCompanySiteManager(String companySiteManager) 
    {
        this.companySiteManager = companySiteManager;
    }

    public String getCompanySiteManager() 
    {
        return companySiteManager;
    }

    public void setWorkerCount(Long workerCount) 
    {
        this.workerCount = workerCount;
    }

    public Long getWorkerCount() 
    {
        return workerCount;
    }

    public void setWorkersReviewed(String workersReviewed) 
    {
        this.workersReviewed = workersReviewed;
    }

    public String getWorkersReviewed() 
    {
        return workersReviewed;
    }

    public void setWorkContentChanged(String workContentChanged) 
    {
        this.workContentChanged = workContentChanged;
    }

    public String getWorkContentChanged() 
    {
        return workContentChanged;
    }

    public void setWorkLocationChanged(String workLocationChanged) 
    {
        this.workLocationChanged = workLocationChanged;
    }

    public String getWorkLocationChanged() 
    {
        return workLocationChanged;
    }

    public void setHasSpecialWork(String hasSpecialWork) 
    {
        this.hasSpecialWork = hasSpecialWork;
    }

    public String getHasSpecialWork() 
    {
        return hasSpecialWork;
    }

    public void setSpecialWorkType(String specialWorkType) 
    {
        this.specialWorkType = specialWorkType;
    }

    public String getSpecialWorkType() 
    {
        return specialWorkType;
    }

    public void setSpecialWorkContent(String specialWorkContent) 
    {
        this.specialWorkContent = specialWorkContent;
    }

    public String getSpecialWorkContent() 
    {
        return specialWorkContent;
    }

    public void setHasAccident(String hasAccident) 
    {
        this.hasAccident = hasAccident;
    }

    public String getHasAccident() 
    {
        return hasAccident;
    }

    public void setAccidentBrief(String accidentBrief) 
    {
        this.accidentBrief = accidentBrief;
    }

    public String getAccidentBrief() 
    {
        return accidentBrief;
    }

    public void setCommunicationCoordination(String communicationCoordination) 
    {
        this.communicationCoordination = communicationCoordination;
    }

    public String getCommunicationCoordination() 
    {
        return communicationCoordination;
    }

    public void setAttachments(String attachments) 
    {
        this.attachments = attachments;
    }

    public String getAttachments() 
    {
        return attachments;
    }

    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
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
            .append("logId", getLogId())
            .append("logCode", getLogCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("logDate", getLogDate())
            .append("todayWorkContent", getTodayWorkContent())
            .append("companySiteManager", getCompanySiteManager())
            .append("workerCount", getWorkerCount())
            .append("workersReviewed", getWorkersReviewed())
            .append("workContentChanged", getWorkContentChanged())
            .append("workLocationChanged", getWorkLocationChanged())
            .append("hasSpecialWork", getHasSpecialWork())
            .append("specialWorkType", getSpecialWorkType())
            .append("specialWorkContent", getSpecialWorkContent())
            .append("hasAccident", getHasAccident())
            .append("accidentBrief", getAccidentBrief())
            .append("communicationCoordination", getCommunicationCoordination())
            .append("attachments", getAttachments())
            .append("notes", getNotes())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
