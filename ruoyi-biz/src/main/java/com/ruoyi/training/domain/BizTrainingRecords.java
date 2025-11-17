package com.ruoyi.training.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 培训记录对象 biz_training_records
 *
 * @author ruoyi
 * @date 2025-10-24
 */
public class BizTrainingRecords extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long id;

    /**
     * 选择编号
     */
    @Excel(name = "选择编号")
    private Long choiceId;

    /**
     * 培训编号
     */
    @Excel(name = "培训编号")
    private String trainingCode;

    /**
     * 培训名称
     */
    @Excel(name = "培训名称")
    private String trainingName;

    /**
     * 项目名称
     */
    @Excel(name = "项目名称")
    private String projectName;

    /**
     * 承包商
     */
    @Excel(name = "承包商")
    private String contractorName;

    /**
     * 培训类型
     */
    @Excel(name = "培训类型")
    private String trainingType;

    /**
     * 培训日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "培训日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date trainingDate;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "开始时间", width = 30, dateFormat = "HH:mm")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "结束时间", width = 30, dateFormat = "HH:mm")
    private Date endTime;

    /**
     * 培训学时
     */
    @Excel(name = "培训学时")
    private BigDecimal trainingHours;

    /**
     * 培训内容
     */
    @Excel(name = "培训内容")
    private String trainingContent;

    /**
     * 培训部门
     */
    @Excel(name = "培训部门")
    private String trainingDepartment;

    /**
     * 培训讲师
     */
    @Excel(name = "培训讲师")
    private String trainer;

    /**
     * 培训地点
     */
    @Excel(name = "培训地点")
    private String trainingLocation;

    /**
     * 计划参训人数
     */
    @Excel(name = "计划参训人数")
    private Long plannedAttendees;

    /**
     * 实际参训人数
     */
    @Excel(name = "实际参训人数")
    private Long actualAttendees;

    /**
     * 培训状态
     */
    @Excel(name = "培训状态")
    private String trainingStatus;

    /**
     * 培训照片
     */
    @Excel(name = "培训照片")
    private String trainingPictures;

    /**
     * 培训合格证明
     */
    @Excel(name = "培训合格证明")
    private String qualificationCertificate;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 创建者
     */
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /**
     * 更新者
     */
    private String updatedBy;

    /**
     * 更新时间
     */
    private Date updatedAt;

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTrainingCode(String trainingCode) {
        this.trainingCode = trainingCode;
    }

    public String getTrainingCode() {
        return trainingCode;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setTrainingType(String trainingType) {
        this.trainingType = trainingType;
    }

    public String getTrainingType() {
        return trainingType;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setTrainingHours(BigDecimal trainingHours) {
        this.trainingHours = trainingHours;
    }

    public BigDecimal getTrainingHours() {
        return trainingHours;
    }

    public void setTrainingContent(String trainingContent) {
        this.trainingContent = trainingContent;
    }

    public String getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingDepartment(String trainingDepartment) {
        this.trainingDepartment = trainingDepartment;
    }

    public String getTrainingDepartment() {
        return trainingDepartment;
    }

    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    public String getTrainer() {
        return trainer;
    }

    public void setTrainingLocation(String trainingLocation) {
        this.trainingLocation = trainingLocation;
    }

    public String getTrainingLocation() {
        return trainingLocation;
    }

    public void setPlannedAttendees(Long plannedAttendees) {
        this.plannedAttendees = plannedAttendees;
    }

    public Long getPlannedAttendees() {
        return plannedAttendees;
    }

    public void setActualAttendees(Long actualAttendees) {
        this.actualAttendees = actualAttendees;
    }

    public Long getActualAttendees() {
        return actualAttendees;
    }

    public void setTrainingStatus(String trainingStatus) {
        this.trainingStatus = trainingStatus;
    }

    public String getTrainingStatus() {
        return trainingStatus;
    }

    public void setTrainingPictures(String trainingPictures) {
        this.trainingPictures = trainingPictures;
    }

    public String getTrainingPictures() {
        return trainingPictures;
    }

    public void setQualificationCertificate(String qualificationCertificate) {
        this.qualificationCertificate = qualificationCertificate;
    }

    public String getQualificationCertificate() {
        return qualificationCertificate;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("trainingCode", getTrainingCode())
                .append("trainingName", getTrainingName())
                .append("projectName", getProjectName())
                .append("contractorName", getContractorName())
                .append("trainingType", getTrainingType())
                .append("trainingDate", getTrainingDate())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("trainingHours", getTrainingHours())
                .append("trainingContent", getTrainingContent())
                .append("trainingDepartment", getTrainingDepartment())
                .append("trainer", getTrainer())
                .append("trainingLocation", getTrainingLocation())
                .append("plannedAttendees", getPlannedAttendees())
                .append("actualAttendees", getActualAttendees())
                .append("trainingStatus", getTrainingStatus())
                .append("trainingPictures", getTrainingPictures())
                .append("qualificationCertificate", getQualificationCertificate())
                .append("remarks", getRemarks())
                .append("createdBy", getCreatedBy())
                .append("createdAt", getCreatedAt())
                .append("updatedBy", getUpdatedBy())
                .append("updatedAt", getUpdatedAt())
                .toString();
    }
}
