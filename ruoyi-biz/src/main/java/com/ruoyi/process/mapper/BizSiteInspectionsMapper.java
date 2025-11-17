package com.ruoyi.process.mapper;

import java.util.List;
import com.ruoyi.process.domain.BizSiteInspections;

/**
 * 监督检查记录Mapper接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface BizSiteInspectionsMapper 
{
    /**
     * 查询监督检查记录
     * 
     * @param id 监督检查记录主键
     * @return 监督检查记录
     */
    public BizSiteInspections selectBizSiteInspectionsById(Long id);

    /**
     * 根据检查记录编号查询监督检查记录
     * 
     * @param inspectionCode 检查记录编号
     * @return 监督检查记录
     */
    public BizSiteInspections selectBizSiteInspectionsByInspectionCode(String inspectionCode);

    /**
     * 查询监督检查记录列表
     * 
     * @param bizSiteInspections 监督检查记录
     * @return 监督检查记录集合
     */
    public List<BizSiteInspections> selectBizSiteInspectionsList(BizSiteInspections bizSiteInspections);

    /**
     * 新增监督检查记录
     * 
     * @param bizSiteInspections 监督检查记录
     * @return 结果
     */
    public int insertBizSiteInspections(BizSiteInspections bizSiteInspections);

    /**
     * 修改监督检查记录
     * 
     * @param bizSiteInspections 监督检查记录
     * @return 结果
     */
    public int updateBizSiteInspections(BizSiteInspections bizSiteInspections);

    /**
     * 删除监督检查记录
     * 
     * @param id 监督检查记录主键
     * @return 结果
     */
    public int deleteBizSiteInspectionsById(Long id);

    /**
     * 批量删除监督检查记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSiteInspectionsByIds(Long[] ids);
}
