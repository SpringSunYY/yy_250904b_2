package com.ruoyi.special.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 压力容器对象 equip_pressure_vessel
 * 
 * @author Laogao
 * @date 2025-11-04
 */
public class EquipPressureVessel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String name;

    /** 规格 */
    @Excel(name = "规格")
    private String specification;

    /** 容积(m³) */
    @Excel(name = "容积(m³)")
    private BigDecimal volume;

    /** 设备类别 */
    @Excel(name = "设备类别")
    private String equipmentCategory;

    /** 安装型式 */
    @Excel(name = "安装型式")
    private String installationType;

    /** 设计压力(MPa) */
    @Excel(name = "设计压力(MPa)")
    private BigDecimal designPressure;

    /** 设计温度(℃) */
    @Excel(name = "设计温度(℃)")
    private BigDecimal designTemperature;

    /** 工作压力(MPa) */
    @Excel(name = "工作压力(MPa)")
    private BigDecimal workingPressure;

    /** 工作温度(℃) */
    @Excel(name = "工作温度(℃)")
    private BigDecimal workingTemperature;

    /** 材质 */
    @Excel(name = "材质")
    private String material;

    /** 介质 */
    @Excel(name = "介质")
    private String medium;

    /** 容器净重(KG) */
    @Excel(name = "容器净重(KG)")
    private BigDecimal netWeight;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private String manufacturer;

    /** 制造日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "制造日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date manufactureDate;

    /** 制造许可证号 */
    @Excel(name = "制造许可证号")
    private String manufactureLicenseNo;

    /** 制造编号 */
    @Excel(name = "制造编号")
    private String manufactureSerialNo;

    /** 安装单位 */
    @Excel(name = "安装单位")
    private String installationUnit;

    /** 安装日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installationDate;

    /** 投入使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投入使用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date commissioningDate;

    /** 设备代码 */
    @Excel(name = "设备代码")
    private String equipmentCode;

    /** 使用证编号 */
    @Excel(name = "使用证编号")
    private String usageCertificateNo;

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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }

    public void setVolume(BigDecimal volume) 
    {
        this.volume = volume;
    }

    public BigDecimal getVolume() 
    {
        return volume;
    }

    public void setEquipmentCategory(String equipmentCategory) 
    {
        this.equipmentCategory = equipmentCategory;
    }

    public String getEquipmentCategory() 
    {
        return equipmentCategory;
    }

    public void setInstallationType(String installationType) 
    {
        this.installationType = installationType;
    }

    public String getInstallationType() 
    {
        return installationType;
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

    public void setWorkingPressure(BigDecimal workingPressure) 
    {
        this.workingPressure = workingPressure;
    }

    public BigDecimal getWorkingPressure() 
    {
        return workingPressure;
    }

    public void setWorkingTemperature(BigDecimal workingTemperature) 
    {
        this.workingTemperature = workingTemperature;
    }

    public BigDecimal getWorkingTemperature() 
    {
        return workingTemperature;
    }

    public void setMaterial(String material) 
    {
        this.material = material;
    }

    public String getMaterial() 
    {
        return material;
    }

    public void setMedium(String medium) 
    {
        this.medium = medium;
    }

    public String getMedium() 
    {
        return medium;
    }

    public void setNetWeight(BigDecimal netWeight) 
    {
        this.netWeight = netWeight;
    }

    public BigDecimal getNetWeight() 
    {
        return netWeight;
    }

    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }

    public void setManufactureDate(Date manufactureDate) 
    {
        this.manufactureDate = manufactureDate;
    }

    public Date getManufactureDate() 
    {
        return manufactureDate;
    }

    public void setManufactureLicenseNo(String manufactureLicenseNo) 
    {
        this.manufactureLicenseNo = manufactureLicenseNo;
    }

    public String getManufactureLicenseNo() 
    {
        return manufactureLicenseNo;
    }

    public void setManufactureSerialNo(String manufactureSerialNo) 
    {
        this.manufactureSerialNo = manufactureSerialNo;
    }

    public String getManufactureSerialNo() 
    {
        return manufactureSerialNo;
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

    public void setEquipmentCode(String equipmentCode) 
    {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentCode() 
    {
        return equipmentCode;
    }

    public void setUsageCertificateNo(String usageCertificateNo) 
    {
        this.usageCertificateNo = usageCertificateNo;
    }

    public String getUsageCertificateNo() 
    {
        return usageCertificateNo;
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
            .append("id", getId())
            .append("name", getName())
            .append("specification", getSpecification())
            .append("volume", getVolume())
            .append("equipmentCategory", getEquipmentCategory())
            .append("installationType", getInstallationType())
            .append("designPressure", getDesignPressure())
            .append("designTemperature", getDesignTemperature())
            .append("workingPressure", getWorkingPressure())
            .append("workingTemperature", getWorkingTemperature())
            .append("material", getMaterial())
            .append("medium", getMedium())
            .append("netWeight", getNetWeight())
            .append("manufacturer", getManufacturer())
            .append("manufactureDate", getManufactureDate())
            .append("manufactureLicenseNo", getManufactureLicenseNo())
            .append("manufactureSerialNo", getManufactureSerialNo())
            .append("installationUnit", getInstallationUnit())
            .append("installationDate", getInstallationDate())
            .append("commissioningDate", getCommissioningDate())
            .append("equipmentCode", getEquipmentCode())
            .append("usageCertificateNo", getUsageCertificateNo())
            .append("usageRegistrationCertificate", getUsageRegistrationCertificate())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
