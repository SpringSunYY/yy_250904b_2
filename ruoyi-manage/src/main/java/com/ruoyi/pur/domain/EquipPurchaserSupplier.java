package com.ruoyi.pur.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 供应商管理对象 equip_purchaser_supplier
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
public class EquipPurchaserSupplier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long supplierId;

    /** 供应商 */
    @Excel(name = "供应商")
    private String supplierName;

    /** 地址 */
    @Excel(name = "地址")
    private String address;

    /** 经营范围 */
    @Excel(name = "经营范围")
    private String businessScope;

    /** 公司规模 */
    @Excel(name = "公司规模")
    private Long companySize;

    /** 法定代表人 */
    @Excel(name = "法定代表人")
    private String legalRepresen;

    /** 营业执照 */
    @Excel(name = "营业执照")
    private String businessLicense;

    /** 资质证书 */
    @Excel(name = "资质证书")
    private String qualificationCertificate;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contactPerson;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactPhone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

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

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }

    public void setBusinessScope(String businessScope) 
    {
        this.businessScope = businessScope;
    }

    public String getBusinessScope() 
    {
        return businessScope;
    }

    public void setCompanySize(Long companySize) 
    {
        this.companySize = companySize;
    }

    public Long getCompanySize() 
    {
        return companySize;
    }

    public void setLegalRepresen(String legalRepresen) 
    {
        this.legalRepresen = legalRepresen;
    }

    public String getLegalRepresen() 
    {
        return legalRepresen;
    }

    public void setBusinessLicense(String businessLicense) 
    {
        this.businessLicense = businessLicense;
    }

    public String getBusinessLicense() 
    {
        return businessLicense;
    }

    public void setQualificationCertificate(String qualificationCertificate) 
    {
        this.qualificationCertificate = qualificationCertificate;
    }

    public String getQualificationCertificate() 
    {
        return qualificationCertificate;
    }

    public void setContactPerson(String contactPerson) 
    {
        this.contactPerson = contactPerson;
    }

    public String getContactPerson() 
    {
        return contactPerson;
    }

    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("supplierId", getSupplierId())
            .append("supplierName", getSupplierName())
            .append("address", getAddress())
            .append("businessScope", getBusinessScope())
            .append("companySize", getCompanySize())
            .append("legalRepresen", getLegalRepresen())
            .append("businessLicense", getBusinessLicense())
            .append("qualificationCertificate", getQualificationCertificate())
            .append("contactPerson", getContactPerson())
            .append("contactPhone", getContactPhone())
            .append("email", getEmail())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
