package com.ruoyi.special.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 锅炉对象 equip_special_boiler
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public class EquipSpecialBoiler extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long boilerId;

    /** 设备编号 */
    @Excel(name = "设备编号")
    private String deviceNumber;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 锅炉种类 */
    @Excel(name = "锅炉种类")
    private String boilerType;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String deviceModel;

    /** 制造厂家 */
    @Excel(name = "制造厂家")
    private String manufacturer;

    /** 制造日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "制造日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date manufactureDate;

    /** 出厂编号 */
    @Excel(name = "出厂编号")
    private String serialNumber;

    /** 安装单位 */
    @Excel(name = "安装单位")
    private String installationUnit;

    /** 安装日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "安装日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date installationDate;

    /** 验收日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date acceptanceDate;

    /** 额定蒸发量（t/h） */
    @Excel(name = "额定蒸发量", readConverterExp = "t=/h")
    private BigDecimal ratedEvaporation;

    /** 热功率（MW） */
    @Excel(name = "热功率", readConverterExp = "M=W")
    private BigDecimal thermalPower;

    /** 额定工作压力（MPa） */
    @Excel(name = "额定工作压力", readConverterExp = "M=Pa")
    private BigDecimal ratedWorkingPressure;

    /** 额定工作温度（℃） */
    @Excel(name = "额定工作温度", readConverterExp = "℃=")
    private Long ratedWorkingTemperature;

    /** 燃料种类 */
    @Excel(name = "燃料种类")
    private String fuelType;

    /** 受热面积（m²） */
    @Excel(name = "受热面积", readConverterExp = "m=²")
    private BigDecimal heatingArea;

    /** 本体重量（KG） */
    @Excel(name = "本体重量", readConverterExp = "K=G")
    private BigDecimal bodyWeight;

    /** 设备(注册)代码 */
    @Excel(name = "设备(注册)代码")
    private String deviceCode;

    /** 登记机关 */
    @Excel(name = "登记机关")
    private String registrationAuthority;

    /** 登记证编号 */
    @Excel(name = "登记证编号")
    private String registrationNumber;

    /** 登记证文本 */
    @Excel(name = "登记证文本")
    private String registrationCertificate;

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

    public void setBoilerId(Long boilerId) 
    {
        this.boilerId = boilerId;
    }

    public Long getBoilerId() 
    {
        return boilerId;
    }

    public void setDeviceNumber(String deviceNumber) 
    {
        this.deviceNumber = deviceNumber;
    }

    public String getDeviceNumber() 
    {
        return deviceNumber;
    }

    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }

    public void setBoilerType(String boilerType) 
    {
        this.boilerType = boilerType;
    }

    public String getBoilerType() 
    {
        return boilerType;
    }

    public void setDeviceModel(String deviceModel) 
    {
        this.deviceModel = deviceModel;
    }

    public String getDeviceModel() 
    {
        return deviceModel;
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

    public void setSerialNumber(String serialNumber) 
    {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() 
    {
        return serialNumber;
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

    public void setAcceptanceDate(Date acceptanceDate) 
    {
        this.acceptanceDate = acceptanceDate;
    }

    public Date getAcceptanceDate() 
    {
        return acceptanceDate;
    }

    public void setRatedEvaporation(BigDecimal ratedEvaporation) 
    {
        this.ratedEvaporation = ratedEvaporation;
    }

    public BigDecimal getRatedEvaporation() 
    {
        return ratedEvaporation;
    }

    public void setThermalPower(BigDecimal thermalPower) 
    {
        this.thermalPower = thermalPower;
    }

    public BigDecimal getThermalPower() 
    {
        return thermalPower;
    }

    public void setRatedWorkingPressure(BigDecimal ratedWorkingPressure) 
    {
        this.ratedWorkingPressure = ratedWorkingPressure;
    }

    public BigDecimal getRatedWorkingPressure() 
    {
        return ratedWorkingPressure;
    }

    public void setRatedWorkingTemperature(Long ratedWorkingTemperature) 
    {
        this.ratedWorkingTemperature = ratedWorkingTemperature;
    }

    public Long getRatedWorkingTemperature() 
    {
        return ratedWorkingTemperature;
    }

    public void setFuelType(String fuelType) 
    {
        this.fuelType = fuelType;
    }

    public String getFuelType() 
    {
        return fuelType;
    }

    public void setHeatingArea(BigDecimal heatingArea) 
    {
        this.heatingArea = heatingArea;
    }

    public BigDecimal getHeatingArea() 
    {
        return heatingArea;
    }

    public void setBodyWeight(BigDecimal bodyWeight) 
    {
        this.bodyWeight = bodyWeight;
    }

    public BigDecimal getBodyWeight() 
    {
        return bodyWeight;
    }

    public void setDeviceCode(String deviceCode) 
    {
        this.deviceCode = deviceCode;
    }

    public String getDeviceCode() 
    {
        return deviceCode;
    }

    public void setRegistrationAuthority(String registrationAuthority) 
    {
        this.registrationAuthority = registrationAuthority;
    }

    public String getRegistrationAuthority() 
    {
        return registrationAuthority;
    }

    public void setRegistrationNumber(String registrationNumber) 
    {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationNumber() 
    {
        return registrationNumber;
    }

    public void setRegistrationCertificate(String registrationCertificate) 
    {
        this.registrationCertificate = registrationCertificate;
    }

    public String getRegistrationCertificate() 
    {
        return registrationCertificate;
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
            .append("boilerId", getBoilerId())
            .append("deviceNumber", getDeviceNumber())
            .append("deviceName", getDeviceName())
            .append("boilerType", getBoilerType())
            .append("deviceModel", getDeviceModel())
            .append("manufacturer", getManufacturer())
            .append("manufactureDate", getManufactureDate())
            .append("serialNumber", getSerialNumber())
            .append("installationUnit", getInstallationUnit())
            .append("installationDate", getInstallationDate())
            .append("acceptanceDate", getAcceptanceDate())
            .append("ratedEvaporation", getRatedEvaporation())
            .append("thermalPower", getThermalPower())
            .append("ratedWorkingPressure", getRatedWorkingPressure())
            .append("ratedWorkingTemperature", getRatedWorkingTemperature())
            .append("fuelType", getFuelType())
            .append("heatingArea", getHeatingArea())
            .append("bodyWeight", getBodyWeight())
            .append("deviceCode", getDeviceCode())
            .append("registrationAuthority", getRegistrationAuthority())
            .append("registrationNumber", getRegistrationNumber())
            .append("registrationCertificate", getRegistrationCertificate())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
