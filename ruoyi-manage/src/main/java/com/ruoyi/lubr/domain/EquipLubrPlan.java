package com.ruoyi.lubr.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 润滑计划对象 equip_lubr_plan
 *
 * @author laogao
 * @date 2025-10-24
 */
public class EquipLubrPlan extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 润滑计划ID
     */
    @Excel(name = "润滑计划ID")
    private Long planId;

    /**
     * 计划编号
     */
    @Excel(name = "计划编号")
    private String planNo;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
    private Long equipId;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipName;

    /**
     * 设备位号
     */
    @Excel(name = "设备位号")
    private String equCode;

    /**
     * 管理级别
     */
    @Excel(name = "管理级别")
    private String equipLevels;

    /**
     * 润滑部位
     */
    @Excel(name = "润滑部位")
    private String lubLocation;

    /**
     * 润滑点数
     */
    @Excel(name = "润滑点数")
    private Long lubPoints;

    /**
     * 润滑周期
     */
    @Excel(name = "润滑周期")
    private String lubInterval;

    /**
     * 润滑油类别
     */
    @Excel(name = "润滑油类别")
    private String lubCategory;

    /**
     * 润滑油牌号
     */
    @Excel(name = "润滑油牌号")
    private String lubGrade;

    /**
     * 加注方式
     */
    @Excel(name = "加注方式")
    private String lubMethod;

    /**
     * 标准数量(L)
     */
    @Excel(name = "标准数量(L)")
    private BigDecimal standardQuantity;

    /**
     * 负责人ID
     */
    @Excel(name = "负责人ID")
    private Long responsibleId;

    /**
     * 负责人
     */
    @Excel(name = "负责人")
    private String responsibleName;

    /**
     * 申请部门ID
     */
    @Excel(name = "申请部门ID")
    private Long deptId;

    /**
     * 申请部门
     */
    @Excel(name = "申请部门")
    private String deptName;

    /**
     * 申请人ID
     */
    @Excel(name = "申请人ID")
    private Long applyUserId;

    /**
     * 申请人
     */
    @Excel(name = "申请人")
    private String applyUserName;

    /**
     * 任务ID
     */
    @Excel(name = "任务ID")
    private String taskId;

    /**
     * 流程实例ID
     */
    @Excel(name = "流程实例ID")
    private String processInstanceId;

    /**
     * 流程自定义ID
     */
    @Excel(name = "流程自定义ID")
    private String deployId;

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setEquipId(Long equipId) {
        this.equipId = equipId;
    }

    public Long getEquipId() {
        return equipId;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquCode(String equCode) {
        this.equCode = equCode;
    }

    public String getEquCode() {
        return equCode;
    }

    public void setEquipLevels(String equipLevels) {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() {
        return equipLevels;
    }

    public void setLubLocation(String lubLocation) {
        this.lubLocation = lubLocation;
    }

    public String getLubLocation() {
        return lubLocation;
    }

    public void setLubPoints(Long lubPoints) {
        this.lubPoints = lubPoints;
    }

    public Long getLubPoints() {
        return lubPoints;
    }

    public void setLubInterval(String lubInterval) {
        this.lubInterval = lubInterval;
    }

    public String getLubInterval() {
        return lubInterval;
    }

    public void setLubCategory(String lubCategory) {
        this.lubCategory = lubCategory;
    }

    public String getLubCategory() {
        return lubCategory;
    }

    public void setLubGrade(String lubGrade) {
        this.lubGrade = lubGrade;
    }

    public String getLubGrade() {
        return lubGrade;
    }

    public void setLubMethod(String lubMethod) {
        this.lubMethod = lubMethod;
    }

    public String getLubMethod() {
        return lubMethod;
    }

    public void setStandardQuantity(BigDecimal standardQuantity) {
        this.standardQuantity = standardQuantity;
    }

    public BigDecimal getStandardQuantity() {
        return standardQuantity;
    }

    public void setResponsibleId(Long responsibleId) {
        this.responsibleId = responsibleId;
    }

    public Long getResponsibleId() {
        return responsibleId;
    }

    public void setResponsibleName(String responsibleName) {
        this.responsibleName = responsibleName;
    }

    public String getResponsibleName() {
        return responsibleName;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }

    public Long getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setDeployId(String deployId) {
        this.deployId = deployId;
    }

    public String getDeployId() {
        return deployId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("planId", getPlanId())
                .append("planNo", getPlanNo())
                .append("equipId", getEquipId())
                .append("equipName", getEquipName())
                .append("equCode", getEquCode())
                .append("equipLevels", getEquipLevels())
                .append("lubLocation", getLubLocation())
                .append("lubPoints", getLubPoints())
                .append("lubInterval", getLubInterval())
                .append("lubCategory", getLubCategory())
                .append("lubGrade", getLubGrade())
                .append("lubMethod", getLubMethod())
                .append("standardQuantity", getStandardQuantity())
                .append("responsibleId", getResponsibleId())
                .append("responsibleName", getResponsibleName())
                .append("deptId", getDeptId())
                .append("deptName", getDeptName())
                .append("applyUserId", getApplyUserId())
                .append("applyUserName", getApplyUserName())
                .append("taskId", getTaskId())
                .append("processInstanceId", getProcessInstanceId())
                .append("deployId", getDeployId())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
