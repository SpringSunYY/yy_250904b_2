package com.ruoyi.prepare.service;

import java.util.List;
import com.ruoyi.prepare.domain.BizSafetyBriefing;

/**
 * 安全技术交底Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizSafetyBriefingService 
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
     * 批量删除安全技术交底
     * 
     * @param ids 需要删除的安全技术交底主键集合
     * @return 结果
     */
    public int deleteBizSafetyBriefingByIds(Long[] ids);

    /**
     * 删除安全技术交底信息
     * 
     * @param id 安全技术交底主键
     * @return 结果
     */
    public int deleteBizSafetyBriefingById(Long id);
}
