package com.ruoyi.vulnerable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 易损设备检查对象 equip_vulnerable_inspection
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public class EquipVulnerableInspection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 检查编号 */
    @Excel(name = "检查编号")
    private String inspectionNumber;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 检查日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionDate;

    /** 检查人 */
    @Excel(name = "检查人")
    private String inspector;

    /** 检查照片 */
    @Excel(name = "检查照片")
    private String inspectionPhotos;

    /** 易损部位状态 */
    @Excel(name = "易损部位状态")
    private String vulnerablePartStatus;

    /** 问题描述 */
    @Excel(name = "问题描述")
    private String problemDescription;

    /** 现场照片 */
    @Excel(name = "现场照片")
    private String sitePhotos;

    /** 处理措施 */
    @Excel(name = "处理措施")
    private String handlingMeasures;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handler;

    /** 更换部件 */
    @Excel(name = "更换部件")
    private String replacedParts;

    /** 更换后照片 */
    @Excel(name = "更换后照片")
    private String afterReplacementPhotos;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setInspectionNumber(String inspectionNumber) 
    {
        this.inspectionNumber = inspectionNumber;
    }

    public String getInspectionNumber() 
    {
        return inspectionNumber;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setInspectionDate(Date inspectionDate) 
    {
        this.inspectionDate = inspectionDate;
    }

    public Date getInspectionDate() 
    {
        return inspectionDate;
    }

    public void setInspector(String inspector) 
    {
        this.inspector = inspector;
    }

    public String getInspector() 
    {
        return inspector;
    }

    public void setInspectionPhotos(String inspectionPhotos) 
    {
        this.inspectionPhotos = inspectionPhotos;
    }

    public String getInspectionPhotos() 
    {
        return inspectionPhotos;
    }

    public void setVulnerablePartStatus(String vulnerablePartStatus) 
    {
        this.vulnerablePartStatus = vulnerablePartStatus;
    }

    public String getVulnerablePartStatus() 
    {
        return vulnerablePartStatus;
    }

    public void setProblemDescription(String problemDescription) 
    {
        this.problemDescription = problemDescription;
    }

    public String getProblemDescription() 
    {
        return problemDescription;
    }

    public void setSitePhotos(String sitePhotos) 
    {
        this.sitePhotos = sitePhotos;
    }

    public String getSitePhotos() 
    {
        return sitePhotos;
    }

    public void setHandlingMeasures(String handlingMeasures) 
    {
        this.handlingMeasures = handlingMeasures;
    }

    public String getHandlingMeasures() 
    {
        return handlingMeasures;
    }

    public void setHandler(String handler) 
    {
        this.handler = handler;
    }

    public String getHandler() 
    {
        return handler;
    }

    public void setReplacedParts(String replacedParts) 
    {
        this.replacedParts = replacedParts;
    }

    public String getReplacedParts() 
    {
        return replacedParts;
    }

    public void setAfterReplacementPhotos(String afterReplacementPhotos) 
    {
        this.afterReplacementPhotos = afterReplacementPhotos;
    }

    public String getAfterReplacementPhotos() 
    {
        return afterReplacementPhotos;
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
            .append("id", getId())
            .append("inspectionNumber", getInspectionNumber())
            .append("equipmentName", getEquipmentName())
            .append("inspectionDate", getInspectionDate())
            .append("inspector", getInspector())
            .append("inspectionPhotos", getInspectionPhotos())
            .append("vulnerablePartStatus", getVulnerablePartStatus())
            .append("problemDescription", getProblemDescription())
            .append("sitePhotos", getSitePhotos())
            .append("handlingMeasures", getHandlingMeasures())
            .append("handler", getHandler())
            .append("replacedParts", getReplacedParts())
            .append("afterReplacementPhotos", getAfterReplacementPhotos())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
