package com.ruoyi.equip.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备运行明细对象 equip_runtime_datail
 * 
 * @author laogao
 * @date 2025-09-23
 */
public class EquipRuntimeDatail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long datailId;

    /** 统计id */
    private String statId;

    /** 设备id */
    private Long deviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 操作类型 */
    @Excel(name = "操作类型")
    private String operationType;

    /** 操作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operationTime;

    /** 停机原因 */
    @Excel(name = "停机原因")
    private String shutdownReason;

    public void setDatailId(Long datailId) 
    {
        this.datailId = datailId;
    }

    public Long getDatailId() 
    {
        return datailId;
    }
    public void setStatId(String statId) 
    {
        this.statId = statId;
    }

    public String getStatId() 
    {
        return statId;
    }
    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setDeviceName(String deviceName) 
    {
        this.deviceName = deviceName;
    }

    public String getDeviceName() 
    {
        return deviceName;
    }
    public void setOperationType(String operationType) 
    {
        this.operationType = operationType;
    }

    public String getOperationType() 
    {
        return operationType;
    }
    public void setOperationTime(Date operationTime) 
    {
        this.operationTime = operationTime;
    }

    public Date getOperationTime() 
    {
        return operationTime;
    }
    public void setShutdownReason(String shutdownReason) 
    {
        this.shutdownReason = shutdownReason;
    }

    public String getShutdownReason() 
    {
        return shutdownReason;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("datailId", getDatailId())
            .append("statId", getStatId())
            .append("deviceId", getDeviceId())
            .append("deviceName", getDeviceName())
            .append("operationType", getOperationType())
            .append("operationTime", getOperationTime())
            .append("shutdownReason", getShutdownReason())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
