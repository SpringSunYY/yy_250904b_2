package com.ruoyi.process.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizSiteInspectionsMapper;
import com.ruoyi.process.domain.BizSiteInspections;
import com.ruoyi.process.service.IBizSiteInspectionsService;

/**
 * 监督检查记录Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizSiteInspectionsServiceImpl implements IBizSiteInspectionsService 
{
    @Autowired
    private BizSiteInspectionsMapper bizSiteInspectionsMapper;

    /**
     * 查询监督检查记录
     * 
     * @param id 监督检查记录主键
     * @return 监督检查记录
     */
    @Override
    public BizSiteInspections selectBizSiteInspectionsById(Long id)
    {
        return bizSiteInspectionsMapper.selectBizSiteInspectionsById(id);
    }

    /**
     * 根据检查记录编号查询监督检查记录
     * 
     * @param inspectionCode 检查记录编号
     * @return 监督检查记录
     */
    @Override
    public BizSiteInspections selectBizSiteInspectionsByInspectionCode(String inspectionCode)
    {
        return bizSiteInspectionsMapper.selectBizSiteInspectionsByInspectionCode(inspectionCode);
    }

    /**
     * 查询监督检查记录列表
     * 
     * @param bizSiteInspections 监督检查记录
     * @return 监督检查记录
     */
    @Override
    public List<BizSiteInspections> selectBizSiteInspectionsList(BizSiteInspections bizSiteInspections)
    {
        return bizSiteInspectionsMapper.selectBizSiteInspectionsList(bizSiteInspections);
    }

    /**
     * 新增监督检查记录
     * 
     * @param bizSiteInspections 监督检查记录
     * @return 结果
     */
    @Override
    public int insertBizSiteInspections(BizSiteInspections bizSiteInspections)
    {
        // 检查编号唯一性验证
        if (bizSiteInspections.getInspectionCode() != null && !bizSiteInspections.getInspectionCode().isEmpty()) {
            BizSiteInspections existing = bizSiteInspectionsMapper.selectBizSiteInspectionsByInspectionCode(bizSiteInspections.getInspectionCode());
            if (existing != null) {
                throw new RuntimeException("检查记录编号已存在，请重新生成或修改编号");
            }
        }
        return bizSiteInspectionsMapper.insertBizSiteInspections(bizSiteInspections);
    }

    /**
     * 修改监督检查记录
     * 
     * @param bizSiteInspections 监督检查记录
     * @return 结果
     */
    @Override
    public int updateBizSiteInspections(BizSiteInspections bizSiteInspections)
    {
        // 检查编号唯一性验证
        if (bizSiteInspections.getInspectionCode() != null && !bizSiteInspections.getInspectionCode().isEmpty()) {
            BizSiteInspections existing = bizSiteInspectionsMapper.selectBizSiteInspectionsByInspectionCode(bizSiteInspections.getInspectionCode());
            if (existing != null && existing.getId() != null && !existing.getId().equals(bizSiteInspections.getId())) {
                throw new RuntimeException("检查记录编号已存在，请重新生成或修改编号");
            }
        }
        return bizSiteInspectionsMapper.updateBizSiteInspections(bizSiteInspections);
    }

    /**
     * 批量删除监督检查记录
     * 
     * @param ids 需要删除的监督检查记录主键
     * @return 结果
     */
    @Override
    public int deleteBizSiteInspectionsByIds(Long[] ids)
    {
        return bizSiteInspectionsMapper.deleteBizSiteInspectionsByIds(ids);
    }

    /**
     * 删除监督检查记录信息
     * 
     * @param id 监督检查记录主键
     * @return 结果
     */
    @Override
    public int deleteBizSiteInspectionsById(Long id)
    {
        return bizSiteInspectionsMapper.deleteBizSiteInspectionsById(id);
    }
}
