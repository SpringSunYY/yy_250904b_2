package com.ruoyi.personnel.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 特种人员台账对象 biz_special_operations
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizSpecialOperations extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 姓名 */
    private Long personnelId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String personnelName;

    /** 职业资格类型 */
    @Excel(name = "职业资格类型")
    private String vqType;

    /** 证书名称 */
    @Excel(name = "证书名称")
    private String certificate;

    /** 证书编号 */
    @Excel(name = "证书编号")
    private String certificateNumber;

    /** 发证机关 */
    @Excel(name = "发证机关")
    private String issueOrganization;

    /** 发证日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发证日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issueDate;

    /** 证书有效期至 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "证书有效期至", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expiryDate;

    /** 证书状态 */
    @Excel(name = "证书状态")
    private String status;

    /** 下次培训日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下次培训日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date nextTrainingDate;

    /** 证书扫描件 */
    @Excel(name = "证书扫描件")
    private String certificateScan;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建者 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新者 */
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

    public void setPersonnelId(Long personnelId) 
    {
        this.personnelId = personnelId;
    }

    public Long getPersonnelId() 
    {
        return personnelId;
    }

    public void setPersonnelName(String personnelName) 
    {
        this.personnelName = personnelName;
    }

    public String getPersonnelName() 
    {
        return personnelName;
    }

    public void setVqType(String vqType) 
    {
        this.vqType = vqType;
    }

    public String getVqType() 
    {
        return vqType;
    }

    public void setCertificate(String certificate) 
    {
        this.certificate = certificate;
    }

    public String getCertificate() 
    {
        return certificate;
    }

    public void setCertificateNumber(String certificateNumber) 
    {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificateNumber() 
    {
        return certificateNumber;
    }

    public void setIssueOrganization(String issueOrganization) 
    {
        this.issueOrganization = issueOrganization;
    }

    public String getIssueOrganization() 
    {
        return issueOrganization;
    }

    public void setIssueDate(Date issueDate) 
    {
        this.issueDate = issueDate;
    }

    public Date getIssueDate() 
    {
        return issueDate;
    }

    public void setExpiryDate(Date expiryDate) 
    {
        this.expiryDate = expiryDate;
    }

    public Date getExpiryDate() 
    {
        return expiryDate;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setNextTrainingDate(Date nextTrainingDate) 
    {
        this.nextTrainingDate = nextTrainingDate;
    }

    public Date getNextTrainingDate() 
    {
        return nextTrainingDate;
    }

    public void setCertificateScan(String certificateScan) 
    {
        this.certificateScan = certificateScan;
    }

    public String getCertificateScan() 
    {
        return certificateScan;
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
            .append("personnelId", getPersonnelId())
            .append("personnelName", getPersonnelName())
            .append("vqType", getVqType())
            .append("certificate", getCertificate())
            .append("certificateNumber", getCertificateNumber())
            .append("issueOrganization", getIssueOrganization())
            .append("issueDate", getIssueDate())
            .append("expiryDate", getExpiryDate())
            .append("status", getStatus())
            .append("nextTrainingDate", getNextTrainingDate())
            .append("certificateScan", getCertificateScan())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
