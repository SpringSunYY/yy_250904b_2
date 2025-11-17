package com.ruoyi.contractor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 承包商制度对象 biz_contractor_management
 * 
 * @author Laogao
 * @date 2025-10-23
 */
public class BizContractorManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long nanageId;

    /** 制度编号 */
    @Excel(name = "制度编号")
    private String systemCode;

    /** 制度名称 */
    @Excel(name = "制度名称")
    private String systemName;

    /** 版本号 */
    @Excel(name = "版本号")
    private String version;

    /** 发布日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishDate;

    /** 生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date effectiveDate;

    /** 修订日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修订日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date revisionDate;

    /** 变更说明 */
    @Excel(name = "变更说明")
    private String changeDescription;

    /** 状态 */
    @Excel(name = "状态")
    private String managementStatus;

    /** 责任部门 */
    @Excel(name = "责任部门")
    private String responsibleDepartment;

    /** 制度文本 */
    @Excel(name = "制度文本")
    private String systemContent;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedAt;

    public void setNanageId(Long nanageId) 
    {
        this.nanageId = nanageId;
    }

    public Long getNanageId() 
    {
        return nanageId;
    }

    public void setSystemCode(String systemCode) 
    {
        this.systemCode = systemCode;
    }

    public String getSystemCode() 
    {
        return systemCode;
    }

    public void setSystemName(String systemName) 
    {
        this.systemName = systemName;
    }

    public String getSystemName() 
    {
        return systemName;
    }

    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }

    public void setPublishDate(Date publishDate) 
    {
        this.publishDate = publishDate;
    }

    public Date getPublishDate() 
    {
        return publishDate;
    }

    public void setEffectiveDate(Date effectiveDate) 
    {
        this.effectiveDate = effectiveDate;
    }

    public Date getEffectiveDate() 
    {
        return effectiveDate;
    }

    public void setRevisionDate(Date revisionDate) 
    {
        this.revisionDate = revisionDate;
    }

    public Date getRevisionDate() 
    {
        return revisionDate;
    }

    public void setChangeDescription(String changeDescription) 
    {
        this.changeDescription = changeDescription;
    }

    public String getChangeDescription() 
    {
        return changeDescription;
    }

    public void setManagementStatus(String managementStatus) 
    {
        this.managementStatus = managementStatus;
    }

    public String getManagementStatus() 
    {
        return managementStatus;
    }

    public void setResponsibleDepartment(String responsibleDepartment) 
    {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsibleDepartment() 
    {
        return responsibleDepartment;
    }

    public void setSystemContent(String systemContent) 
    {
        this.systemContent = systemContent;
    }

    public String getSystemContent() 
    {
        return systemContent;
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

    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
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
            .append("nanageId", getNanageId())
            .append("systemCode", getSystemCode())
            .append("systemName", getSystemName())
            .append("version", getVersion())
            .append("publishDate", getPublishDate())
            .append("effectiveDate", getEffectiveDate())
            .append("revisionDate", getRevisionDate())
            .append("changeDescription", getChangeDescription())
            .append("managementStatus", getManagementStatus())
            .append("responsibleDepartment", getResponsibleDepartment())
            .append("systemContent", getSystemContent())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("remarks", getRemarks())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
