package com.ruoyi.training.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 人员参训记录对象 biz_training_attendance
 *
 * @author Laogao
 * @date 2025-10-24
 */
public class BizTrainingAttendance extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    @Excel(name = "序号")
    private Long id;

    /**
     * 培训名称
     */
    private Long trainingId;

    /**
     * 选择编号
     */
    @Excel(name = "选择编号")
    private Long choiceId;

    /**
     * 培训名称
     */
    @Excel(name = "培训名称")
    private String trainingName;

    /**
     * 姓名
     */
    private Long personnelId;

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String personnelName;

    /**
     * 考试时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "考试时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date examTime;

    /**
     * 考试分数
     */
    @Excel(name = "考试分数")
    private BigDecimal examScore;

    /**
     * 考核结果
     */
    @Excel(name = "考核结果")
    private String assessmentResult;

    /**
     * 考试试卷
     */
    @Excel(name = "考试试卷")
    private String examPaper;

    /**
     * 是否需要补考
     */
    @Excel(name = "是否需要补考")
    private String needRetake;

    /**
     * 补考时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "补考时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date retakeTime;

    /**
     * 补考成绩
     */
    @Excel(name = "补考成绩")
    private BigDecimal retakeScore;

    /**
     * 最终判定结果
     */
    @Excel(name = "最终判定结果")
    private String finalResult;

    /**
     * 补考试卷
     */
    @Excel(name = "补考试卷")
    private String retakePaper;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTrainingId(Long trainingId) {
        this.trainingId = trainingId;
    }

    public Long getTrainingId() {
        return trainingId;
    }

    public void setTrainingName(String trainingName) {
        this.trainingName = trainingName;
    }

    public String getTrainingName() {
        return trainingName;
    }

    public void setPersonnelId(Long personnelId) {
        this.personnelId = personnelId;
    }

    public Long getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setExamTime(Date examTime) {
        this.examTime = examTime;
    }

    public Date getExamTime() {
        return examTime;
    }

    public void setExamScore(BigDecimal examScore) {
        this.examScore = examScore;
    }

    public BigDecimal getExamScore() {
        return examScore;
    }

    public void setAssessmentResult(String assessmentResult) {
        this.assessmentResult = assessmentResult;
    }

    public String getAssessmentResult() {
        return assessmentResult;
    }

    public void setExamPaper(String examPaper) {
        this.examPaper = examPaper;
    }

    public String getExamPaper() {
        return examPaper;
    }

    public void setNeedRetake(String needRetake) {
        this.needRetake = needRetake;
    }

    public String getNeedRetake() {
        return needRetake;
    }

    public void setRetakeTime(Date retakeTime) {
        this.retakeTime = retakeTime;
    }

    public Date getRetakeTime() {
        return retakeTime;
    }

    public void setRetakeScore(BigDecimal retakeScore) {
        this.retakeScore = retakeScore;
    }

    public BigDecimal getRetakeScore() {
        return retakeScore;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public void setRetakePaper(String retakePaper) {
        this.retakePaper = retakePaper;
    }

    public String getRetakePaper() {
        return retakePaper;
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

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("trainingId", getTrainingId())
                .append("trainingName", getTrainingName())
                .append("personnelId", getPersonnelId())
                .append("personnelName", getPersonnelName())
                .append("examTime", getExamTime())
                .append("examScore", getExamScore())
                .append("assessmentResult", getAssessmentResult())
                .append("examPaper", getExamPaper())
                .append("needRetake", getNeedRetake())
                .append("retakeTime", getRetakeTime())
                .append("retakeScore", getRetakeScore())
                .append("finalResult", getFinalResult())
                .append("retakePaper", getRetakePaper())
                .append("createdBy", getCreatedBy())
                .append("remark", getRemark())
                .append("createdAt", getCreatedAt())
                .append("updatedBy", getUpdatedBy())
                .append("updatedAt", getUpdatedAt())
                .toString();
    }
}
