package com.ruoyi.anticorrosion.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 防腐基础信息对象 equip_anticorrosion_base
 *
 * @author laogao
 * @date 2025-11-08
 */
public class EquipAnticorrosionBase extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Excel(name = "主键ID")
    private Long id;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
    private Long equipmentId;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipmentName;

    /**
     * 设备位号
     */
    @Excel(name = "设备位号")
    private String equipmentCode;

    /**
     * 设备管理级别
     */
    @Excel(name = "设备管理级别")
    private String equipLevels;

    /**
     * 规格型号
     */
    @Excel(name = "规格型号")
    private String specification;

    /**
     * 易腐蚀部位
     */
    @Excel(name = "易腐蚀部位")
    private String corrosionParts;

    /**
     * 材质
     */
    @Excel(name = "材质")
    private String material;

    /**
     * 介质
     */
    @Excel(name = "介质")
    private String medium;

    /**
     * 温度
     */
    @Excel(name = "温度")
    private BigDecimal temperature;

    /**
     * 压力
     */
    @Excel(name = "压力")
    private BigDecimal pressure;

    /**
     * 设计厚度(mm)
     */
    @Excel(name = "设计厚度(mm)")
    private BigDecimal originalThickness;

    /**
     * 最小安全厚度(mm)
     */
    @Excel(name = "最小安全厚度(mm)")
    private BigDecimal minAllowableThickness;

    /**
     * 检测周期
     */
    @Excel(name = "检测周期")
    private String defaultInspectionCycle;

    /**
     * 控制措施
     */
    @Excel(name = "控制措施")
    private String controlMeasures;

    /**
     * 删除标志
     */
    private String delFlag;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipLevels(String equipLevels) {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() {
        return equipLevels;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getSpecification() {
        return specification;
    }

    public void setCorrosionParts(String corrosionParts) {
        this.corrosionParts = corrosionParts;
    }

    public String getCorrosionParts() {
        return corrosionParts;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getMaterial() {
        return material;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getMedium() {
        return medium;
    }

    public void setTemperature(BigDecimal temperature) {
        this.temperature = temperature;
    }

    public BigDecimal getTemperature() {
        return temperature;
    }

    public void setPressure(BigDecimal pressure) {
        this.pressure = pressure;
    }

    public BigDecimal getPressure() {
        return pressure;
    }

    public void setOriginalThickness(BigDecimal originalThickness) {
        this.originalThickness = originalThickness;
    }

    public BigDecimal getOriginalThickness() {
        return originalThickness;
    }

    public void setMinAllowableThickness(BigDecimal minAllowableThickness) {
        this.minAllowableThickness = minAllowableThickness;
    }

    public BigDecimal getMinAllowableThickness() {
        return minAllowableThickness;
    }

    public void setDefaultInspectionCycle(String defaultInspectionCycle) {
        this.defaultInspectionCycle = defaultInspectionCycle;
    }

    public String getDefaultInspectionCycle() {
        return defaultInspectionCycle;
    }

    public void setControlMeasures(String controlMeasures) {
        this.controlMeasures = controlMeasures;
    }

    public String getControlMeasures() {
        return controlMeasures;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("equipmentId", getEquipmentId())
                .append("equipmentName", getEquipmentName())
                .append("equipmentCode", getEquipmentCode())
                .append("equipLevels", getEquipLevels())
                .append("specification", getSpecification())
                .append("corrosionParts", getCorrosionParts())
                .append("material", getMaterial())
                .append("medium", getMedium())
                .append("temperature", getTemperature())
                .append("pressure", getPressure())
                .append("originalThickness", getOriginalThickness())
                .append("minAllowableThickness", getMinAllowableThickness())
                .append("defaultInspectionCycle", getDefaultInspectionCycle())
                .append("controlMeasures", getControlMeasures())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("delFlag", getDelFlag())
                .toString();
    }
}
