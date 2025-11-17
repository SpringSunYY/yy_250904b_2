package com.ruoyi.contractor.mapper;

import com.ruoyi.contractor.domain.BizQualificationAudit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 承包商资格审查Mapper接口
 *
 * @author Laogao
 * @date 2025-10-23
 */
public interface BizQualificationAuditMapper {
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
     * 查询指定年份的最大审核编号
     *
     * @param year 年份
     * @return 最大审核编号
     */
    public String selectMaxAuditNumberByYear(String year);

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
     * 删除承包商资格审查
     *
     * @param auditId 承包商资格审查主键
     * @return 结果
     */
    public int deleteBizQualificationAuditByAuditId(Long auditId);

    /**
     * 批量删除承包商资格审查
     *
     * @param auditIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizQualificationAuditByAuditIds(Long[] auditIds);

    /**
     * 查询contractor_qualification_audit表数据
     *
     * @param dateType
     * @param startDate
     * @param endDate
     * @return List<BizQualificationAudit>
     * @author: YY
     * @method: selectBizQualificationAuditByStatistics
     * @date: 2025/11/15 16:07
     **/
    List<BizQualificationAudit> selectBizQualificationAuditByStatistics(@Param("dateType") String dateType, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
