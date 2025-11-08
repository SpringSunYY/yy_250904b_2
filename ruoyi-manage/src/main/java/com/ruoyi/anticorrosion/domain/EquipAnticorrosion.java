package com.ruoyi.anticorrosion.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 防腐蚀检测对象 equip_anticorrosion
 *
 * @author laogao
 * @date 2025-11-08
 */
public class EquipAnticorrosion extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Excel(name = "主键ID")
    private Long id;

    /**
     * 记录编号
     */
    @Excel(name = "记录编号")
    private String recordNo;

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
    private Long equipLevels;

    /**
     * 检测日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检测日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionDate;

    /**
     * 检测部门id
     */
    @Excel(name = "检测部门id")
    private Long inspectionId;

    /**
     * 检测部门
     */
    @Excel(name = "检测部门")
    private String inspectionDept;

    /**
     * 检测人id
     */
    @Excel(name = "检测人id")
    private String inspectorId;

    /**
     * 检测人
     */
    @Excel(name = "检测人")
    private String inspector;

    /**
     * 测点1厚度(mm)
     */
    @Excel(name = "测点1厚度(mm)")
    private BigDecimal point1Thickness;

    /**
     * 测点2厚度(mm)
     */
    @Excel(name = "测点2厚度(mm)")
    private BigDecimal point2Thickness;

    /**
     * 测点3厚度(mm)
     */
    @Excel(name = "测点3厚度(mm)")
    private BigDecimal point3Thickness;

    /**
     * 腐蚀速率
     */
    @Excel(name = "腐蚀速率")
    private BigDecimal corrosionRate;

    /**
     * 防腐蚀评估
     */
    @Excel(name = "防腐蚀评估")
    private String corrosionAssessment;

    /**
     * 剩余使用寿命
     */
    @Excel(name = "剩余使用寿命")
    private String remainingUsefulLife;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getRecordNo() {
        return recordNo;
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

    public void setEquipLevels(Long equipLevels) {
        this.equipLevels = equipLevels;
    }

    public Long getEquipLevels() {
        return equipLevels;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionId(Long inspectionId) {
        this.inspectionId = inspectionId;
    }

    public Long getInspectionId() {
        return inspectionId;
    }

    public void setInspectionDept(String inspectionDept) {
        this.inspectionDept = inspectionDept;
    }

    public String getInspectionDept() {
        return inspectionDept;
    }

    public void setInspectorId(String inspectorId) {
        this.inspectorId = inspectorId;
    }

    public String getInspectorId() {
        return inspectorId;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    public String getInspector() {
        return inspector;
    }

    public void setPoint1Thickness(BigDecimal point1Thickness) {
        this.point1Thickness = point1Thickness;
    }

    public BigDecimal getPoint1Thickness() {
        return point1Thickness;
    }

    public void setPoint2Thickness(BigDecimal point2Thickness) {
        this.point2Thickness = point2Thickness;
    }

    public BigDecimal getPoint2Thickness() {
        return point2Thickness;
    }

    public void setPoint3Thickness(BigDecimal point3Thickness) {
        this.point3Thickness = point3Thickness;
    }

    public BigDecimal getPoint3Thickness() {
        return point3Thickness;
    }

    public void setCorrosionRate(BigDecimal corrosionRate) {
        this.corrosionRate = corrosionRate;
    }

    public BigDecimal getCorrosionRate() {
        return corrosionRate;
    }

    public void setCorrosionAssessment(String corrosionAssessment) {
        this.corrosionAssessment = corrosionAssessment;
    }

    public String getCorrosionAssessment() {
        return corrosionAssessment;
    }

    public void setRemainingUsefulLife(String remainingUsefulLife) {
        this.remainingUsefulLife = remainingUsefulLife;
    }

    public String getRemainingUsefulLife() {
        return remainingUsefulLife;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("recordNo", getRecordNo())
                .append("equipmentId", getEquipmentId())
                .append("equipmentName", getEquipmentName())
                .append("equipmentCode", getEquipmentCode())
                .append("equipLevels", getEquipLevels())
                .append("inspectionDate", getInspectionDate())
                .append("inspectionId", getInspectionId())
                .append("inspectionDept", getInspectionDept())
                .append("inspectorId", getInspectorId())
                .append("inspector", getInspector())
                .append("point1Thickness", getPoint1Thickness())
                .append("point2Thickness", getPoint2Thickness())
                .append("point3Thickness", getPoint3Thickness())
                .append("corrosionRate", getCorrosionRate())
                .append("corrosionAssessment", getCorrosionAssessment())
                .append("remainingUsefulLife", getRemainingUsefulLife())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
