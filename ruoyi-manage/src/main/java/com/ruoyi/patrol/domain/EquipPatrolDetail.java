package com.ruoyi.patrol.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 巡检记录明细对象 equip_patrol_detail
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
public class EquipPatrolDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long detailId;

    /** 记录 */
    @Excel(name = "记录")
    private Long patrolId;

    /** 巡检项 */
    private Long itemId;

    /** 巡检项 */
    @Excel(name = "巡检项")
    private String itemName;

    /** 检查标准 */
    @Excel(name = "检查标准")
    private String standard;

    /** 巡检结果 */
    @Excel(name = "巡检结果")
    private String result;

    /** 问题描述 */
    @Excel(name = "问题描述")
    private String problem;

    /** 图片附件 */
    @Excel(name = "图片附件")
    private String images;

    public void setDetailId(Long detailId) 
    {
        this.detailId = detailId;
    }

    public Long getDetailId() 
    {
        return detailId;
    }
    public void setPatrolId(Long patrolId) 
    {
        this.patrolId = patrolId;
    }

    public Long getPatrolId() 
    {
        return patrolId;
    }
    public void setItemId(Long itemId) 
    {
        this.itemId = itemId;
    }

    public Long getItemId() 
    {
        return itemId;
    }
    public void setItemName(String itemName) 
    {
        this.itemName = itemName;
    }

    public String getItemName() 
    {
        return itemName;
    }
    public void setStandard(String standard) 
    {
        this.standard = standard;
    }

    public String getStandard() 
    {
        return standard;
    }
    public void setResult(String result) 
    {
        this.result = result;
    }

    public String getResult() 
    {
        return result;
    }
    public void setProblem(String problem) 
    {
        this.problem = problem;
    }

    public String getProblem() 
    {
        return problem;
    }
    public void setImages(String images) 
    {
        this.images = images;
    }

    public String getImages() 
    {
        return images;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("detailId", getDetailId())
            .append("patrolId", getPatrolId())
            .append("itemId", getItemId())
            .append("itemName", getItemName())
            .append("standard", getStandard())
            .append("result", getResult())
            .append("problem", getProblem())
            .append("images", getImages())
            .append("remark", getRemark())
            .toString();
    }
}
