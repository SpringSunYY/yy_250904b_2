package com.ruoyi.process.domain;

import java.util.Date;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 特殊作业管理对象 biz_safety_work_permit
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizSafetyWorkPermit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long permitId;

    /** 关联项目日志 */
    @Excel(name = "关联项目日志")
    private Long logsId;

    /** 项目日志编号 */
    private String logsCode;

    /** 特殊作业内容 */
    @Excel(name = "特殊作业内容")
    private String specialWork;

    /** 作业地点 */
    @Excel(name = "作业地点")
    private String specialWorkLocation;

    /** 特殊作业类型 */
    @Excel(name = "特殊作业类型")
    private String specialWorkType;

    /** 安全作业票编号 */
    @Excel(name = "安全作业票编号")
    private String safetyWorkPermitNo;

    /** 作业时段 */
    @Excel(name = "作业时段")
    private String workPeriod;

    /** 作业负责人 */
    @Excel(name = "作业负责人")
    private String workTeamLeader;

    /** 现场监护人 */
    @Excel(name = "现场监护人")
    private String siteGuardian;

    /** 作业票状态 */
    @Excel(name = "作业票状态")
    private String dailyPermitStatus;

    /** 关联作业 */
    private String relatedWork;

    /** 附件 */
    @Excel(name = "附件")
    private String attachment;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    private Date updateTime;

    public void setPermitId(Long permitId) 
    {
        this.permitId = permitId;
    }

    public Long getPermitId() 
    {
        return permitId;
    }

    public void setLogsId(Long logsId) 
    {
        this.logsId = logsId;
    }

    public Long getLogsId() 
    {
        return logsId;
    }
    
    public void setLogsCode(String logsCode) 
    {
        this.logsCode = logsCode;
    }

    public String getLogsCode() 
    {
        return logsCode;
    }

    public void setSpecialWork(String specialWork) 
    {
        this.specialWork = specialWork;
    }

    public String getSpecialWork() 
    {
        return specialWork;
    }

    public void setSpecialWorkLocation(String specialWorkLocation) 
    {
        this.specialWorkLocation = specialWorkLocation;
    }

    public String getSpecialWorkLocation() 
    {
        return specialWorkLocation;
    }

    public void setSpecialWorkType(String specialWorkType) 
    {
        this.specialWorkType = specialWorkType;
    }

    public String getSpecialWorkType() 
    {
        return specialWorkType;
    }

    public void setSafetyWorkPermitNo(String safetyWorkPermitNo) 
    {
        this.safetyWorkPermitNo = safetyWorkPermitNo;
    }

    public String getSafetyWorkPermitNo() 
    {
        return safetyWorkPermitNo;
    }

    public void setWorkPeriod(String workPeriod) 
    {
        this.workPeriod = workPeriod;
    }

    public String getWorkPeriod() 
    {
        return workPeriod;
    }

    public void setWorkTeamLeader(String workTeamLeader) 
    {
        this.workTeamLeader = workTeamLeader;
    }

    public String getWorkTeamLeader() 
    {
        return workTeamLeader;
    }

    public void setSiteGuardian(String siteGuardian) 
    {
        this.siteGuardian = siteGuardian;
    }

    public String getSiteGuardian() 
    {
        return siteGuardian;
    }

    public void setDailyPermitStatus(String dailyPermitStatus) 
    {
        this.dailyPermitStatus = dailyPermitStatus;
    }

    public String getDailyPermitStatus() 
    {
        return dailyPermitStatus;
    }
    
    public void setRelatedWork(String relatedWork) 
    {
        this.relatedWork = relatedWork;
    }

    public String getRelatedWork() 
    {
        return relatedWork;
    }

    public void setAttachment(String attachment) 
    {
        this.attachment = attachment;
    }

    public String getAttachment() 
    {
        return attachment;
    }

    public void setRemark(String remark) 
    {
        this.remark = remark;
    }

    public String getRemark() 
    {
        return remark;
    }

    public void setCreateBy(String createBy) 
    {
        this.createBy = createBy;
    }

    public String getCreateBy() 
    {
        return createBy;
    }

    public void setCreateTime(Date createTime) 
    {
        this.createTime = createTime;
    }

    public Date getCreateTime() 
    {
        return createTime;
    }

    public void setUpdateBy(String updateBy) 
    {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() 
    {
        return updateBy;
    }

    public void setUpdateTime(Date updateTime) 
    {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() 
    {
        return updateTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("permitId", getPermitId())
            .append("logsId", getLogsId())
            .append("specialWork", getSpecialWork())
            .append("specialWorkLocation", getSpecialWorkLocation())
            .append("specialWorkType", getSpecialWorkType())
            .append("safetyWorkPermitNo", getSafetyWorkPermitNo())
            .append("workPeriod", getWorkPeriod())
            .append("workTeamLeader", getWorkTeamLeader())
            .append("siteGuardian", getSiteGuardian())
            .append("dailyPermitStatus", getDailyPermitStatus())
            .append("relatedWork", getRelatedWork())
            .append("attachment", getAttachment())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
