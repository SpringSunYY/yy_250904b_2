package com.ruoyi.equip.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备检验检测对象 eq_inspection
 * 
 * @author laogao
 * @date 2025-09-23
 */
public class EqInspection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long inspectionId;

    /** 设备 */
    private Long equipmentId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备位号 */
    @Excel(name = "设备位号")
    private String equipmentCode;

    /** 检验类型 */
    @Excel(name = "检验类型")
    private String inspectionType;

    /** 检验周期 */
    @Excel(name = "检验周期")
    private Long inspectionCycle;

    /** 检验日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检验日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualDate;

    /** 检验机构 */
    @Excel(name = "检验机构")
    private String inspectionOrg;

    /** 检验结论 */
    @Excel(name = "检验结论")
    private String inspectionStatus;

    /** 检验证书编号 */
    @Excel(name = "检验证书编号")
    private String certificateNo;

    /** 检验报告文件 */
    @Excel(name = "检验报告文件")
    private String reportFile;

    /** 证书有效期至 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "证书有效期至", width = 30, dateFormat = "yyyy-MM-dd")
    private Date validDate;

    public void setInspectionId(Long inspectionId) 
    {
        this.inspectionId = inspectionId;
    }

    public Long getInspectionId() 
    {
        return inspectionId;
    }

    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setEquipmentCode(String equipmentCode) 
    {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentCode() 
    {
        return equipmentCode;
    }

    public void setInspectionType(String inspectionType) 
    {
        this.inspectionType = inspectionType;
    }

    public String getInspectionType() 
    {
        return inspectionType;
    }

    public void setInspectionCycle(Long inspectionCycle) 
    {
        this.inspectionCycle = inspectionCycle;
    }

    public Long getInspectionCycle() 
    {
        return inspectionCycle;
    }

    public void setActualDate(Date actualDate) 
    {
        this.actualDate = actualDate;
    }

    public Date getActualDate() 
    {
        return actualDate;
    }

    public void setInspectionOrg(String inspectionOrg) 
    {
        this.inspectionOrg = inspectionOrg;
    }

    public String getInspectionOrg() 
    {
        return inspectionOrg;
    }

    public void setInspectionStatus(String inspectionStatus) 
    {
        this.inspectionStatus = inspectionStatus;
    }

    public String getInspectionStatus() 
    {
        return inspectionStatus;
    }

    public void setCertificateNo(String certificateNo) 
    {
        this.certificateNo = certificateNo;
    }

    public String getCertificateNo() 
    {
        return certificateNo;
    }

    public void setReportFile(String reportFile) 
    {
        this.reportFile = reportFile;
    }

    public String getReportFile() 
    {
        return reportFile;
    }

    public void setValidDate(Date validDate) 
    {
        this.validDate = validDate;
    }

    public Date getValidDate() 
    {
        return validDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("inspectionId", getInspectionId())
            .append("equipmentId", getEquipmentId())
            .append("equipmentName", getEquipmentName())
            .append("equipmentCode", getEquipmentCode())
            .append("inspectionType", getInspectionType())
            .append("inspectionCycle", getInspectionCycle())
            .append("actualDate", getActualDate())
            .append("inspectionOrg", getInspectionOrg())
            .append("inspectionStatus", getInspectionStatus())
            .append("certificateNo", getCertificateNo())
            .append("reportFile", getReportFile())
            .append("validDate", getValidDate())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
