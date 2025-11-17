package com.ruoyi.contractor.service;

import java.util.List;
import com.ruoyi.contractor.domain.BizQualificationAudit;

/**
 * 承包商资格审查Service接口
 * 
 * @author Laogao
 * @date 2025-10-23
 */
public interface IBizQualificationAuditService 
{
    /**
     * 查询承包商资格审查
     * 
     * @param auditId 承包商资格审查主键
     * @return 承包商资格审查
     */
    public BizQualificationAudit selectBizQualificationAuditByAuditId(Long auditId);

    /**
     * 查询承包商资格审查列表
     * 
     * @param bizQualificationAudit 承包商资格审查
     * @return 承包商资格审查集合
     */
    public List<BizQualificationAudit> selectBizQualificationAuditList(BizQualificationAudit bizQualificationAudit);

    /**
     * 新增承包商资格审查
     * 
     * @param bizQualificationAudit 承包商资格审查
     * @return 结果
     */
    public int insertBizQualificationAudit(BizQualificationAudit bizQualificationAudit);

    /**
     * 修改承包商资格审查
     * 
     * @param bizQualificationAudit 承包商资格审查
     * @return 结果
     */
    public int updateBizQualificationAudit(BizQualificationAudit bizQualificationAudit);

    /**
     * 批量删除承包商资格审查
     * 
     * @param auditIds 需要删除的承包商资格审查主键集合
     * @return 结果
     */
    public int deleteBizQualificationAuditByAuditIds(Long[] auditIds);

    /**
     * 删除承包商资格审查信息
     * 
     * @param auditId 承包商资格审查主键
     * @return 结果
     */
    public int deleteBizQualificationAuditByAuditId(Long auditId);
}
