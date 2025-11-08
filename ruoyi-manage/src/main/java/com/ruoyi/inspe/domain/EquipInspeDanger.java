package com.ruoyi.inspe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备隐患治理对象 equip_inspe_danger
 *
 * @author laogao
 * @date 2025-10-23
 */
public class EquipInspeDanger extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 隐患ID
     */
    @Excel(name = "隐患ID")
    private Long dangerId;

    /**
     * 检查记录ID
     */
    @Excel(name = "检查记录ID")
    private String inspeId;

    /**
     * 检查单号
     */
    @Excel(name = "检查单号")
    private String inspeNo;

    /**
     * 隐患编号
     */
    @Excel(name = "隐患编号")
    private String dangerNo;

    /**
     * 设备id
     */
    @Excel(name = "设备id")
    private String equipId;

    /**
     * 设备名称
     */
    @Excel(name = "设备名称")
    private String equipName;

    /**
     * 设备位号
     */
    @Excel(name = "设备位号")
    private String equipCode;

    /**
     * 隐患等级
     */
    @Excel(name = "隐患等级")
    private String dangerLevel;

    /**
     * 隐患描述
     */
    @Excel(name = "隐患描述")
    private String description;

    /**
     * 责任人ID
     */
    @Excel(name = "责任人ID")
    private Long responId;

    /**
     * 责任人
     */
    @Excel(name = "责任人")
    private String responName;

    /**
     * 整改期限
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改期限", width = 30, dateFormat = "yyyy-MM-dd")
    private Date deadline;

    /**
     * 验收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalTime;

    /**
     * 验收结果
     */
    @Excel(name = "验收结果")
    private String approvalResult;

    /**
     * 奖励用户
     */
    @Excel(name = "奖励用户")
    private String awardUserName;
    private Long awardUserId;

    /**
     * 奖励部门
     */
    @Excel(name = "奖励部门")
    private String awardDeptName;
    private Long awardDeptId;

    /**
     * 奖励金额
     */
    @Excel(name = "奖励金额")
    private BigDecimal awardAmount;

    /**
     * 惩罚用户
     */
    @Excel(name = "惩罚用户")
    private String punishUserName;
    private Long punishUserId;

    /**
     * 惩罚部门
     */
    @Excel(name = "惩罚部门")
    private String punishDeptName;
    private Long punishDeptId;

    /**
     * 惩罚金额
     */
    @Excel(name = "惩罚金额")
    private BigDecimal punishAmount;

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

    public void setDangerId(Long dangerId) {
        this.dangerId = dangerId;
    }

    public Long getDangerId() {
        return dangerId;
    }

    public void setInspeId(String inspeId) {
        this.inspeId = inspeId;
    }

    public String getInspeId() {
        return inspeId;
    }

    public void setInspeNo(String inspeNo) {
        this.inspeNo = inspeNo;
    }

    public String getInspeNo() {
        return inspeNo;
    }

    public void setDangerNo(String dangerNo) {
        this.dangerNo = dangerNo;
    }

    public String getDangerNo() {
        return dangerNo;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipName() {
        return equipName;
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setResponId(Long responId) {
        this.responId = responId;
    }

    public Long getResponId() {
        return responId;
    }

    public void setResponName(String responName) {
        this.responName = responName;
    }

    public String getResponName() {
        return responName;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setApprovalTime(Date approvalTime) {
        this.approvalTime = approvalTime;
    }

    public Date getApprovalTime() {
        return approvalTime;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult;
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setAwardUserId(Long awardUserId) {
        this.awardUserId = awardUserId;
    }

    public Long getAwardUserId() {
        return awardUserId;
    }

    public void setAwardDeptId(Long awardDeptId) {
        this.awardDeptId = awardDeptId;
    }

    public Long getAwardDeptId() {
        return awardDeptId;
    }

    public void setAwardAmount(BigDecimal awardAmount) {
        this.awardAmount = awardAmount;
    }

    public BigDecimal getAwardAmount() {
        return awardAmount;
    }

    public void setPunishUserId(Long punishUserId) {
        this.punishUserId = punishUserId;
    }

    public Long getPunishUserId() {
        return punishUserId;
    }

    public void setPunishDeptId(Long punishDeptId) {
        this.punishDeptId = punishDeptId;
    }

    public Long getPunishDeptId() {
        return punishDeptId;
    }

    public void setPunishAmount(BigDecimal punishAmount) {
        this.punishAmount = punishAmount;
    }

    public BigDecimal getPunishAmount() {
        return punishAmount;
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

    public String getAwardUserName() {
        return awardUserName;
    }

    public void setAwardUserName(String awardUserName) {
        this.awardUserName = awardUserName;
    }

    public String getAwardDeptName() {
        return awardDeptName;
    }

    public void setAwardDeptName(String awardDeptName) {
        this.awardDeptName = awardDeptName;
    }

    public String getPunishUserName() {
        return punishUserName;
    }

    public void setPunishUserName(String punishUserName) {
        this.punishUserName = punishUserName;
    }

    public String getPunishDeptName() {
        return punishDeptName;
    }

    public void setPunishDeptName(String punishDeptName) {
        this.punishDeptName = punishDeptName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("dangerId", getDangerId())
                .append("inspeId", getInspeId())
                .append("inspeNo", getInspeNo())
                .append("dangerNo", getDangerNo())
                .append("equipId", getEquipId())
                .append("equipName", getEquipName())
                .append("equipCode", getEquipCode())
                .append("dangerLevel", getDangerLevel())
                .append("description", getDescription())
                .append("responId", getResponId())
                .append("responName", getResponName())
                .append("deadline", getDeadline())
                .append("approvalTime", getApprovalTime())
                .append("approvalResult", getApprovalResult())
                .append("awardUserId", getAwardUserId())
                .append("awardDeptId", getAwardDeptId())
                .append("awardAmount", getAwardAmount())
                .append("punishUserId", getPunishUserId())
                .append("punishDeptId", getPunishDeptId())
                .append("punishAmount", getPunishAmount())
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
