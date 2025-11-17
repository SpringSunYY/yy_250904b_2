package com.ruoyi.contractor.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全业绩评价对象 biz_safety_evaluation
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
public class BizSafetyEvaluation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 评价编号 */
    @Excel(name = "评价编号")
    private String evaluationCode;

    /** 承包商选择id */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String reviewContent;

    /** 评价时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "评价时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date evaluationDate;

    /** 评价部门 */
    @Excel(name = "评价部门")
    private String evaluationDepartment;

    /** 评价人员 */
    @Excel(name = "评价人员")
    private String evaluatorName;

    /** 得分 */
    @Excel(name = "得分")
    private BigDecimal score;

    /** 评价结论 */
    @Excel(name = "评价结论")
    private String evaluationConclusion;

    /** 相关附件 */
    @Excel(name = "相关附件")
    private String attachments;

    /** 备注 */
    @Excel(name = "备注")
    private String notes;

    /** 创建人 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新人 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedAt;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setEvaluationCode(String evaluationCode) 
    {
        this.evaluationCode = evaluationCode;
    }

    public String getEvaluationCode() 
    {
        return evaluationCode;
    }

    public void setChoiceId(Long choiceId) 
    {
        this.choiceId = choiceId;
    }

    public Long getChoiceId() 
    {
        return choiceId;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setContractorName(String contractorName) 
    {
        this.contractorName = contractorName;
    }

    public String getContractorName() 
    {
        return contractorName;
    }

    public void setReviewContent(String reviewContent) 
    {
        this.reviewContent = reviewContent;
    }

    public String getReviewContent() 
    {
        return reviewContent;
    }

    public void setEvaluationDate(Date evaluationDate) 
    {
        this.evaluationDate = evaluationDate;
    }

    public Date getEvaluationDate() 
    {
        return evaluationDate;
    }

    public void setEvaluationDepartment(String evaluationDepartment) 
    {
        this.evaluationDepartment = evaluationDepartment;
    }

    public String getEvaluationDepartment() 
    {
        return evaluationDepartment;
    }

    public void setEvaluatorName(String evaluatorName) 
    {
        this.evaluatorName = evaluatorName;
    }

    public String getEvaluatorName() 
    {
        return evaluatorName;
    }

    public void setScore(BigDecimal score) 
    {
        this.score = score;
    }

    public BigDecimal getScore() 
    {
        return score;
    }

    public void setEvaluationConclusion(String evaluationConclusion) 
    {
        this.evaluationConclusion = evaluationConclusion;
    }

    public String getEvaluationConclusion() 
    {
        return evaluationConclusion;
    }

    public void setAttachments(String attachments) 
    {
        this.attachments = attachments;
    }

    public String getAttachments() 
    {
        return attachments;
    }

    public void setNotes(String notes) 
    {
        this.notes = notes;
    }

    public String getNotes() 
    {
        return notes;
    }

    public void setCreatedBy(String createdBy) 
    {
        this.createdBy = createdBy;
    }

    public String getCreatedBy() 
    {
        return createdBy;
    }

    public void setCreatedAt(Date createdAt) 
    {
        this.createdAt = createdAt;
    }

    public Date getCreatedAt() 
    {
        return createdAt;
    }

    public void setUpdatedBy(String updatedBy) 
    {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedBy() 
    {
        return updatedBy;
    }

    public void setUpdatedAt(Date updatedAt) 
    {
        this.updatedAt = updatedAt;
    }

    public Date getUpdatedAt() 
    {
        return updatedAt;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("evaluationCode", getEvaluationCode())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("reviewContent", getReviewContent())
            .append("evaluationDate", getEvaluationDate())
            .append("evaluationDepartment", getEvaluationDepartment())
            .append("evaluatorName", getEvaluatorName())
            .append("score", getScore())
            .append("evaluationConclusion", getEvaluationConclusion())
            .append("attachments", getAttachments())
            .append("notes", getNotes())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
