package com.ruoyi.seal.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 密封点对象 equip_seal_base
 * 
 * @author laogao
 * @date 2025-09-23
 */
public class EquipSealBase extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long baseId;

    /** 设备 */
    private Long equipId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** 设备管理级别 */
    @Excel(name = "设备管理级别")
    private String equipLevels;

    /** 静密封点数量 */
    @Excel(name = "静密封点数量")
    private Long staticSealCount;

    /** 动密封点数量 */
    @Excel(name = "动密封点数量")
    private Long dynamicSealCount;

    /** 密封点总数 */
    @Excel(name = "密封点总数")
    private Long totalSealCount;

    public void setBaseId(Long baseId) 
    {
        this.baseId = baseId;
    }

    public Long getBaseId() 
    {
        return baseId;
    }

    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }

    public void setEquipName(String equipName) 
    {
        this.equipName = equipName;
    }

    public String getEquipName() 
    {
        return equipName;
    }

    public void setEquipLevels(String equipLevels) 
    {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() 
    {
        return equipLevels;
    }

    public void setStaticSealCount(Long staticSealCount) 
    {
        this.staticSealCount = staticSealCount;
    }

    public Long getStaticSealCount() 
    {
        return staticSealCount;
    }

    public void setDynamicSealCount(Long dynamicSealCount) 
    {
        this.dynamicSealCount = dynamicSealCount;
    }

    public Long getDynamicSealCount() 
    {
        return dynamicSealCount;
    }

    public void setTotalSealCount(Long totalSealCount) 
    {
        this.totalSealCount = totalSealCount;
    }

    public Long getTotalSealCount() 
    {
        return totalSealCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("baseId", getBaseId())
            .append("equipId", getEquipId())
            .append("equipName", getEquipName())
            .append("equipLevels", getEquipLevels())
            .append("staticSealCount", getStaticSealCount())
            .append("dynamicSealCount", getDynamicSealCount())
            .append("totalSealCount", getTotalSealCount())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
