package com.ruoyi.process.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 项目日志对象 biz_project_logs
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizProjectLogs extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long logsId;

    /** 日志编号 */
    @Excel(name = "日志编号")
    private String logsCode;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 日志日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "日志日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date logsDate;

    /** 今日工作内容 */
    @Excel(name = "今日工作内容")
    private String todayWorkContent;

    /** 作业地点/区域 */
    @Excel(name = "作业地点/区域")
    private String workLocation;

    /** 承包商现场负责人 */
    @Excel(name = "承包商现场负责人")
    private String contractorSiteManager;

    /** 安全管理人员 */
    @Excel(name = "安全管理人员")
    private String safetyManager;

    /** 作业人员 */
    @Excel(name = "作业人员")
    private String workersInfo;

    /** 使用的工器具 */
    private Long toolId;

    /** 使用的工器具 */
    @Excel(name = "使用的工器具")
    private String toolsEquipment;

    /** 遇到的问题 */
    @Excel(name = "遇到的问题")
    private String issuesEncountered;

    /** 需要协调事项 */
    @Excel(name = "需要协调事项")
    private String coordinationNeeds;

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

    public void setLogsId(Long logsId) 
    {
        this.logsId = logsId;
    }

    public Long getLogsId() 
    {
        return logsId;
    }

    public void setLogsCode(String logsCode) 
    {
        this.logsCode = logsCode;
    }

    public String getLogsCode() 
    {
        return logsCode;
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

    public void setLogsDate(Date logsDate) 
    {
        this.logsDate = logsDate;
    }

    public Date getLogsDate() 
    {
        return logsDate;
    }

    public void setTodayWorkContent(String todayWorkContent) 
    {
        this.todayWorkContent = todayWorkContent;
    }

    public String getTodayWorkContent() 
    {
        return todayWorkContent;
    }

    public void setWorkLocation(String workLocation) 
    {
        this.workLocation = workLocation;
    }

    public String getWorkLocation() 
    {
        return workLocation;
    }

    public void setContractorSiteManager(String contractorSiteManager) 
    {
        this.contractorSiteManager = contractorSiteManager;
    }

    public String getContractorSiteManager() 
    {
        return contractorSiteManager;
    }

    public void setSafetyManager(String safetyManager) 
    {
        this.safetyManager = safetyManager;
    }

    public String getSafetyManager() 
    {
        return safetyManager;
    }

    public void setWorkersInfo(String workersInfo) 
    {
        this.workersInfo = workersInfo;
    }

    public String getWorkersInfo() 
    {
        return workersInfo;
    }

    public void setToolId(Long toolId) 
    {
        this.toolId = toolId;
    }

    public Long getToolId() 
    {
        return toolId;
    }

    public void setToolsEquipment(String toolsEquipment) 
    {
        this.toolsEquipment = toolsEquipment;
    }

    public String getToolsEquipment() 
    {
        return toolsEquipment;
    }

    public void setIssuesEncountered(String issuesEncountered) 
    {
        this.issuesEncountered = issuesEncountered;
    }

    public String getIssuesEncountered() 
    {
        return issuesEncountered;
    }

    public void setCoordinationNeeds(String coordinationNeeds) 
    {
        this.coordinationNeeds = coordinationNeeds;
    }

    public String getCoordinationNeeds() 
    {
        return coordinationNeeds;
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
            .append("logsId", getLogsId())
            .append("logsCode", getLogsCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("logsDate", getLogsDate())
            .append("todayWorkContent", getTodayWorkContent())
            .append("workLocation", getWorkLocation())
            .append("contractorSiteManager", getContractorSiteManager())
            .append("safetyManager", getSafetyManager())
            .append("workersInfo", getWorkersInfo())
            .append("toolId", getToolId())
            .append("toolsEquipment", getToolsEquipment())
            .append("issuesEncountered", getIssuesEncountered())
            .append("coordinationNeeds", getCoordinationNeeds())
            .append("notes", getNotes())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
