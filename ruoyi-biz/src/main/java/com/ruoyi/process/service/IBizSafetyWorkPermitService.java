package com.ruoyi.process.service;

import java.util.List;
import com.ruoyi.process.domain.BizSafetyWorkPermit;

/**
 * 特殊作业管理Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizSafetyWorkPermitService 
{
    /**
     * 查询特殊作业管理
     * 
     * @param permitId 特殊作业管理主键
     * @return 特殊作业管理
     */
    public BizSafetyWorkPermit selectBizSafetyWorkPermitByPermitId(Long permitId);

    /**
     * 查询特殊作业管理列表
     * 
     * @param bizSafetyWorkPermit 特殊作业管理
     * @return 特殊作业管理集合
     */
    public List<BizSafetyWorkPermit> selectBizSafetyWorkPermitList(BizSafetyWorkPermit bizSafetyWorkPermit);

    /**
     * 新增特殊作业管理
     * 
     * @param bizSafetyWorkPermit 特殊作业管理
     * @return 结果
     */
    public int insertBizSafetyWorkPermit(BizSafetyWorkPermit bizSafetyWorkPermit);

    /**
     * 修改特殊作业管理
     * 
     * @param bizSafetyWorkPermit 特殊作业管理
     * @return 结果
     */
    public int updateBizSafetyWorkPermit(BizSafetyWorkPermit bizSafetyWorkPermit);

    /**
     * 批量删除特殊作业管理
     * 
     * @param permitIds 需要删除的特殊作业管理主键集合
     * @return 结果
     */
    public int deleteBizSafetyWorkPermitByPermitIds(Long[] permitIds);

    /**
     * 删除特殊作业管理信息
     * 
     * @param permitId 特殊作业管理主键
     * @return 结果
     */
    public int deleteBizSafetyWorkPermitByPermitId(Long permitId);
}
