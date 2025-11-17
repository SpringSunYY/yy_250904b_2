package com.ruoyi.prepare.mapper;

import java.util.List;
import com.ruoyi.prepare.domain.BizSafetyBriefing;

/**
 * 安全技术交底Mapper接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface BizSafetyBriefingMapper 
{
    /**
     * 查询安全技术交底
     * 
     * @param id 安全技术交底主键
     * @return 安全技术交底
     */
    public BizSafetyBriefing selectBizSafetyBriefingById(Long id);

    /**
     * 查询安全技术交底列表
     * 
     * @param bizSafetyBriefing 安全技术交底
     * @return 安全技术交底集合
     */
    public List<BizSafetyBriefing> selectBizSafetyBriefingList(BizSafetyBriefing bizSafetyBriefing);

    /**
     * 新增安全技术交底
     * 
     * @param bizSafetyBriefing 安全技术交底
     * @return 结果
     */
    public int insertBizSafetyBriefing(BizSafetyBriefing bizSafetyBriefing);

    /**
     * 修改安全技术交底
     * 
     * @param bizSafetyBriefing 安全技术交底
     * @return 结果
     */
    public int updateBizSafetyBriefing(BizSafetyBriefing bizSafetyBriefing);

    /**
     * 删除安全技术交底
     * 
     * @param id 安全技术交底主键
     * @return 结果
     */
    public int deleteBizSafetyBriefingById(Long id);

    /**
     * 批量删除安全技术交底
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSafetyBriefingByIds(Long[] ids);
}
