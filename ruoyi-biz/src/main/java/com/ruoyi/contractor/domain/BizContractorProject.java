package com.ruoyi.contractor.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 发包项目信息对象 biz_contractor_project
 * 
 * @author Laogao
 * @date 2025-10-23
 */
public class BizContractorProject extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long projectId;

    /** 项目编号 */
    @Excel(name = "项目编号")
    private String projectCode;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目内容 */
    @Excel(name = "项目内容")
    private String projectContent;

    /** 项目类型 */
    @Excel(name = "项目类型")
    private String projectType;

    /** 项目预算（万元） */
    @Excel(name = "项目预算(元)", readConverterExp = "元=")
    private BigDecimal projectBudget;

    /** 项目地点 */
    @Excel(name = "项目地点")
    private String projectLocation;

    /** 计划开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plannedStartDate;

    /** 计划结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date plannedEndDate;

    /** 实际开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualStartDate;

    /** 实际结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualEndDate;

    /** 技术要求 */
    @Excel(name = "技术要求")
    private String technicalRequirements;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String responsibleDepartment;

    /** 项目负责人 */
    @Excel(name = "项目负责人")
    private String projectLeader;

    /** 项目状态 */
    @Excel(name = "项目状态")
    private String projectStatus;

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
    
    // 添加获取和设置创建时间的方法，与基类保持一致
    public Date getCreateTime() {
        return this.createdAt;
    }
    
    public void setCreateTime(Date createTime) {
        this.createdAt = createTime;
    }
    
    // 添加获取和设置创建人的方法，与基类保持一致
    public String getCreateBy() {
        return this.createdBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createdBy = createBy;
    }
    
    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    public void setProjectCode(String projectCode) 
    {
        this.projectCode = projectCode;
    }

    public String getProjectCode() 
    {
        return projectCode;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setProjectContent(String projectContent) 
    {
        this.projectContent = projectContent;
    }

    public String getProjectContent() 
    {
        return projectContent;
    }

    public void setProjectType(String projectType) 
    {
        this.projectType = projectType;
    }

    public String getProjectType() 
    {
        return projectType;
    }

    public void setProjectBudget(BigDecimal projectBudget) 
    {
        this.projectBudget = projectBudget;
    }

    public BigDecimal getProjectBudget() 
    {
        return projectBudget;
    }

    public void setProjectLocation(String projectLocation) 
    {
        this.projectLocation = projectLocation;
    }

    public String getProjectLocation() 
    {
        return projectLocation;
    }

    public void setPlannedStartDate(Date plannedStartDate) 
    {
        this.plannedStartDate = plannedStartDate;
    }

    public Date getPlannedStartDate() 
    {
        return plannedStartDate;
    }

    public void setPlannedEndDate(Date plannedEndDate) 
    {
        this.plannedEndDate = plannedEndDate;
    }

    public Date getPlannedEndDate() 
    {
        return plannedEndDate;
    }

    public void setActualStartDate(Date actualStartDate) 
    {
        this.actualStartDate = actualStartDate;
    }

    public Date getActualStartDate() 
    {
        return actualStartDate;
    }

    public void setActualEndDate(Date actualEndDate) 
    {
        this.actualEndDate = actualEndDate;
    }

    public Date getActualEndDate() 
    {
        return actualEndDate;
    }

    public void setTechnicalRequirements(String technicalRequirements) 
    {
        this.technicalRequirements = technicalRequirements;
    }

    public String getTechnicalRequirements() 
    {
        return technicalRequirements;
    }

    public void setResponsibleDepartment(String responsibleDepartment) 
    {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsibleDepartment() 
    {
        return responsibleDepartment;
    }

    public void setProjectLeader(String projectLeader) 
    {
        this.projectLeader = projectLeader;
    }

    public String getProjectLeader() 
    {
        return projectLeader;
    }

    public void setProjectStatus(String projectStatus) 
    {
        this.projectStatus = projectStatus;
    }

    public String getProjectStatus() 
    {
        return projectStatus;
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
            .append("projectId", getProjectId())
            .append("projectCode", getProjectCode())
            .append("projectName", getProjectName())
            .append("projectContent", getProjectContent())
            .append("projectType", getProjectType())
            .append("projectBudget", getProjectBudget())
            .append("projectLocation", getProjectLocation())
            .append("plannedStartDate", getPlannedStartDate())
            .append("plannedEndDate", getPlannedEndDate())
            .append("actualStartDate", getActualStartDate())
            .append("actualEndDate", getActualEndDate())
            .append("technicalRequirements", getTechnicalRequirements())
            .append("responsibleDepartment", getResponsibleDepartment())
            .append("projectLeader", getProjectLeader())
            .append("projectStatus", getProjectStatus())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("remark", getRemark())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
