package com.ruoyi.contents.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 承包商黑名单对象 biz_contractor_blacklist
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizContractorBlacklist extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long blackId;

    /** 审查id */
    private Long auditId;

    /** 承包商名称 */
    @Excel(name = "承包商名称")
    private String contractorName;

    /** 列入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "列入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date blacklistDate;

    /** 列入事由 */
    @Excel(name = "列入事由")
    private String blacklistReason;

    /** 处罚金额（万元） */
    @Excel(name = "处罚金额", readConverterExp = "万=元")
    private BigDecimal penaltyAmount;

    /** 期限（月） */
    @Excel(name = "期限", readConverterExp = "月=")
    private Long penaltyPeriod;

    /** 解除日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "解除日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date releaseDate;

    /** 相关附件 */
    @Excel(name = "相关附件")
    private String attachment;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

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

    public void setBlackId(Long blackId) 
    {
        this.blackId = blackId;
    }

    public Long getBlackId() 
    {
        return blackId;
    }

    public void setAuditId(Long auditId) 
    {
        this.auditId = auditId;
    }

    public Long getAuditId() 
    {
        return auditId;
    }

    public void setContractorName(String contractorName) 
    {
        this.contractorName = contractorName;
    }

    public String getContractorName() 
    {
        return contractorName;
    }

    public void setBlacklistDate(Date blacklistDate) 
    {
        this.blacklistDate = blacklistDate;
    }

    public Date getBlacklistDate() 
    {
        return blacklistDate;
    }

    public void setBlacklistReason(String blacklistReason) 
    {
        this.blacklistReason = blacklistReason;
    }

    public String getBlacklistReason() 
    {
        return blacklistReason;
    }

    public void setPenaltyAmount(BigDecimal penaltyAmount) 
    {
        this.penaltyAmount = penaltyAmount;
    }

    public BigDecimal getPenaltyAmount() 
    {
        return penaltyAmount;
    }

    public void setPenaltyPeriod(Long penaltyPeriod) 
    {
        this.penaltyPeriod = penaltyPeriod;
    }

    public Long getPenaltyPeriod() 
    {
        return penaltyPeriod;
    }

    public void setReleaseDate(Date releaseDate) 
    {
        this.releaseDate = releaseDate;
    }

    public Date getReleaseDate() 
    {
        return releaseDate;
    }

    public void setAttachment(String attachment) 
    {
        this.attachment = attachment;
    }

    public String getAttachment() 
    {
        return attachment;
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
            .append("blackId", getBlackId())
            .append("auditId", getAuditId())
            .append("contractorName", getContractorName())
            .append("blacklistDate", getBlacklistDate())
            .append("blacklistReason", getBlacklistReason())
            .append("penaltyAmount", getPenaltyAmount())
            .append("penaltyPeriod", getPenaltyPeriod())
            .append("releaseDate", getReleaseDate())
            .append("attachment", getAttachment())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
