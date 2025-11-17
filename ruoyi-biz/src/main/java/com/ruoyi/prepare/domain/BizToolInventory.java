package com.ruoyi.prepare.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 工器具检查对象 biz_tool_inventory
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizToolInventory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long toolId;

    /** 工器具编号 */
    @Excel(name = "工器具编号")
    private String toolCode;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 工器具名称 */
    @Excel(name = "工器具名称")
    private String toolName;

    /** 工器具类型 */
    @Excel(name = "工器具类型")
    private String toolType;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String toolModel;

    /** 生产厂家 */
    @Excel(name = "生产厂家")
    private String manufacturer;

    /** 生产日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生产日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date productionDate;

    /** 购置日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购置日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date purchaseDate;

    /** 工器具状态 */
    @Excel(name = "工器具状态")
    private String toolStatus;

    /** 完好性确认 */
    @Excel(name = "完好性确认")
    private String integrityCheck;

    /** 安全标准符合性 */
    @Excel(name = "安全标准符合性")
    private String securityStandards;

    /** 作业安全符合性 */
    @Excel(name = "作业安全符合性")
    private String jobSafety;

    /** 工器具照片 */
    @Excel(name = "工器具照片")
    private String photo;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedAt;

    public void setToolId(Long toolId) 
    {
        this.toolId = toolId;
    }

    public Long getToolId() 
    {
        return toolId;
    }

    public void setToolCode(String toolCode) 
    {
        this.toolCode = toolCode;
    }

    public String getToolCode() 
    {
        return toolCode;
    }

    public void setChoiceId(Long choiceId) 
    {
        this.choiceId = choiceId;
    }

    public Long getChoiceId() 
    {
        return choiceId;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setContractorName(String contractorName) 
    {
        this.contractorName = contractorName;
    }

    public String getContractorName() 
    {
        return contractorName;
    }

    public void setToolName(String toolName) 
    {
        this.toolName = toolName;
    }

    public String getToolName() 
    {
        return toolName;
    }

    public void setToolType(String toolType) 
    {
        this.toolType = toolType;
    }

    public String getToolType() 
    {
        return toolType;
    }

    public void setToolModel(String toolModel) 
    {
        this.toolModel = toolModel;
    }

    public String getToolModel() 
    {
        return toolModel;
    }

    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }

    public void setProductionDate(Date productionDate) 
    {
        this.productionDate = productionDate;
    }

    public Date getProductionDate() 
    {
        return productionDate;
    }

    public void setPurchaseDate(Date purchaseDate) 
    {
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() 
    {
        return purchaseDate;
    }

    public void setToolStatus(String toolStatus) 
    {
        this.toolStatus = toolStatus;
    }

    public String getToolStatus() 
    {
        return toolStatus;
    }

    public void setIntegrityCheck(String integrityCheck) 
    {
        this.integrityCheck = integrityCheck;
    }

    public String getIntegrityCheck() 
    {
        return integrityCheck;
    }

    public void setSecurityStandards(String securityStandards) 
    {
        this.securityStandards = securityStandards;
    }

    public String getSecurityStandards() 
    {
        return securityStandards;
    }

    public void setJobSafety(String jobSafety) 
    {
        this.jobSafety = jobSafety;
    }

    public String getJobSafety() 
    {
        return jobSafety;
    }

    public void setPhoto(String photo) 
    {
        this.photo = photo;
    }

    public String getPhoto() 
    {
        return photo;
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
            .append("toolId", getToolId())
            .append("toolCode", getToolCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("toolName", getToolName())
            .append("toolType", getToolType())
            .append("toolModel", getToolModel())
            .append("manufacturer", getManufacturer())
            .append("productionDate", getProductionDate())
            .append("purchaseDate", getPurchaseDate())
            .append("toolStatus", getToolStatus())
            .append("integrityCheck", getIntegrityCheck())
            .append("securityStandards", getSecurityStandards())
            .append("jobSafety", getJobSafety())
            .append("photo", getPhoto())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
