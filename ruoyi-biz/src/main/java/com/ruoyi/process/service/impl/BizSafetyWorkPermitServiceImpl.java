package com.ruoyi.process.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizSafetyWorkPermitMapper;
import com.ruoyi.process.domain.BizSafetyWorkPermit;
import com.ruoyi.process.service.IBizSafetyWorkPermitService;

/**
 * 特殊作业管理Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizSafetyWorkPermitServiceImpl implements IBizSafetyWorkPermitService 
{
    @Autowired
    private BizSafetyWorkPermitMapper bizSafetyWorkPermitMapper;

    /**
     * 查询特殊作业管理
     * 
     * @param permitId 特殊作业管理主键
     * @return 特殊作业管理
     */
    @Override
    public BizSafetyWorkPermit selectBizSafetyWorkPermitByPermitId(Long permitId)
    {
        return bizSafetyWorkPermitMapper.selectBizSafetyWorkPermitByPermitId(permitId);
    }

    /**
     * 查询特殊作业管理列表
     * 
     * @param bizSafetyWorkPermit 特殊作业管理
     * @return 特殊作业管理
     */
    @Override
    public List<BizSafetyWorkPermit> selectBizSafetyWorkPermitList(BizSafetyWorkPermit bizSafetyWorkPermit)
    {
        return bizSafetyWorkPermitMapper.selectBizSafetyWorkPermitList(bizSafetyWorkPermit);
    }

    /**
     * 新增特殊作业管理
     * 
     * @param bizSafetyWorkPermit 特殊作业管理
     * @return 结果
     */
    @Override
    public int insertBizSafetyWorkPermit(BizSafetyWorkPermit bizSafetyWorkPermit)
    {
        bizSafetyWorkPermit.setCreateTime(DateUtils.getNowDate());
        return bizSafetyWorkPermitMapper.insertBizSafetyWorkPermit(bizSafetyWorkPermit);
    }

    /**
     * 修改特殊作业管理
     * 
     * @param bizSafetyWorkPermit 特殊作业管理
     * @return 结果
     */
    @Override
    public int updateBizSafetyWorkPermit(BizSafetyWorkPermit bizSafetyWorkPermit)
    {
        bizSafetyWorkPermit.setUpdateTime(DateUtils.getNowDate());
        return bizSafetyWorkPermitMapper.updateBizSafetyWorkPermit(bizSafetyWorkPermit);
    }

    /**
     * 批量删除特殊作业管理
     * 
     * @param permitIds 需要删除的特殊作业管理主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyWorkPermitByPermitIds(Long[] permitIds)
    {
        return bizSafetyWorkPermitMapper.deleteBizSafetyWorkPermitByPermitIds(permitIds);
    }

    /**
     * 删除特殊作业管理信息
     * 
     * @param permitId 特殊作业管理主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyWorkPermitByPermitId(Long permitId)
    {
        return bizSafetyWorkPermitMapper.deleteBizSafetyWorkPermitByPermitId(permitId);
    }
}
