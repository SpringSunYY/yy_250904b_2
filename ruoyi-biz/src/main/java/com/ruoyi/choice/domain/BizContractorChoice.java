package com.ruoyi.choice.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 承包商选择对象 biz_contractor_choice
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizContractorChoice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long choiceId;

    /** 项目名称 */
    private Long projectId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 拟选承包商 */
    private Long qualId;

    /** 拟选承包商 */
    @Excel(name = "拟选承包商")
    private String selectedContractor;

    /** 相关附件 */
    @Excel(name = "相关附件")
    private String attachment;

    /** 流程实例id */
    private Long processInstanceId;

    /** 流程状态 */
    @Excel(name = "流程状态")
    private String processStatus;

    /** 当前节点 */
    @Excel(name = "当前节点")
    private String currentNode;

    /** 当前处理人 */
    @Excel(name = "当前处理人")
    private String currentHandler;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date approvalTime;

    /** 审批人 */
    @Excel(name = "审批人")
    private String approver;

    /** 审批结果 */
    @Excel(name = "审批结果")
    private String approvalResult;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建者 */
    private String createdBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createdAt;

    /** 更新者 */
    private String updatedBy;

    /** 更新时间 */
    private Date updatedAt;

    public void setChoiceId(Long choiceId) 
    {
        this.choiceId = choiceId;
    }

    public Long getChoiceId() 
    {
        return choiceId;
    }

    public void setProjectId(Long projectId) 
    {
        this.projectId = projectId;
    }

    public Long getProjectId() 
    {
        return projectId;
    }

    public void setProjectName(String projectName) 
    {
        this.projectName = projectName;
    }

    public String getProjectName() 
    {
        return projectName;
    }

    public void setQualId(Long qualId) 
    {
        this.qualId = qualId;
    }

    public Long getQualId() 
    {
        return qualId;
    }

    public void setSelectedContractor(String selectedContractor) 
    {
        this.selectedContractor = selectedContractor;
    }

    public String getSelectedContractor() 
    {
        return selectedContractor;
    }

    public void setAttachment(String attachment) 
    {
        this.attachment = attachment;
    }

    public String getAttachment() 
    {
        return attachment;
    }

    public void setProcessInstanceId(Long processInstanceId) 
    {
        this.processInstanceId = processInstanceId;
    }

    public Long getProcessInstanceId() 
    {
        return processInstanceId;
    }

    public void setProcessStatus(String processStatus) 
    {
        this.processStatus = processStatus;
    }

    public String getProcessStatus() 
    {
        return processStatus;
    }

    public void setCurrentNode(String currentNode) 
    {
        this.currentNode = currentNode;
    }

    public String getCurrentNode() 
    {
        return currentNode;
    }

    public void setCurrentHandler(String currentHandler) 
    {
        this.currentHandler = currentHandler;
    }

    public String getCurrentHandler() 
    {
        return currentHandler;
    }

    public void setApprovalTime(Date approvalTime)
    {
        this.approvalTime = approvalTime;
    }

    public Date getApprovalTime()
    {
        return approvalTime;
    }

    public void setApprover(String approver)
    {
        this.approver = approver;
    }

    public String getApprover()
    {
        return approver;
    }

    public void setApprovalResult(String approvalResult)
    {
        this.approvalResult = approvalResult;
    }

    public String getApprovalResult()
    {
        return approvalResult;
    }

    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
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
            .append("choiceId", getChoiceId())
            .append("projectId", getProjectId())
            .append("projectName", getProjectName())
            .append("qualId", getQualId())
            .append("selectedContractor", getSelectedContractor())
            .append("attachment", getAttachment())
            .append("processInstanceId", getProcessInstanceId())
            .append("processStatus", getProcessStatus())
            .append("currentNode", getCurrentNode())
            .append("currentHandler", getCurrentHandler())
            .append("approvalTime", getApprovalTime())
            .append("approver", getApprover())
            .append("approvalResult", getApprovalResult())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
