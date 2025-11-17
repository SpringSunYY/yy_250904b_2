package com.ruoyi.process.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 监督检查记录对象 biz_site_inspections
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizSiteInspections extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 检查记录编号 */
    @Excel(name = "检查记录编号")
    private String inspectionCode;

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
    private Date inspectionDatetime;

    /** 天气情况 */
    @Excel(name = "天气情况")
    private String weatherCondition;

    /** 作业地点/区域 */
    @Excel(name = "作业地点/区域")
    private String workLocation;

    /** 检查人员 */
    @Excel(name = "检查人员")
    private String inspectorName;

    /** 承包商陪同人员 */
    @Excel(name = "承包商陪同人员")
    private String contractorAttendant;

    /** 有无隐患 */
    @Excel(name = "有无隐患")
    private String hasHazards;

    /** 隐患数量 */
    @Excel(name = "隐患数量")
    private Long hazardsCount;

    /** 检查记录 */
    @Excel(name = "检查记录")
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setInspectionCode(String inspectionCode) 
    {
        this.inspectionCode = inspectionCode;
    }

    public String getInspectionCode() 
    {
        return inspectionCode;
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

    public void setInspectionDatetime(Date inspectionDatetime) 
    {
        this.inspectionDatetime = inspectionDatetime;
    }

    public Date getInspectionDatetime() 
    {
        return inspectionDatetime;
    }

    public void setWeatherCondition(String weatherCondition) 
    {
        this.weatherCondition = weatherCondition;
    }

    public String getWeatherCondition() 
    {
        return weatherCondition;
    }

    public void setWorkLocation(String workLocation) 
    {
        this.workLocation = workLocation;
    }

    public String getWorkLocation() 
    {
        return workLocation;
    }

    public void setInspectorName(String inspectorName) 
    {
        this.inspectorName = inspectorName;
    }

    public String getInspectorName() 
    {
        return inspectorName;
    }

    public void setContractorAttendant(String contractorAttendant) 
    {
        this.contractorAttendant = contractorAttendant;
    }

    public String getContractorAttendant() 
    {
        return contractorAttendant;
    }

    public void setHasHazards(String hasHazards) 
    {
        this.hasHazards = hasHazards;
    }

    public String getHasHazards() 
    {
        return hasHazards;
    }

    public void setHazardsCount(Long hazardsCount) 
    {
        this.hazardsCount = hazardsCount;
    }

    public Long getHazardsCount() 
    {
        return hazardsCount;
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
            .append("id", getId())
            .append("inspectionCode", getInspectionCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("inspectionDatetime", getInspectionDatetime())
            .append("weatherCondition", getWeatherCondition())
            .append("workLocation", getWorkLocation())
            .append("inspectorName", getInspectorName())
            .append("contractorAttendant", getContractorAttendant())
            .append("hasHazards", getHasHazards())
            .append("hazardsCount", getHazardsCount())
            .append("attachments", getAttachments())
            .append("notes", getNotes())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
