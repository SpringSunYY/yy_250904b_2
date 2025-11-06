package com.ruoyi.anticorrosion.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 防腐基础信息对象 equip_anticorrosion_base
 * 
 * @author laogao
 * @date 2025-09-23
 */
public class EquipAnticorrosionBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 设备 */
    private Long equipmentId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipmentName;

    /** 设备位号 */
    @Excel(name = "设备位号")
    private String equipmentCode;

    /** 设备管理级别 */
    @Excel(name = "设备管理级别")
    private String equipLevels;

    /** 设计厚度(mm) */
    @Excel(name = "设计厚度(mm)")
    private BigDecimal originalThickness;

    /** 最小安全厚度(mm) */
    @Excel(name = "最小安全厚度(mm)")
    private BigDecimal minAllowableThickness;

    /** 检测周期 */
    @Excel(name = "检测周期")
    private String defaultInspectionCycle;

    /** 删除标志 */
    private String delFlag;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setEquipmentId(Long equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() 
    {
        return equipmentId;
    }

    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }

    public void setEquipmentCode(String equipmentCode) 
    {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentCode() 
    {
        return equipmentCode;
    }

    public void setEquipLevels(String equipLevels) 
    {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() 
    {
        return equipLevels;
    }

    public void setOriginalThickness(BigDecimal originalThickness) 
    {
        this.originalThickness = originalThickness;
    }

    public BigDecimal getOriginalThickness() 
    {
        return originalThickness;
    }

    public void setMinAllowableThickness(BigDecimal minAllowableThickness) 
    {
        this.minAllowableThickness = minAllowableThickness;
    }

    public BigDecimal getMinAllowableThickness() 
    {
        return minAllowableThickness;
    }

    public void setDefaultInspectionCycle(String defaultInspectionCycle) 
    {
        this.defaultInspectionCycle = defaultInspectionCycle;
    }

    public String getDefaultInspectionCycle() 
    {
        return defaultInspectionCycle;
    }

    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("equipmentId", getEquipmentId())
            .append("equipmentName", getEquipmentName())
            .append("equipmentCode", getEquipmentCode())
            .append("equipLevels", getEquipLevels())
            .append("originalThickness", getOriginalThickness())
            .append("minAllowableThickness", getMinAllowableThickness())
            .append("defaultInspectionCycle", getDefaultInspectionCycle())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("delFlag", getDelFlag())
            .toString();
    }
}
