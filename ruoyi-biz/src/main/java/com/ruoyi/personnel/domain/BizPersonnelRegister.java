package com.ruoyi.personnel.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 人员信息台账对象 biz_personnel_register
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public class BizPersonnelRegister extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long personnelId;

    /** 承包商 */
    private Long choiceId;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 承包商 */
    @Excel(name = "承包商")
    private String contractorName;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCardNumber;

    /** 姓名 */
    @Excel(name = "姓名")
    private String personnelName;

    /** 职务 */
    @Excel(name = "职务")
    private String title;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 工种 */
    @Excel(name = "工种")
    private String workType;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactPhone;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String homeAddress;

    /** 体检日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "体检日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date physicalExamDate;

    /** 体检结果 */
    @Excel(name = "体检结果")
    private String physicalExamResult;

    /** 体检记录 */
    @Excel(name = "体检记录")
    private String physicalExamRecord;

    /** 入场时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入场时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryFactoryTime;

    /** 离场时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "离场时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date exitFactoryTime;

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

    public void setPersonnelId(Long personnelId) 
    {
        this.personnelId = personnelId;
    }

    public Long getPersonnelId() 
    {
        return personnelId;
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

    public void setIdCardNumber(String idCardNumber) 
    {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardNumber() 
    {
        return idCardNumber;
    }

    public void setPersonnelName(String personnelName) 
    {
        this.personnelName = personnelName;
    }

    public String getPersonnelName() 
    {
        return personnelName;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }

    public void setAge(Long age) 
    {
        this.age = age;
    }

    public Long getAge() 
    {
        return age;
    }

    public void setWorkType(String workType) 
    {
        this.workType = workType;
    }

    public String getWorkType() 
    {
        return workType;
    }

    public void setContactPhone(String contactPhone) 
    {
        this.contactPhone = contactPhone;
    }

    public String getContactPhone() 
    {
        return contactPhone;
    }

    public void setHomeAddress(String homeAddress) 
    {
        this.homeAddress = homeAddress;
    }

    public String getHomeAddress() 
    {
        return homeAddress;
    }

    public void setPhysicalExamDate(Date physicalExamDate) 
    {
        this.physicalExamDate = physicalExamDate;
    }

    public Date getPhysicalExamDate() 
    {
        return physicalExamDate;
    }

    public void setPhysicalExamResult(String physicalExamResult) 
    {
        this.physicalExamResult = physicalExamResult;
    }

    public String getPhysicalExamResult() 
    {
        return physicalExamResult;
    }

    public void setPhysicalExamRecord(String physicalExamRecord) 
    {
        this.physicalExamRecord = physicalExamRecord;
    }

    public String getPhysicalExamRecord() 
    {
        return physicalExamRecord;
    }

    public void setEntryFactoryTime(Date entryFactoryTime) 
    {
        this.entryFactoryTime = entryFactoryTime;
    }

    public Date getEntryFactoryTime() 
    {
        return entryFactoryTime;
    }

    public void setExitFactoryTime(Date exitFactoryTime) 
    {
        this.exitFactoryTime = exitFactoryTime;
    }

    public Date getExitFactoryTime() 
    {
        return exitFactoryTime;
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
            .append("personnelId", getPersonnelId())
            .append("choiceId", getChoiceId())
            .append("projectName", getProjectName())
            .append("contractorName", getContractorName())
            .append("idCardNumber", getIdCardNumber())
            .append("personnelName", getPersonnelName())
            .append("title", getTitle())
            .append("gender", getGender())
            .append("age", getAge())
            .append("workType", getWorkType())
            .append("contactPhone", getContactPhone())
            .append("homeAddress", getHomeAddress())
            .append("physicalExamDate", getPhysicalExamDate())
            .append("physicalExamResult", getPhysicalExamResult())
            .append("physicalExamRecord", getPhysicalExamRecord())
            .append("entryFactoryTime", getEntryFactoryTime())
            .append("exitFactoryTime", getExitFactoryTime())
            .append("remarks", getRemarks())
            .append("createdBy", getCreatedBy())
            .append("createdAt", getCreatedAt())
            .append("updatedBy", getUpdatedBy())
            .append("updatedAt", getUpdatedAt())
            .toString();
    }
}
