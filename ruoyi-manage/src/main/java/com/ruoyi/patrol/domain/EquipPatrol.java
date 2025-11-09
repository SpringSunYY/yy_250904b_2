package com.ruoyi.patrol.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 巡检记录对象 equip_patrol
 *
 * @author ruoyi
 * @date 2025-11-09
 */
public class EquipPatrol extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @Excel(name = "记录ID")
    private Long patrolId;

    /**
     * 巡检单号
     */
    @Excel(name = "巡检单号")
    private String patrolNo;

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
    private String equipCode;

    /**
     * 巡检人ID
     */
    @Excel(name = "巡检人ID")
    private Long userId;

    /**
     * 巡检人姓名
     */
    @Excel(name = "巡检人姓名")
    private String userName;

    /**
     * 巡检时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "巡检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date patrolTime;

    /**
     * 巡检结果
     */
    @Excel(name = "巡检结果")
    private String patrolResult;

    /**
     * 问题描述
     */
    @Excel(name = "问题描述")
    private String problem;

    /**
     * 处理措施
     */
    @Excel(name = "处理措施")
    private String processMeasures;

    /**
     * 处理后照片
     */
    @Excel(name = "处理后照片")
    private String processImage;

    /**
     * 处理人
     */
    @Excel(name = "处理人")
    private String processUserName;
    private Long processUserId;

    /**
     * 状态 (0-待执行, 1-已完成, 2-已关闭)
     */
    @Excel(name = "状态 (0-待执行, 1-已完成, 2-已关闭)")
    private String status;

    /**
     * 巡检记录明细信息
     */
    private List<EquipPatrolDetail> equipPatrolDetailList;

    public void setPatrolId(Long patrolId) {
        this.patrolId = patrolId;
    }

    public Long getPatrolId() {
        return patrolId;
    }

    public void setPatrolNo(String patrolNo) {
        this.patrolNo = patrolNo;
    }

    public String getPatrolNo() {
        return patrolNo;
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

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPatrolTime(Date patrolTime) {
        this.patrolTime = patrolTime;
    }

    public Date getPatrolTime() {
        return patrolTime;
    }

    public void setPatrolResult(String patrolResult) {
        this.patrolResult = patrolResult;
    }

    public String getPatrolResult() {
        return patrolResult;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getProblem() {
        return problem;
    }

    public void setProcessMeasures(String processMeasures) {
        this.processMeasures = processMeasures;
    }

    public String getProcessMeasures() {
        return processMeasures;
    }

    public void setProcessImage(String processImage) {
        this.processImage = processImage;
    }

    public String getProcessImage() {
        return processImage;
    }

    public void setProcessUserId(Long processUserId) {
        this.processUserId = processUserId;
    }

    public Long getProcessUserId() {
        return processUserId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<EquipPatrolDetail> getEquipPatrolDetailList() {
        return equipPatrolDetailList;
    }

    public void setEquipPatrolDetailList(List<EquipPatrolDetail> equipPatrolDetailList) {
        this.equipPatrolDetailList = equipPatrolDetailList;
    }

    public String getProcessUserName() {
        return processUserName;
    }

    public void setProcessUserName(String processUserName) {
        this.processUserName = processUserName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("patrolId", getPatrolId())
                .append("patrolNo", getPatrolNo())
                .append("equipId", getEquipId())
                .append("equipName", getEquipName())
                .append("equipCode", getEquipCode())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("patrolTime", getPatrolTime())
                .append("patrolResult", getPatrolResult())
                .append("problem", getProblem())
                .append("processMeasures", getProcessMeasures())
                .append("processImage", getProcessImage())
                .append("processUserId", getProcessUserId())
                .append("status", getStatus())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("equipPatrolDetailList", getEquipPatrolDetailList())
                .toString();
    }
}
