package com.ruoyi.inspe.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备检查记录对象 equip_inspe
 *
 * @author laogao
 * @date 2025-10-22
 */
public class EquipInspe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 检查记录ID */
    @Excel(name = "检查记录ID")
    private Long inspeId;

    /** 检查单号 */
    @Excel(name = "检查单号")
    private String inspeNo;

    /** 检查日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspeDate;

    /** 检查类型 */
    @Excel(name = "检查类型")
    private String inspeType;

    /** 检查组长 */
    @Excel(name = "检查组长")
    private String teamLeaderId;

    /** 参与人员 */
    @Excel(name = "参与人员")
    private String participantsIds;

    /** 有无隐患 */
    @Excel(name = "有无隐患")
    private String hasDanger;

    /** 隐患数量 */
    @Excel(name = "隐患数量")
    private String dangerCoun;

    /** 隐患照片 */
    @Excel(name = "隐患照片")
    private String appendix;

    /** 整体评价 */
    @Excel(name = "整体评价")
    private String overallStatus;

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

    /**
     * 任务ID
     */
    @Excel(name = "任务ID")
    private String taskId;

    /** 流程自定义ID */
    @Excel(name = "流程自定义ID")
    private String deployId;

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String processInstanceId;

    public void setInspeId(Long inspeId)
    {
        this.inspeId = inspeId;
    }

    public Long getInspeId()
    {
        return inspeId;
    }

    public void setInspeNo(String inspeNo)
    {
        this.inspeNo = inspeNo;
    }

    public String getInspeNo()
    {
        return inspeNo;
    }

    public void setInspeDate(Date inspeDate)
    {
        this.inspeDate = inspeDate;
    }

    public Date getInspeDate()
    {
        return inspeDate;
    }

    public void setInspeType(String inspeType)
    {
        this.inspeType = inspeType;
    }

    public String getInspeType()
    {
        return inspeType;
    }

    public void setTeamLeaderId(String teamLeaderId)
    {
        this.teamLeaderId = teamLeaderId;
    }

    public String getTeamLeaderId()
    {
        return teamLeaderId;
    }

    public void setParticipantsIds(String participantsIds)
    {
        this.participantsIds = participantsIds;
    }

    public String getParticipantsIds()
    {
        return participantsIds;
    }

    public void setHasDanger(String hasDanger)
    {
        this.hasDanger = hasDanger;
    }

    public String getHasDanger()
    {
        return hasDanger;
    }

    public void setDangerCoun(String dangerCoun)
    {
        this.dangerCoun = dangerCoun;
    }

    public String getDangerCoun()
    {
        return dangerCoun;
    }

    public void setAppendix(String appendix)
    {
        this.appendix = appendix;
    }

    public String getAppendix()
    {
        return appendix;
    }

    public void setOverallStatus(String overallStatus)
    {
        this.overallStatus = overallStatus;
    }

    public String getOverallStatus()
    {
        return overallStatus;
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

    public void setDeployId(String deployId)
    {
        this.deployId = deployId;
    }

    public String getDeployId()
    {
        return deployId;
    }

    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId()
    {
        return processInstanceId;
    }


    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("inspeId", getInspeId())
            .append("inspeNo", getInspeNo())
            .append("inspeDate", getInspeDate())
            .append("inspeType", getInspeType())
            .append("teamLeaderId", getTeamLeaderId())
            .append("participantsIds", getParticipantsIds())
            .append("hasDanger", getHasDanger())
            .append("dangerCoun", getDangerCoun())
            .append("appendix", getAppendix())
            .append("overallStatus", getOverallStatus())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("applyUserId", getApplyUserId())
            .append("applyUserName", getApplyUserName())
            .append("deployId", getDeployId())
            .append("processInstanceId", getProcessInstanceId())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
