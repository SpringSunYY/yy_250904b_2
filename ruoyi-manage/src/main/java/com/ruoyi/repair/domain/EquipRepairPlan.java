package com.ruoyi.repair.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备检修计划对象 equip_repair_plan
 *
 * @author laogao
 * @date 2025-10-23
 */
public class EquipRepairPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long planId;

    /** 计划编号 */
    @Excel(name = "计划编号")
    private String planNo;

    /** 计划类型 */
    @Excel(name = "计划类型")
    private String planType;

    /** 设备ID */
    @Excel(name = "设备ID")
    private String equipId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** 设备位号 */
    @Excel(name = "设备位号")
    private String equipCode;

    /** 设备管理等级 */
    @Excel(name = "设备管理等级")
    private String equipLevels;

    /** 检修内容 */
    @Excel(name = "检修内容")
    private String planContent;

    /** 检修时间 */
    @Excel(name = "检修时间")
    private String planTime;

    /** 检修费用 */
    @Excel(name = "检修费用")
    private BigDecimal budget;

    /** 检维修方案 */
    @Excel(name = "检维修方案")
    private String appendix;

    /** 计划年度 */
    @Excel(name = "计划年度")
    private String planYear;

    /** 计划月份 */
    @Excel(name = "计划月份")
    private String planMonth;

    /** 申请部门ID */
    @Excel(name = "申请部门ID")
    private Long deptId;

    /** 申请部门 */
    @Excel(name = "申请部门")
    private String deptName;

    /** 申请人ID */
    @Excel(name = "申请人ID")
    private Long applyUserId;

    /** 申请人 */
    @Excel(name = "申请人")
    private String applyUserName;

    /** 任务ID */
    @Excel(name = "任务ID")
    private String taskId;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String processInstanceId;

    /** 流程自定义ID */
    @Excel(name = "流程自定义ID")
    private String deployId;

    public void setPlanId(Long planId)
    {
        this.planId = planId;
    }

    public Long getPlanId()
    {
        return planId;
    }

    public void setPlanNo(String planNo)
    {
        this.planNo = planNo;
    }

    public String getPlanNo()
    {
        return planNo;
    }

    public void setPlanType(String planType)
    {
        this.planType = planType;
    }

    public String getPlanType()
    {
        return planType;
    }

    public void setEquipId(String equipId)
    {
        this.equipId = equipId;
    }

    public String getEquipId()
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

    public void setEquipCode(String equipCode)
    {
        this.equipCode = equipCode;
    }

    public String getEquipCode()
    {
        return equipCode;
    }

    public void setEquipLevels(String equipLevels)
    {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels()
    {
        return equipLevels;
    }

    public void setPlanContent(String planContent)
    {
        this.planContent = planContent;
    }

    public String getPlanContent()
    {
        return planContent;
    }

    public void setPlanTime(String planTime)
    {
        this.planTime = planTime;
    }

    public String getPlanTime()
    {
        return planTime;
    }

    public void setBudget(BigDecimal budget)
    {
        this.budget = budget;
    }

    public BigDecimal getBudget()
    {
        return budget;
    }

    public void setAppendix(String appendix)
    {
        this.appendix = appendix;
    }

    public String getAppendix()
    {
        return appendix;
    }

    public void setPlanYear(String planYear)
    {
        this.planYear = planYear;
    }

    public String getPlanYear()
    {
        return planYear;
    }

    public void setPlanMonth(String planMonth)
    {
        this.planMonth = planMonth;
    }

    public String getPlanMonth()
    {
        return planMonth;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setApplyUserId(Long applyUserId)
    {
        this.applyUserId = applyUserId;
    }

    public Long getApplyUserId()
    {
        return applyUserId;
    }

    public void setApplyUserName(String applyUserName)
    {
        this.applyUserName = applyUserName;
    }

    public String getApplyUserName()
    {
        return applyUserName;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    public String getTaskId()
    {
        return taskId;
    }

    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId()
    {
        return processInstanceId;
    }

    public void setDeployId(String deployId)
    {
        this.deployId = deployId;
    }

    public String getDeployId()
    {
        return deployId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("planId", getPlanId())
            .append("planNo", getPlanNo())
            .append("planType", getPlanType())
            .append("equipId", getEquipId())
            .append("equipName", getEquipName())
            .append("equipCode", getEquipCode())
            .append("equipLevels", getEquipLevels())
            .append("planContent", getPlanContent())
            .append("planTime", getPlanTime())
            .append("budget", getBudget())
            .append("appendix", getAppendix())
            .append("planYear", getPlanYear())
            .append("planMonth", getPlanMonth())
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
