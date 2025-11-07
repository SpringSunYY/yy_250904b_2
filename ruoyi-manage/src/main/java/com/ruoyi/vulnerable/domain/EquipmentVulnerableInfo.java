package com.ruoyi.vulnerable.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 易损设备信息对象 equipment_vulnerable_info
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public class EquipmentVulnerableInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 易损编号 */
    @Excel(name = "易损编号")
    private String vulnerableNumber;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 易损类型 */
    @Excel(name = "易损类型")
    private String vulnerableType;

    /** 易损件 */
    @Excel(name = "易损件")
    private String vulnerablePart;

    /** 安装位置 */
    @Excel(name = "安装位置")
    private String installationLocation;

    /** 建议更换周期(月) */
    @Excel(name = "建议更换周期(月)")
    private Long replacementCycleMonths;

    /** 当前状态 */
    @Excel(name = "当前状态")
    private String currentStatus;

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

    public void setVulnerableNumber(String vulnerableNumber) 
    {
        this.vulnerableNumber = vulnerableNumber;
    }

    public String getVulnerableNumber() 
    {
        return vulnerableNumber;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setVulnerableType(String vulnerableType) 
    {
        this.vulnerableType = vulnerableType;
    }

    public String getVulnerableType() 
    {
        return vulnerableType;
    }

    public void setVulnerablePart(String vulnerablePart) 
    {
        this.vulnerablePart = vulnerablePart;
    }

    public String getVulnerablePart() 
    {
        return vulnerablePart;
    }

    public void setInstallationLocation(String installationLocation) 
    {
        this.installationLocation = installationLocation;
    }

    public String getInstallationLocation() 
    {
        return installationLocation;
    }

    public void setReplacementCycleMonths(Long replacementCycleMonths) 
    {
        this.replacementCycleMonths = replacementCycleMonths;
    }

    public Long getReplacementCycleMonths() 
    {
        return replacementCycleMonths;
    }

    public void setCurrentStatus(String currentStatus) 
    {
        this.currentStatus = currentStatus;
    }

    public String getCurrentStatus() 
    {
        return currentStatus;
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
            .append("vulnerableNumber", getVulnerableNumber())
            .append("equipmentName", getEquipmentName())
            .append("vulnerableType", getVulnerableType())
            .append("vulnerablePart", getVulnerablePart())
            .append("installationLocation", getInstallationLocation())
            .append("replacementCycleMonths", getReplacementCycleMonths())
            .append("currentStatus", getCurrentStatus())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdTime", getCreatedTime())
            .append("updatedBy", getUpdatedBy())
            .append("updatedTime", getUpdatedTime())
            .toString();
    }
}
