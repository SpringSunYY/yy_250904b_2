package com.ruoyi.special.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 场(厂)内专用机动车辆对象 equip_vehicle_inventory
 * 
 * @author Laogao
 * @date 2025-11-04
 */
public class EquipVehicleInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 单位内编号 */
    @Excel(name = "单位内编号")
    private String internalNumber;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String deviceType;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specificationModel;

    /** 动力方式 */
    @Excel(name = "动力方式")
    private String powerMethod;

    /** 制造单位 */
    @Excel(name = "制造单位")
    private String manufacturer;

    /** 制造日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "制造日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date manufactureDate;

    /** 出厂编号 */
    @Excel(name = "出厂编号")
    private String serialNumber;

    /** 投入使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投入使用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date useDate;

    /** 额定载荷（KG） */
    @Excel(name = "额定载荷", readConverterExp = "K=G")
    private BigDecimal ratedLoad;

    /** 设备(注册)代码 */
    @Excel(name = "设备(注册)代码")
    private String deviceCode;

    /** 登记机关 */
    @Excel(name = "登记机关")
    private String registrationAuthority;

    /** 登记证编号 */
    @Excel(name = "登记证编号")
    private String registrationNumber;

    /** 登记证图片 */
    @Excel(name = "登记证图片")
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

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }

    public void setInternalNumber(String internalNumber) 
    {
        this.internalNumber = internalNumber;
    }

    public String getInternalNumber() 
    {
        return internalNumber;
    }

    public void setDeviceType(String deviceType) 
    {
        this.deviceType = deviceType;
    }

    public String getDeviceType() 
    {
        return deviceType;
    }

    public void setSpecificationModel(String specificationModel) 
    {
        this.specificationModel = specificationModel;
    }

    public String getSpecificationModel() 
    {
        return specificationModel;
    }

    public void setPowerMethod(String powerMethod) 
    {
        this.powerMethod = powerMethod;
    }

    public String getPowerMethod() 
    {
        return powerMethod;
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

    public void setUseDate(Date useDate) 
    {
        this.useDate = useDate;
    }

    public Date getUseDate() 
    {
        return useDate;
    }

    public void setRatedLoad(BigDecimal ratedLoad) 
    {
        this.ratedLoad = ratedLoad;
    }

    public BigDecimal getRatedLoad() 
    {
        return ratedLoad;
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
            .append("id", getId())
            .append("deviceName", getDeviceName())
            .append("internalNumber", getInternalNumber())
            .append("deviceType", getDeviceType())
            .append("specificationModel", getSpecificationModel())
            .append("powerMethod", getPowerMethod())
            .append("manufacturer", getManufacturer())
            .append("manufactureDate", getManufactureDate())
            .append("serialNumber", getSerialNumber())
            .append("useDate", getUseDate())
            .append("ratedLoad", getRatedLoad())
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
