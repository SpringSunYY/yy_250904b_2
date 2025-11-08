package com.ruoyi.inspe.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 设备检查记录对象 equip_inspe
 *
 * @author laogao
 * @date 2025-11-08
 */
public class EquipInspe extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 检查记录ID
     */
    @Excel(name = "检查记录ID")
    private Long inspeId;

    /**
     * 检查单号
     */
    @Excel(name = "检查单号")
    private String inspeNo;

    /**
     * 检查日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "检查日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspeDate;

    /**
     * 检查类型
     */
    @Excel(name = "检查类型")
    private String inspeType;

    /**
     * 检查组长
     */
    @Excel(name = "检查组长")
    private String teamLeaderId;

    /**
     * 参与人员
     */
    @Excel(name = "参与人员")
    private String participantsIds;

    /**
     * 有无隐患
     */
    @Excel(name = "有无隐患")
    private String hasDanger;

    /**
     * 隐患数量
     */
    @Excel(name = "隐患数量")
    private String dangerCoun;

    /**
     * 整体评价
     */
    @Excel(name = "整体评价")
    private String overallStatus;

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

    public void setInspeId(Long inspeId) {
        this.inspeId = inspeId;
    }

    public Long getInspeId() {
        return inspeId;
    }

    public void setInspeNo(String inspeNo) {
        this.inspeNo = inspeNo;
    }

    public String getInspeNo() {
        return inspeNo;
    }

    public void setInspeDate(Date inspeDate) {
        this.inspeDate = inspeDate;
    }

    public Date getInspeDate() {
        return inspeDate;
    }

    public void setInspeType(String inspeType) {
        this.inspeType = inspeType;
    }

    public String getInspeType() {
        return inspeType;
    }

    public void setTeamLeaderId(String teamLeaderId) {
        this.teamLeaderId = teamLeaderId;
    }

    public String getTeamLeaderId() {
        return teamLeaderId;
    }

    public void setParticipantsIds(String participantsIds) {
        this.participantsIds = participantsIds;
    }

    public String getParticipantsIds() {
        return participantsIds;
    }

    public void setHasDanger(String hasDanger) {
        this.hasDanger = hasDanger;
    }

    public String getHasDanger() {
        return hasDanger;
    }

    public void setDangerCoun(String dangerCoun) {
        this.dangerCoun = dangerCoun;
    }

    public String getDangerCoun() {
        return dangerCoun;
    }

    public void setOverallStatus(String overallStatus) {
        this.overallStatus = overallStatus;
    }

    public String getOverallStatus() {
        return overallStatus;
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

    public String getProcessUserName() {
        return processUserName;
    }

    public void setProcessUserName(String processUserName) {
        this.processUserName = processUserName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("inspeId", getInspeId())
                .append("inspeNo", getInspeNo())
                .append("inspeDate", getInspeDate())
                .append("inspeType", getInspeType())
                .append("teamLeaderId", getTeamLeaderId())
                .append("participantsIds", getParticipantsIds())
                .append("hasDanger", getHasDanger())
                .append("dangerCoun", getDangerCoun())
                .append("overallStatus", getOverallStatus())
                .append("processMeasures", getProcessMeasures())
                .append("processImage", getProcessImage())
                .append("processUserId", getProcessUserId())
                .append("remark", getRemark())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .toString();
    }
}
