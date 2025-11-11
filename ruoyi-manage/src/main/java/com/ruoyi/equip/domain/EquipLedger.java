package com.ruoyi.equip.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备台账对象 equip_ledger
 * 
 * @author laogao
 * @date 2025-09-23
 */
public class EquipLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long equipId;

    /** 设备来源 */
    @Excel(name = "设备来源")
    private Integer isOriginal;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equName;

    /** 设备位号 */
    @Excel(name = "设备位号")
    private String equCode;

    /** 设备类型 */
    @Excel(name = "设备类型")
    private String type;

    /** 管理级别 */
    @Excel(name = "管理级别")
    private String equipLevels;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String specification;

    /** 投入使用日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "投入使用日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date putIntroDate;

    /** 设备材质 */
    @Excel(name = "设备材质")
    private String equipmentMaterial;

    /** 采购日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 出厂日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date factoryDate;

    /** 动静设备 */
    @Excel(name = "动静设备")
    private String dynamicStaticEquipment;

    /** 设计/操作参数 */
    @Excel(name = "设计/操作参数")
    private String designOperatingParameters;

    /** 安装位置 */
    @Excel(name = "安装位置")
    private String installLocation;

    /** 供应商 */
    private Long supplierId;

    /** 供应商 */
    @Excel(name = "供应商")
    private String supplierName;

    /** 所属部门 */
    private Long departmentId;

    /** 所属部门 */
    @Excel(name = "所属部门")
    private String departmentName;

    /** 责任人 */
    private Long responsiblePersonId;

    /** 责任人 */
    @Excel(name = "责任人")
    private String responsiblePerson;

    /** 设备状态 */
    @Excel(name = "设备状态")
    private String status;

    /** 特种设备 */
    @Excel(name = "特种设备")
    private String specialEquipment;

    /** 设备照片 */
    @Excel(name = "设备照片")
    private String equipmentPhotos;

    /** 铭牌照片 */
    @Excel(name = "铭牌照片")
    private String nameplatePhotos;

    /** 技术资料 */
    @Excel(name = "技术资料")
    private String technicalData;

    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }

    public void setIsOriginal(Integer isOriginal) 
    {
        this.isOriginal = isOriginal;
    }

    public Integer getIsOriginal() 
    {
        return isOriginal;
    }

    public void setEquName(String equName) 
    {
        this.equName = equName;
    }

    public String getEquName() 
    {
        return equName;
    }

    public void setEquCode(String equCode) 
    {
        this.equCode = equCode;
    }

    public String getEquCode() 
    {
        return equCode;
    }

    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setEquipLevels(String equipLevels) 
    {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() 
    {
        return equipLevels;
    }

    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    public String getSpecification() 
    {
        return specification;
    }

    public void setPutIntroDate(Date putIntroDate) 
    {
        this.putIntroDate = putIntroDate;
    }

    public Date getPutIntroDate() 
    {
        return putIntroDate;
    }

    public void setEquipmentMaterial(String equipmentMaterial) 
    {
        this.equipmentMaterial = equipmentMaterial;
    }

    public String getEquipmentMaterial() 
    {
        return equipmentMaterial;
    }

    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }

    public void setFactoryDate(Date factoryDate) 
    {
        this.factoryDate = factoryDate;
    }

    public Date getFactoryDate() 
    {
        return factoryDate;
    }

    public void setDynamicStaticEquipment(String dynamicStaticEquipment) 
    {
        this.dynamicStaticEquipment = dynamicStaticEquipment;
    }

    public String getDynamicStaticEquipment() 
    {
        return dynamicStaticEquipment;
    }

    public void setDesignOperatingParameters(String designOperatingParameters) 
    {
        this.designOperatingParameters = designOperatingParameters;
    }

    public String getDesignOperatingParameters() 
    {
        return designOperatingParameters;
    }

    public void setInstallLocation(String installLocation) 
    {
        this.installLocation = installLocation;
    }

    public String getInstallLocation() 
    {
        return installLocation;
    }

    public void setSupplierId(Long supplierId) 
    {
        this.supplierId = supplierId;
    }

    public Long getSupplierId() 
    {
        return supplierId;
    }

    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }

    public void setDepartmentId(Long departmentId) 
    {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() 
    {
        return departmentId;
    }

    public void setDepartmentName(String departmentName) 
    {
        this.departmentName = departmentName;
    }

    public String getDepartmentName() 
    {
        return departmentName;
    }

    public void setResponsiblePersonId(Long responsiblePersonId) 
    {
        this.responsiblePersonId = responsiblePersonId;
    }

    public Long getResponsiblePersonId() 
    {
        return responsiblePersonId;
    }

    public void setResponsiblePerson(String responsiblePerson) 
    {
        this.responsiblePerson = responsiblePerson;
    }

    public String getResponsiblePerson() 
    {
        return responsiblePerson;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    /** 特种设备 */
    public void setSpecialEquipment(String specialEquipment) 
    {
        this.specialEquipment = specialEquipment;
    }

    public String getSpecialEquipment() 
    {
        return specialEquipment;
    }

    public String getEquipmentPhotos() {
        return equipmentPhotos;
    }

    public void setEquipmentPhotos(String equipmentPhotos) {
        this.equipmentPhotos = equipmentPhotos;
    }

    public String getNameplatePhotos() {
        return nameplatePhotos;
    }

    public void setNameplatePhotos(String nameplatePhotos) {
        this.nameplatePhotos = nameplatePhotos;
    }

    public String getTechnicalData() {
        return technicalData;
    }

    public void setTechnicalData(String technicalData) {
        this.technicalData = technicalData;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("equipId", getEquipId())
            .append("isOriginal", getIsOriginal())
            .append("equName", getEquName())
            .append("equCode", getEquCode())
            .append("type", getType())
            .append("equipLevels", getEquipLevels())
            .append("specification", getSpecification())
            .append("putIntroDate", getPutIntroDate())
            .append("installLocation", getInstallLocation())
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("departmentId", getDepartmentId())
            .append("departmentName", getDepartmentName())
            .append("responsiblePersonId", getResponsiblePersonId())
            .append("responsiblePerson", getResponsiblePerson())
            .append("status", getStatus())
            .append("specialEquipment", getSpecialEquipment())
            .append("equipmentPhotos", getEquipmentPhotos())
            .append("nameplatePhotos", getNameplatePhotos())
            .append("technicalData", getTechnicalData())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
