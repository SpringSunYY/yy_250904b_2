package com.ruoyi.equip.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备运行管理对象 equip_runtime_stat
 * 
 * @author laogao
 * @date 2025-09-23
 */
public class EquipRuntimeStat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long statId;

    /** 统计编号 */
    @Excel(name = "统计编号")
    private String statNo;

    /** 设备 */
    private Long deviceId;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String deviceName;

    /** 设备位号 */
    @Excel(name = "设备位号")
    private String deviceCode;

    /** 开机记录 */
    private Long startRecordId;

    /** 开机时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开机时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 停机记录 */
    private Long stopRecordId;

    /** 停机时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "停机时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date stopTime;

    /** 运行时长(h) */
    @Excel(name = "运行时长(h)")
    private Long totalRuntime;

    /** 统计状态 */
    @Excel(name = "统计状态")
    private String status;

    /** 停机原因 */
    @Excel(name = "停机原因")
    private String shutdownReason;

    /** 设备运行明细信息 */
    private List<EquipRuntimeDatail> equipRuntimeDatailList;

    public void setStatId(Long statId) 
    {
        this.statId = statId;
    }

    public Long getStatId() 
    {
        return statId;
    }

    public void setStatNo(String statNo) 
    {
        this.statNo = statNo;
    }

    public String getStatNo() 
    {
        return statNo;
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

    public void setDeviceCode(String deviceCode) 
    {
        this.deviceCode = deviceCode;
    }

    public String getDeviceCode() 
    {
        return deviceCode;
    }

    public void setStartRecordId(Long startRecordId) 
    {
        this.startRecordId = startRecordId;
    }

    public Long getStartRecordId() 
    {
        return startRecordId;
    }

    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }

    public void setStopRecordId(Long stopRecordId) 
    {
        this.stopRecordId = stopRecordId;
    }

    public Long getStopRecordId() 
    {
        return stopRecordId;
    }

    public void setStopTime(Date stopTime) 
    {
        this.stopTime = stopTime;
    }

    public Date getStopTime() 
    {
        return stopTime;
    }

    public void setTotalRuntime(Long totalRuntime) 
    {
        this.totalRuntime = totalRuntime;
    }

    public Long getTotalRuntime() 
    {
        return totalRuntime;
    }

    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }

    public void setShutdownReason(String shutdownReason) 
    {
        this.shutdownReason = shutdownReason;
    }

    public String getShutdownReason() 
    {
        return shutdownReason;
    }

    public List<EquipRuntimeDatail> getEquipRuntimeDatailList()
    {
        return equipRuntimeDatailList;
    }

    public void setEquipRuntimeDatailList(List<EquipRuntimeDatail> equipRuntimeDatailList)
    {
        this.equipRuntimeDatailList = equipRuntimeDatailList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("statId", getStatId())
            .append("statNo", getStatNo())
            .append("deviceId", getDeviceId())
            .append("deviceName", getDeviceName())
            .append("deviceCode", getDeviceCode())
            .append("startRecordId", getStartRecordId())
            .append("startTime", getStartTime())
            .append("stopRecordId", getStopRecordId())
            .append("stopTime", getStopTime())
            .append("totalRuntime", getTotalRuntime())
            .append("status", getStatus())
            .append("shutdownReason", getShutdownReason())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("equipRuntimeDatailList", getEquipRuntimeDatailList())
            .toString();
    }
}
