package com.ruoyi.prepare.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.prepare.mapper.BizSafetyBriefingMapper;
import com.ruoyi.prepare.domain.BizSafetyBriefing;
import com.ruoyi.prepare.service.IBizSafetyBriefingService;

/**
 * 安全技术交底Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizSafetyBriefingServiceImpl implements IBizSafetyBriefingService 
{
    @Autowired
    private BizSafetyBriefingMapper bizSafetyBriefingMapper;

    /**
     * 查询安全技术交底
     * 
     * @param id 安全技术交底主键
     * @return 安全技术交底
     */
    @Override
    public BizSafetyBriefing selectBizSafetyBriefingById(Long id)
    {
        return bizSafetyBriefingMapper.selectBizSafetyBriefingById(id);
    }

    /**
     * 查询安全技术交底列表
     * 
     * @param bizSafetyBriefing 安全技术交底
     * @return 安全技术交底
     */
    @Override
    public List<BizSafetyBriefing> selectBizSafetyBriefingList(BizSafetyBriefing bizSafetyBriefing)
    {
        return bizSafetyBriefingMapper.selectBizSafetyBriefingList(bizSafetyBriefing);
    }

    /**
     * 新增安全技术交底
     * 
     * @param bizSafetyBriefing 安全技术交底
     * @return 结果
     */
    @Override
    public int insertBizSafetyBriefing(BizSafetyBriefing bizSafetyBriefing)
    {
        return bizSafetyBriefingMapper.insertBizSafetyBriefing(bizSafetyBriefing);
    }

    /**
     * 修改安全技术交底
     * 
     * @param bizSafetyBriefing 安全技术交底
     * @return 结果
     */
    @Override
    public int updateBizSafetyBriefing(BizSafetyBriefing bizSafetyBriefing)
    {
        return bizSafetyBriefingMapper.updateBizSafetyBriefing(bizSafetyBriefing);
    }

    /**
     * 批量删除安全技术交底
     * 
     * @param ids 需要删除的安全技术交底主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyBriefingByIds(Long[] ids)
    {
        return bizSafetyBriefingMapper.deleteBizSafetyBriefingByIds(ids);
    }

    /**
     * 删除安全技术交底信息
     * 
     * @param id 安全技术交底主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyBriefingById(Long id)
    {
        return bizSafetyBriefingMapper.deleteBizSafetyBriefingById(id);
    }
}
