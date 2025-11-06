package com.ruoyi.special.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 检验检测对象 equip_special_inspection
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
public class EquipSpecialInspection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备种类 */
    @Excel(name = "设备种类")
    private String equipmentType;

    /** 检验类型 */
    @Excel(name = "检验类型")
    private String inspectionType;

    /** 使用部门 */
    @Excel(name = "使用部门")
    private String userUnit;

    /** 管理人员 */
    @Excel(name = "管理人员")
    private String manager;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 检定日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检定日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date verificationDate;

    /** 检验周期(月) */
    @Excel(name = "检验周期(月)")
    private Long inspectionCycle;

    /** 下次检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下次检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextInspectionDate;

    /** 检验结论 */
    @Excel(name = "检验结论")
    private String inspectionConclusion;

    /** 检验机构 */
    @Excel(name = "检验机构")
    private String inspectionAgency;

    /** 报告编号 */
    @Excel(name = "报告编号")
    private String reportNo;

    /** 报告文本 */
    @Excel(name = "报告文本")
    private String reportText;

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

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setEquipmentType(String equipmentType) 
    {
        this.equipmentType = equipmentType;
    }

    public String getEquipmentType() 
    {
        return equipmentType;
    }

    public void setInspectionType(String inspectionType) 
    {
        this.inspectionType = inspectionType;
    }

    public String getInspectionType() 
    {
        return inspectionType;
    }

    public void setUserUnit(String userUnit) 
    {
        this.userUnit = userUnit;
    }

    public String getUserUnit() 
    {
        return userUnit;
    }

    public void setManager(String manager) 
    {
        this.manager = manager;
    }

    public String getManager() 
    {
        return manager;
    }

    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    public void setVerificationDate(Date verificationDate) 
    {
        this.verificationDate = verificationDate;
    }

    public Date getVerificationDate() 
    {
        return verificationDate;
    }

    public void setInspectionCycle(Long inspectionCycle) 
    {
        this.inspectionCycle = inspectionCycle;
    }

    public Long getInspectionCycle() 
    {
        return inspectionCycle;
    }

    public void setNextInspectionDate(Date nextInspectionDate) 
    {
        this.nextInspectionDate = nextInspectionDate;
    }

    public Date getNextInspectionDate() 
    {
        return nextInspectionDate;
    }

    public void setInspectionConclusion(String inspectionConclusion) 
    {
        this.inspectionConclusion = inspectionConclusion;
    }

    public String getInspectionConclusion() 
    {
        return inspectionConclusion;
    }

    public void setInspectionAgency(String inspectionAgency) 
    {
        this.inspectionAgency = inspectionAgency;
    }

    public String getInspectionAgency() 
    {
        return inspectionAgency;
    }

    public void setReportNo(String reportNo) 
    {
        this.reportNo = reportNo;
    }

    public String getReportNo() 
    {
        return reportNo;
    }

    public void setReportText(String reportText) 
    {
        this.reportText = reportText;
    }

    public String getReportText() 
    {
        return reportText;
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
            .append("equipmentName", getEquipmentName())
            .append("equipmentType", getEquipmentType())
            .append("inspectionType", getInspectionType())
            .append("userUnit", getUserUnit())
            .append("manager", getManager())
            .append("contactPhone", getContactPhone())
            .append("verificationDate", getVerificationDate())
            .append("inspectionCycle", getInspectionCycle())
            .append("nextInspectionDate", getNextInspectionDate())
            .append("inspectionConclusion", getInspectionConclusion())
            .append("inspectionAgency", getInspectionAgency())
            .append("reportNo", getReportNo())
            .append("reportText", getReportText())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
