package com.ruoyi.process.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 隐患整改记录对象 biz_hazard_rectifications
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizHazardRectifications extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 隐患编号 */
    @Excel(name = "隐患编号")
    private String rectificationCode;

    /** 关联检查记录 */
    @Excel(name = "关联检查记录")
    private Long inspectionId;

    /** 隐患描述 */
    @Excel(name = "隐患描述")
    private String hazardDescription;

    /** 整改要求 */
    @Excel(name = "整改要求")
    private String rectificationRequirements;

    /** 整改期限 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rectificationDeadline;

    /** 隐患照片 */
    @Excel(name = "隐患照片")
    private String hazardPhotos;

    /** 处罚依据 */
    @Excel(name = "处罚依据")
    private String penaltyBasis;

    /** 处罚金额 */
    @Excel(name = "处罚金额")
    private BigDecimal penaltyAmount;

    /** 整改状态 */
    @Excel(name = "整改状态")
    private String rectificationStatus;

    /** 验收人 */
    @Excel(name = "验收人")
    private String acceptancePerson;

    /** 验收日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date acceptanceDate;

    /** 验收结果 */
    @Excel(name = "验收结果")
    private String acceptanceResult;

    /** 整改照片 */
    @Excel(name = "整改照片")
    private String acceptancePhotos;

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

    public void setRectificationCode(String rectificationCode) 
    {
        this.rectificationCode = rectificationCode;
    }

    public String getRectificationCode() 
    {
        return rectificationCode;
    }

    public void setInspectionId(Long inspectionId) 
    {
        this.inspectionId = inspectionId;
    }

    public Long getInspectionId() 
    {
        return inspectionId;
    }

    public void setHazardDescription(String hazardDescription) 
    {
        this.hazardDescription = hazardDescription;
    }

    public String getHazardDescription() 
    {
        return hazardDescription;
    }

    public void setRectificationRequirements(String rectificationRequirements) 
    {
        this.rectificationRequirements = rectificationRequirements;
    }

    public String getRectificationRequirements() 
    {
        return rectificationRequirements;
    }

    public void setRectificationDeadline(Date rectificationDeadline) 
    {
        this.rectificationDeadline = rectificationDeadline;
    }

    public Date getRectificationDeadline() 
    {
        return rectificationDeadline;
    }

    public void setHazardPhotos(String hazardPhotos) 
    {
        this.hazardPhotos = hazardPhotos;
    }

    public String getHazardPhotos() 
    {
        return hazardPhotos;
    }

    public void setPenaltyBasis(String penaltyBasis) 
    {
        this.penaltyBasis = penaltyBasis;
    }

    public String getPenaltyBasis() 
    {
        return penaltyBasis;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) 
    {
        this.penaltyAmount = penaltyAmount;
    }

    public BigDecimal getPenaltyAmount() 
    {
        return penaltyAmount;
    }

    public void setRectificationStatus(String rectificationStatus) 
    {
        this.rectificationStatus = rectificationStatus;
    }

    public String getRectificationStatus() 
    {
        return rectificationStatus;
    }

    public void setAcceptancePerson(String acceptancePerson) 
    {
        this.acceptancePerson = acceptancePerson;
    }

    public String getAcceptancePerson() 
    {
        return acceptancePerson;
    }

    public void setAcceptanceDate(Date acceptanceDate) 
    {
        this.acceptanceDate = acceptanceDate;
    }

    public Date getAcceptanceDate() 
    {
        return acceptanceDate;
    }

    public void setAcceptanceResult(String acceptanceResult) 
    {
        this.acceptanceResult = acceptanceResult;
    }

    public String getAcceptanceResult() 
    {
        return acceptanceResult;
    }

    public void setAcceptancePhotos(String acceptancePhotos) 
    {
        this.acceptancePhotos = acceptancePhotos;
    }

    public String getAcceptancePhotos() 
    {
        return acceptancePhotos;
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
            .append("rectificationCode", getRectificationCode())
            .append("inspectionId", getInspectionId())
            .append("hazardDescription", getHazardDescription())
            .append("rectificationRequirements", getRectificationRequirements())
            .append("rectificationDeadline", getRectificationDeadline())
            .append("hazardPhotos", getHazardPhotos())
            .append("penaltyBasis", getPenaltyBasis())
            .append("penaltyAmount", getPenaltyAmount())
            .append("rectificationStatus", getRectificationStatus())
            .append("acceptancePerson", getAcceptancePerson())
            .append("acceptanceDate", getAcceptanceDate())
            .append("acceptanceResult", getAcceptanceResult())
            .append("acceptancePhotos", getAcceptancePhotos())
            .append("notes", getNotes())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
