package com.ruoyi.contractor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contractor.mapper.BizQualificationAuditMapper;
import com.ruoyi.contractor.domain.BizQualificationAudit;
import com.ruoyi.contractor.service.IBizQualificationAuditService;

/**
 * 承包商资格审查Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-23
 */
@Service
public class BizQualificationAuditServiceImpl implements IBizQualificationAuditService 
{
    @Autowired
    private BizQualificationAuditMapper bizQualificationAuditMapper;

    /**
     * 查询承包商资格审查
     * 
     * @param auditId 承包商资格审查主键
     * @return 承包商资格审查
     */
    @Override
    public BizQualificationAudit selectBizQualificationAuditByAuditId(Long auditId)
    {
        return bizQualificationAuditMapper.selectBizQualificationAuditByAuditId(auditId);
    }

    /**
     * 查询承包商资格审查列表
     * 
     * @param bizQualificationAudit 承包商资格审查
     * @return 承包商资格审查
     */
    @Override
    public List<BizQualificationAudit> selectBizQualificationAuditList(BizQualificationAudit bizQualificationAudit)
    {
        return bizQualificationAuditMapper.selectBizQualificationAuditList(bizQualificationAudit);
    }

    /**
     * 新增承包商资格审查
     * 
     * @param bizQualificationAudit 承包商资格审查
     * @return 结果
     */
    @Override
    public int insertBizQualificationAudit(BizQualificationAudit bizQualificationAudit)
    {
        // 自动生成审核编号
        if (bizQualificationAudit.getAuditNumber() == null || bizQualificationAudit.getAuditNumber().isEmpty()) {
            // 生成新的编号格式: ZG+年份+三位数字
            String year = DateUtils.dateTimeNow("yyyy");
            String prefix = "ZG" + year;
            
            // 查询当前年份最大的编号
            String maxAuditNumber = bizQualificationAuditMapper.selectMaxAuditNumberByYear(year);
            
            int nextNumber = 1;
            if (maxAuditNumber != null && !maxAuditNumber.isEmpty()) {
                // 从现有编号中提取数字部分并加1
                String numberPart = maxAuditNumber.substring(6); // "ZG" + "YYYY" = 6位
                try {
                    nextNumber = Integer.parseInt(numberPart) + 1;
                } catch (NumberFormatException e) {
                    nextNumber = 1;
                }
            }
            
            // 格式化为3位数字，不足的前面补0
            String auditNumber = prefix + String.format("%03d", nextNumber);
            bizQualificationAudit.setAuditNumber(auditNumber);
        }
        bizQualificationAudit.setCreateTime(DateUtils.getNowDate());
        return bizQualificationAuditMapper.insertBizQualificationAudit(bizQualificationAudit);
    }

    /**
     * 修改承包商资格审查
     * 
     * @param bizQualificationAudit 承包商资格审查
     * @return 结果
     */
    @Override
    public int updateBizQualificationAudit(BizQualificationAudit bizQualificationAudit)
    {
        bizQualificationAudit.setUpdateTime(DateUtils.getNowDate());
        return bizQualificationAuditMapper.updateBizQualificationAudit(bizQualificationAudit);
    }

    /**
     * 批量删除承包商资格审查
     * 
     * @param auditIds 需要删除的承包商资格审查主键
     * @return 结果
     */
    @Override
    public int deleteBizQualificationAuditByAuditIds(Long[] auditIds)
    {
        return bizQualificationAuditMapper.deleteBizQualificationAuditByAuditIds(auditIds);
    }

    /**
     * 删除承包商资格审查信息
     * 
     * @param auditId 承包商资格审查主键
     * @return 结果
     */
    @Override
    public int deleteBizQualificationAuditByAuditId(Long auditId)
    {
        return bizQualificationAuditMapper.deleteBizQualificationAuditByAuditId(auditId);
    }
}
