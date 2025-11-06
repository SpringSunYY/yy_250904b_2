package com.ruoyi.equip.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 常压储罐检查对象 equip_atmospheric_tank
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public class EquipAtmosphericTank extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备位号 */
    @Excel(name = "设备位号")
    private String equipmentTag;

    /** 主要材质 */
    @Excel(name = "主要材质")
    private String mainMaterial;

    /** 容积（m³） */
    @Excel(name = "容积", readConverterExp = "m=³")
    private BigDecimal volume;

    /** 储存介质 */
    @Excel(name = "储存介质")
    private String storageMedium;

    /** 检查类型 */
    @Excel(name = "检查类型")
    private String inspectionType;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionTime;

    /** 检查人员 */
    @Excel(name = "检查人员")
    private String inspectors;

    /** 检查结果 */
    @Excel(name = "检查结果")
    private String inspectionResult;

    /** 问题描述 */
    @Excel(name = "问题描述")
    private String problemDescription;

    /** 处置措施 */
    @Excel(name = "处置措施")
    private String disposalMeasures;

    /** 检查记录 */
    @Excel(name = "检查记录")
    private String inspectionRecord;

    /** 整改记录 */
    @Excel(name = "整改记录")
    private String rectificationRecord;

    /** 完好性评价报告 */
    @Excel(name = "完好性评价报告")
    private String integrityReport;

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

    public void setEquipmentTag(String equipmentTag) 
    {
        this.equipmentTag = equipmentTag;
    }

    public String getEquipmentTag() 
    {
        return equipmentTag;
    }

    public void setMainMaterial(String mainMaterial) 
    {
        this.mainMaterial = mainMaterial;
    }

    public String getMainMaterial() 
    {
        return mainMaterial;
    }

    public void setVolume(BigDecimal volume) 
    {
        this.volume = volume;
    }

    public BigDecimal getVolume() 
    {
        return volume;
    }

    public void setStorageMedium(String storageMedium) 
    {
        this.storageMedium = storageMedium;
    }

    public String getStorageMedium() 
    {
        return storageMedium;
    }

    public void setInspectionType(String inspectionType) 
    {
        this.inspectionType = inspectionType;
    }

    public String getInspectionType() 
    {
        return inspectionType;
    }

    public void setInspectionTime(Date inspectionTime) 
    {
        this.inspectionTime = inspectionTime;
    }

    public Date getInspectionTime() 
    {
        return inspectionTime;
    }

    public void setInspectors(String inspectors) 
    {
        this.inspectors = inspectors;
    }

    public String getInspectors() 
    {
        return inspectors;
    }

    public void setInspectionResult(String inspectionResult) 
    {
        this.inspectionResult = inspectionResult;
    }

    public String getInspectionResult() 
    {
        return inspectionResult;
    }

    public void setProblemDescription(String problemDescription) 
    {
        this.problemDescription = problemDescription;
    }

    public String getProblemDescription() 
    {
        return problemDescription;
    }

    public void setDisposalMeasures(String disposalMeasures) 
    {
        this.disposalMeasures = disposalMeasures;
    }

    public String getDisposalMeasures() 
    {
        return disposalMeasures;
    }

    public void setInspectionRecord(String inspectionRecord) 
    {
        this.inspectionRecord = inspectionRecord;
    }

    public String getInspectionRecord() 
    {
        return inspectionRecord;
    }

    public void setRectificationRecord(String rectificationRecord) 
    {
        this.rectificationRecord = rectificationRecord;
    }

    public String getRectificationRecord() 
    {
        return rectificationRecord;
    }

    public void setIntegrityReport(String integrityReport) 
    {
        this.integrityReport = integrityReport;
    }

    public String getIntegrityReport() 
    {
        return integrityReport;
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
            .append("equipmentTag", getEquipmentTag())
            .append("mainMaterial", getMainMaterial())
            .append("volume", getVolume())
            .append("storageMedium", getStorageMedium())
            .append("inspectionType", getInspectionType())
            .append("inspectionTime", getInspectionTime())
            .append("inspectors", getInspectors())
            .append("inspectionResult", getInspectionResult())
            .append("problemDescription", getProblemDescription())
            .append("disposalMeasures", getDisposalMeasures())
            .append("inspectionRecord", getInspectionRecord())
            .append("rectificationRecord", getRectificationRecord())
            .append("integrityReport", getIntegrityReport())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
