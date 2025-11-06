package com.ruoyi.equip.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安装验收明细对象 equip_device_item
 * 
 * @author laogao
 * @date 2025-09-23
 */
public class EquipDeviceItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long annexId;

    /** 验收单号 */
    private Long deviceId;

    /** 外观检查 */
    @Excel(name = "外观检查")
    private String vi;

    /** 功能测试 */
    @Excel(name = "功能测试")
    private String funTest;

    /** 技术资料 */
    @Excel(name = "技术资料")
    private String tecPaper;

    /** 相关附件 */
    @Excel(name = "相关附件")
    private String attachment;

    public void setAnnexId(Long annexId) 
    {
        this.annexId = annexId;
    }

    public Long getAnnexId() 
    {
        return annexId;
    }
    public void setDeviceId(Long deviceId) 
    {
        this.deviceId = deviceId;
    }

    public Long getDeviceId() 
    {
        return deviceId;
    }
    public void setVi(String vi) 
    {
        this.vi = vi;
    }

    public String getVi() 
    {
        return vi;
    }
    public void setFunTest(String funTest) 
    {
        this.funTest = funTest;
    }

    public String getFunTest() 
    {
        return funTest;
    }
    public void setTecPaper(String tecPaper) 
    {
        this.tecPaper = tecPaper;
    }

    public String getTecPaper() 
    {
        return tecPaper;
    }
    public void setAttachment(String attachment) 
    {
        this.attachment = attachment;
    }

    public String getAttachment() 
    {
        return attachment;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("annexId", getAnnexId())
            .append("deviceId", getDeviceId())
            .append("vi", getVi())
            .append("funTest", getFunTest())
            .append("tecPaper", getTecPaper())
            .append("attachment", getAttachment())
            .toString();
    }
}
