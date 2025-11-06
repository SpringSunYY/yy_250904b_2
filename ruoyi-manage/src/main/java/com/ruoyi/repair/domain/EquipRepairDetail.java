package com.ruoyi.repair.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备维修明细对象 equip_repair_detail
 * 
 * @author laogao
 * @date 2025-09-30
 */
public class EquipRepairDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long detailId;

    /** 关联维修记录ID */
    private Long recordId;

    /** 执行人 */
    private String assignedId;

    /** 执行人 */
    @Excel(name = "执行人")
    private String assignedName;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /** 工时 */
    @Excel(name = "工时")
    private BigDecimal menHours;

    /** 维修内容 */
    @Excel(name = "维修内容")
    private String repairContent;

    /** 维修结果 */
    @Excel(name = "维修结果")
    private String repairResult;

    /** 使用材料 */
    @Excel(name = "使用材料")
    private String usedMaterials;

    /** 检修结果 */
    @Excel(name = "检修结果")
    private String checkResult;

    /** 检修意见 */
    @Excel(name = "检修意见")
    private String checkOpinion;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }
    public void setRecordId(Long recordId) 
    {
        this.recordId = recordId;
    }

    public Long getRecordId() 
    {
        return recordId;
    }
    public void setAssignedId(String assignedId) 
    {
        this.assignedId = assignedId;
    }

    public String getAssignedId() 
    {
        return assignedId;
    }
    public void setAssignedName(String assignedName) 
    {
        this.assignedName = assignedName;
    }

    public String getAssignedName() 
    {
        return assignedName;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }
    public void setMenHours(BigDecimal menHours) 
    {
        this.menHours = menHours;
    }

    public BigDecimal getMenHours() 
    {
        return menHours;
    }
    public void setRepairContent(String repairContent) 
    {
        this.repairContent = repairContent;
    }

    public String getRepairContent() 
    {
        return repairContent;
    }
    public void setRepairResult(String repairResult) 
    {
        this.repairResult = repairResult;
    }

    public String getRepairResult() 
    {
        return repairResult;
    }
    public void setUsedMaterials(String usedMaterials) 
    {
        this.usedMaterials = usedMaterials;
    }

    public String getUsedMaterials() 
    {
        return usedMaterials;
    }
    public void setCheckResult(String checkResult) 
    {
        this.checkResult = checkResult;
    }

    public String getCheckResult() 
    {
        return checkResult;
    }
    public void setCheckOpinion(String checkOpinion) 
    {
        this.checkOpinion = checkOpinion;
    }

    public String getCheckOpinion() 
    {
        return checkOpinion;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("recordId", getRecordId())
            .append("assignedId", getAssignedId())
            .append("assignedName", getAssignedName())
            .append("startTime", getStartTime())
            .append("finishTime", getFinishTime())
            .append("menHours", getMenHours())
            .append("repairContent", getRepairContent())
            .append("repairResult", getRepairResult())
            .append("usedMaterials", getUsedMaterials())
            .append("checkResult", getCheckResult())
            .append("checkOpinion", getCheckOpinion())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
