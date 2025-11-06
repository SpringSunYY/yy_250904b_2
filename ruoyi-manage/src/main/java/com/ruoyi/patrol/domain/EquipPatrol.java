package com.ruoyi.patrol.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡检记录对象 equip_patrol
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
public class EquipPatrol extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long patrolId;

    /** 巡检单号 */
    @Excel(name = "巡检单号")
    private String patrolNo;

    /** 设备 */
    private Long equipId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String equipName;

    /** 设备位号 */
    @Excel(name = "设备位号")
    private String equipCode;

    /** 巡检人 */
    private Long userId;

    /** 巡检人 */
    @Excel(name = "巡检人")
    private String userName;

    /** 巡检时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "巡检时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date patrolTime;

    /** 流程实例 */
    private String processInstanceId;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 巡检结果 */
    @Excel(name = "巡检结果")
    private String patrolResult;

    /** 巡检记录明细信息 */
    private List<EquipPatrolDetail> equipPatrolDetailList;

    public void setPatrolId(Long patrolId) 
    {
        this.patrolId = patrolId;
    }

    public Long getPatrolId() 
    {
        return patrolId;
    }

    public void setPatrolNo(String patrolNo) 
    {
        this.patrolNo = patrolNo;
    }

    public String getPatrolNo() 
    {
        return patrolNo;
    }

    public void setEquipId(Long equipId) 
    {
        this.equipId = equipId;
    }

    public Long getEquipId() 
    {
        return equipId;
    }

    public void setEquipName(String equipName) 
    {
        this.equipName = equipName;
    }

    public String getEquipName() 
    {
        return equipName;
    }

    public void setEquipCode(String equipCode) 
    {
        this.equipCode = equipCode;
    }

    public String getEquipCode() 
    {
        return equipCode;
    }

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }

    public void setPatrolTime(Date patrolTime) 
    {
        this.patrolTime = patrolTime;
    }

    public Date getPatrolTime() 
    {
        return patrolTime;
    }

    public void setProcessInstanceId(String processInstanceId) 
    {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() 
    {
        return processInstanceId;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setPatrolResult(String patrolResult) 
    {
        this.patrolResult = patrolResult;
    }

    public String getPatrolResult() 
    {
        return patrolResult;
    }

    public List<EquipPatrolDetail> getEquipPatrolDetailList()
    {
        return equipPatrolDetailList;
    }

    public void setEquipPatrolDetailList(List<EquipPatrolDetail> equipPatrolDetailList)
    {
        this.equipPatrolDetailList = equipPatrolDetailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("patrolId", getPatrolId())
            .append("patrolNo", getPatrolNo())
            .append("equipId", getEquipId())
            .append("equipName", getEquipName())
            .append("equipCode", getEquipCode())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("patrolTime", getPatrolTime())
            .append("processInstanceId", getProcessInstanceId())
            .append("status", getStatus())
            .append("patrolResult", getPatrolResult())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("equipPatrolDetailList", getEquipPatrolDetailList())
            .toString();
    }
}
