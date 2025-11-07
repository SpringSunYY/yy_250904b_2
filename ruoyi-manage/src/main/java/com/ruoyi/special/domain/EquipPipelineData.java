package com.ruoyi.special.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 管道数据对象 equip_pipeline_data
 * 
 * @author Laogao
 * @date 2025-11-04
 */
public class EquipPipelineData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private String pipelineId;

    /** 管道编号 */
    @Excel(name = "管道编号")
    private String pipelineNo;

    /** 管道名称 */
    @Excel(name = "管道名称")
    private String pipelineName;

    /** 是否压力管道 */
    @Excel(name = "是否压力管道")
    private String pressurePipeline;

    /** 管道等级 */
    @Excel(name = "管道等级")
    private String pipelineClass;

    /** 材质 */
    @Excel(name = "材质")
    private String material;

    /** 设计单位 */
    @Excel(name = "设计单位")
    private String designUnit;

    /** 安装单位 */
    @Excel(name = "安装单位")
    private String installationUnit;

    /** 安装日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installationDate;

    /** 投用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date commissioningDate;

    /** 隔热代号 */
    @Excel(name = "隔热代号")
    private String insulationCode;

    /** 公称直径(mm) */
    @Excel(name = "公称直径(mm)")
    private String nominalDiameter;

    /** 公称壁厚(mm) */
    @Excel(name = "公称壁厚(mm)")
    private BigDecimal nominalWallThickness;

    /** 管道壁厚等级 */
    @Excel(name = "管道壁厚等级")
    private String wallThicknessClass;

    /** 介质名称 */
    @Excel(name = "介质名称")
    private String mediumName;

    /** 介质状态 */
    @Excel(name = "介质状态")
    private String mediumState;

    /** 管道长度 */
    @Excel(name = "管道长度")
    private BigDecimal pipelineLength;

    /** 起点 */
    @Excel(name = "起点")
    private String startPoint;

    /** 止点 */
    @Excel(name = "止点")
    private String endPoint;

    /** 工作压力(MPa) */
    @Excel(name = "工作压力(MPa)")
    private BigDecimal operatingPressure;

    /** 工作温度(℃) */
    @Excel(name = "工作温度(℃)")
    private BigDecimal operatingTemperature;

    /** 设计压力(MPa) */
    @Excel(name = "设计压力(MPa)")
    private BigDecimal designPressure;

    /** 设计温度(℃) */
    @Excel(name = "设计温度(℃)")
    private BigDecimal designTemperature;

    /** 压力试验类型 */
    @Excel(name = "压力试验类型")
    private String pressureTestType;

    /** 试验压力 */
    @Excel(name = "试验压力")
    private BigDecimal testPressure;

    /** 安全等级 */
    @Excel(name = "安全等级")
    private String safetyClass;

    /** 使用登记证文本 */
    @Excel(name = "使用登记证文本")
    private String usageRegistrationCertificate;

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

    public void setPipelineId(String pipelineId) 
    {
        this.pipelineId = pipelineId;
    }

    public String getPipelineId() 
    {
        return pipelineId;
    }

    public void setPipelineNo(String pipelineNo) 
    {
        this.pipelineNo = pipelineNo;
    }

    public String getPipelineNo() 
    {
        return pipelineNo;
    }

    public void setPipelineName(String pipelineName) 
    {
        this.pipelineName = pipelineName;
    }

    public String getPipelineName() 
    {
        return pipelineName;
    }

    public void setPressurePipeline(String pressurePipeline) 
    {
        this.pressurePipeline = pressurePipeline;
    }

    public String getPressurePipeline() 
    {
        return pressurePipeline;
    }

    public void setPipelineClass(String pipelineClass) 
    {
        this.pipelineClass = pipelineClass;
    }

    public String getPipelineClass() 
    {
        return pipelineClass;
    }

    public void setMaterial(String material) 
    {
        this.material = material;
    }

    public String getMaterial() 
    {
        return material;
    }

    public void setDesignUnit(String designUnit) 
    {
        this.designUnit = designUnit;
    }

    public String getDesignUnit() 
    {
        return designUnit;
    }

    public void setInstallationUnit(String installationUnit) 
    {
        this.installationUnit = installationUnit;
    }

    public String getInstallationUnit() 
    {
        return installationUnit;
    }

    public void setInstallationDate(Date installationDate) 
    {
        this.installationDate = installationDate;
    }

    public Date getInstallationDate() 
    {
        return installationDate;
    }

    public void setCommissioningDate(Date commissioningDate) 
    {
        this.commissioningDate = commissioningDate;
    }

    public Date getCommissioningDate() 
    {
        return commissioningDate;
    }

    public void setInsulationCode(String insulationCode) 
    {
        this.insulationCode = insulationCode;
    }

    public String getInsulationCode() 
    {
        return insulationCode;
    }

    public void setNominalDiameter(String nominalDiameter) 
    {
        this.nominalDiameter = nominalDiameter;
    }

    public String getNominalDiameter() 
    {
        return nominalDiameter;
    }

    public void setNominalWallThickness(BigDecimal nominalWallThickness) 
    {
        this.nominalWallThickness = nominalWallThickness;
    }

    public BigDecimal getNominalWallThickness() 
    {
        return nominalWallThickness;
    }

    public void setWallThicknessClass(String wallThicknessClass) 
    {
        this.wallThicknessClass = wallThicknessClass;
    }

    public String getWallThicknessClass() 
    {
        return wallThicknessClass;
    }

    public void setMediumName(String mediumName) 
    {
        this.mediumName = mediumName;
    }

    public String getMediumName() 
    {
        return mediumName;
    }

    public void setMediumState(String mediumState) 
    {
        this.mediumState = mediumState;
    }

    public String getMediumState() 
    {
        return mediumState;
    }

    public void setPipelineLength(BigDecimal pipelineLength) 
    {
        this.pipelineLength = pipelineLength;
    }

    public BigDecimal getPipelineLength() 
    {
        return pipelineLength;
    }

    public void setStartPoint(String startPoint) 
    {
        this.startPoint = startPoint;
    }

    public String getStartPoint() 
    {
        return startPoint;
    }

    public void setEndPoint(String endPoint) 
    {
        this.endPoint = endPoint;
    }

    public String getEndPoint() 
    {
        return endPoint;
    }

    public void setOperatingPressure(BigDecimal operatingPressure) 
    {
        this.operatingPressure = operatingPressure;
    }

    public BigDecimal getOperatingPressure() 
    {
        return operatingPressure;
    }

    public void setOperatingTemperature(BigDecimal operatingTemperature) 
    {
        this.operatingTemperature = operatingTemperature;
    }

    public BigDecimal getOperatingTemperature() 
    {
        return operatingTemperature;
    }

    public void setDesignPressure(BigDecimal designPressure) 
    {
        this.designPressure = designPressure;
    }

    public BigDecimal getDesignPressure() 
    {
        return designPressure;
    }

    public void setDesignTemperature(BigDecimal designTemperature) 
    {
        this.designTemperature = designTemperature;
    }

    public BigDecimal getDesignTemperature() 
    {
        return designTemperature;
    }

    public void setPressureTestType(String pressureTestType) 
    {
        this.pressureTestType = pressureTestType;
    }

    public String getPressureTestType() 
    {
        return pressureTestType;
    }

    public void setTestPressure(BigDecimal testPressure) 
    {
        this.testPressure = testPressure;
    }

    public BigDecimal getTestPressure() 
    {
        return testPressure;
    }

    public void setSafetyClass(String safetyClass) 
    {
        this.safetyClass = safetyClass;
    }

    public String getSafetyClass() 
    {
        return safetyClass;
    }

    public void setUsageRegistrationCertificate(String usageRegistrationCertificate) 
    {
        this.usageRegistrationCertificate = usageRegistrationCertificate;
    }

    public String getUsageRegistrationCertificate() 
    {
        return usageRegistrationCertificate;
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
            .append("pipelineId", getPipelineId())
            .append("pipelineNo", getPipelineNo())
            .append("pipelineName", getPipelineName())
            .append("pressurePipeline", getPressurePipeline())
            .append("pipelineClass", getPipelineClass())
            .append("material", getMaterial())
            .append("designUnit", getDesignUnit())
            .append("installationUnit", getInstallationUnit())
            .append("installationDate", getInstallationDate())
            .append("commissioningDate", getCommissioningDate())
            .append("insulationCode", getInsulationCode())
            .append("nominalDiameter", getNominalDiameter())
            .append("nominalWallThickness", getNominalWallThickness())
            .append("wallThicknessClass", getWallThicknessClass())
            .append("mediumName", getMediumName())
            .append("mediumState", getMediumState())
            .append("pipelineLength", getPipelineLength())
            .append("startPoint", getStartPoint())
            .append("endPoint", getEndPoint())
            .append("operatingPressure", getOperatingPressure())
            .append("operatingTemperature", getOperatingTemperature())
            .append("designPressure", getDesignPressure())
            .append("designTemperature", getDesignTemperature())
            .append("pressureTestType", getPressureTestType())
            .append("testPressure", getTestPressure())
            .append("safetyClass", getSafetyClass())
            .append("usageRegistrationCertificate", getUsageRegistrationCertificate())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
