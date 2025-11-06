package com.ruoyi.repair.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 检维修记录对象 equip_repair_record
 *
 * @author laogao
 * @date 2025-10-24
 */
public class EquipRepairRecord extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Excel(name = "主键ID")
    private Long recordId;

    /**
     * 维修单号
     */
    @Excel(name = "维修单号")
    private String recordNo;

    /**
     * 关联计划
     */
    @Excel(name = "关联计划")
    private Long planId;

    /**
     * 计划单号
     */
    @Excel(name = "计划单号")
    private String planNo;

    /**
     * 检修类型
     */
    @Excel(name = "检修类型")
    private String sourceType;

    /**
     * 设备ID
     */
    @Excel(name = "设备ID")
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
     * 设备管理等级
     */
    @Excel(name = "设备管理等级")
    private String equipLevels;

    /**
     * 责任人id
     */
    @Excel(name = "责任人id")
    private Long responId;

    /**
     * 责任人姓名
     */
    @Excel(name = "责任人姓名")
    private String responName;

    /**
     * 故障描述
     */
    @Excel(name = "故障描述")
    private String faultDisc;

    /**
     * 检修内容
     */
    @Excel(name = "检修内容")
    private String repairContent;

    /**
     * 检修时间
     */
    @Excel(name = "检修时间")
    private String repairTime;

    /**
     * 维修方案
     */
    @Excel(name = "维修方案")
    private String appendix;

    /**
     * 验收时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

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

    /**
     * 设备维修明细信息
     */
    private List<EquipRepairDetail> equipRepairDetailList;

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getRecordNo() {
        return recordNo;
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

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getSourceType() {
        return sourceType;
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

    public void setEquipLevels(String equipLevels) {
        this.equipLevels = equipLevels;
    }

    public String getEquipLevels() {
        return equipLevels;
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

    public void setFaultDisc(String faultDisc) {
        this.faultDisc = faultDisc;
    }

    public String getFaultDisc() {
        return faultDisc;
    }

    public void setRepairContent(String repairContent) {
        this.repairContent = repairContent;
    }

    public String getRepairContent() {
        return repairContent;
    }

    public void setRepairTime(String repairTime) {
        this.repairTime = repairTime;
    }

    public String getRepairTime() {
        return repairTime;
    }

    public void setAppendix(String appendix) {
        this.appendix = appendix;
    }

    public String getAppendix() {
        return appendix;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() {
        return finishTime;
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

    public List<EquipRepairDetail> getEquipRepairDetailList() {
        return equipRepairDetailList;
    }

    public void setEquipRepairDetailList(List<EquipRepairDetail> equipRepairDetailList) {
        this.equipRepairDetailList = equipRepairDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("recordId", getRecordId())
                .append("recordNo", getRecordNo())
                .append("planId", getPlanId())
                .append("planNo", getPlanNo())
                .append("sourceType", getSourceType())
                .append("equipId", getEquipId())
                .append("equipName", getEquipName())
                .append("equipCode", getEquipCode())
                .append("equipLevels", getEquipLevels())
                .append("responId", getResponId())
                .append("responName", getResponName())
                .append("faultDisc", getFaultDisc())
                .append("repairContent", getRepairContent())
                .append("repairTime", getRepairTime())
                .append("appendix", getAppendix())
                .append("finishTime", getFinishTime())
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
                .append("equipRepairDetailList", getEquipRepairDetailList())
                .toString();
    }
}
