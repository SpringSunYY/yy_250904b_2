package com.ruoyi.lubr.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设备润滑记录对象 equip_lubr_record2
 *
 * @author laogao
 * @date 2025-10-30
 */
public class EquipLubrRecord2 extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 润滑记录ID
     */
    @Excel(name = "润滑记录ID")
    private Long recordId;

    /**
     * 计划ID
     */
    @Excel(name = "计划ID")
    private Long planId;

    /**
     * 计划编号
     */
    @Excel(name = "计划编号")
    private String planNo;

    /**
     * 设备编号
     */
    @Excel(name = "设备编号")
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
    private String equipCode;

    /**
     * 管理级别
     */
    @Excel(name = "管理级别")
    private String equipLevels;

    /**
     * 实际数量(L)
     */
    @Excel(name = "实际数量(L)")
    private BigDecimal actualQuantity;

    /**
     * 润滑日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "润滑日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lubDate;

    /**
     * 润滑人ID
     */
    @Excel(name = "润滑人ID")
    private String operatorId;

    /**
     * 润滑执行人
     */
    @Excel(name = "润滑执行人")
    private String operatorName;

    /**
     * 是否按时执行
     */
    @Excel(name = "是否按时执行")
    private String isOnTime;

    /**
     * 延迟天数
     */
    @Excel(name = "延迟天数")
    private Long delayDays;

    /**
     * 延迟原因
     */
    @Excel(name = "延迟原因")
    private String delayReason;

    /**
     * 执行情况
     */
    @Excel(name = "执行情况")
    private String executionCondition;

    /**
     * 现场照片
     */
    @Excel(name = "现场照片")
    private String appendix;

    /**
     * 复核人ID
     */
    @Excel(name = "复核人ID")
    private String reviewerId;

    /**
     * 复核人
     */
    @Excel(name = "复核人")
    private String reviewerName;

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

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

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

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode;
    }

    public String getEquipCode() {
        return equipCode;
    }

    public void setEquipLevels(String equipLevels) {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() {
        return equipLevels;
    }

    public void setActualQuantity(BigDecimal actualQuantity) {
        this.actualQuantity = actualQuantity;
    }

    public BigDecimal getActualQuantity() {
        return actualQuantity;
    }

    public void setLubDate(Date lubDate) {
        this.lubDate = lubDate;
    }

    public Date getLubDate() {
        return lubDate;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setIsOnTime(String isOnTime) {
        this.isOnTime = isOnTime;
    }

    public String getIsOnTime() {
        return isOnTime;
    }

    public void setDelayDays(Long delayDays) {
        this.delayDays = delayDays;
    }

    public Long getDelayDays() {
        return delayDays;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setExecutionCondition(String executionCondition) {
        this.executionCondition = executionCondition;
    }

    public String getExecutionCondition() {
        return executionCondition;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerName() {
        return reviewerName;
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
                .append("recordId", getRecordId())
                .append("planId", getPlanId())
                .append("planNo", getPlanNo())
                .append("equipId", getEquipId())
                .append("equipName", getEquipName())
                .append("equipCode", getEquipCode())
                .append("equipLevels", getEquipLevels())
                .append("actualQuantity", getActualQuantity())
                .append("lubDate", getLubDate())
                .append("operatorId", getOperatorId())
                .append("operatorName", getOperatorName())
                .append("isOnTime", getIsOnTime())
                .append("delayDays", getDelayDays())
                .append("delayReason", getDelayReason())
                .append("executionCondition", getExecutionCondition())
                .append("appendix", getAppendix())
                .append("reviewerId", getReviewerId())
                .append("reviewerName", getReviewerName())
                .append("remark", getRemark())
                .append("applyUserId", getApplyUserId())
                .append("applyUserName", getApplyUserName())
                .append("taskId", getTaskId())
                .append("processInstanceId", getProcessInstanceId())
                .append("deployId", getDeployId())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
