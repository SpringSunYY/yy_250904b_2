package com.ruoyi.process.service;

import java.util.List;
import com.ruoyi.process.domain.BizProjectChange;

/**
 * 项目变更管理Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizProjectChangeService 
{
    /**
     * 查询项目变更管理
     * 
     * @param changeId 项目变更管理主键
     * @return 项目变更管理
     */
    public BizProjectChange selectBizProjectChangeByChangeId(Long changeId);

    /**
     * 查询项目变更管理列表
     * 
     * @param bizProjectChange 项目变更管理
     * @return 项目变更管理集合
     */
    public List<BizProjectChange> selectBizProjectChangeList(BizProjectChange bizProjectChange);

    /**
     * 新增项目变更管理
     * 
     * @param bizProjectChange 项目变更管理
     * @return 结果
     */
    public int insertBizProjectChange(BizProjectChange bizProjectChange);

    /**
     * 修改项目变更管理
     * 
     * @param bizProjectChange 项目变更管理
     * @return 结果
     */
    public int updateBizProjectChange(BizProjectChange bizProjectChange);

    /**
     * 批量删除项目变更管理
     * 
     * @param changeIds 需要删除的项目变更管理主键集合
     * @return 结果
     */
    public int deleteBizProjectChangeByChangeIds(Long[] changeIds);

    /**
     * 删除项目变更管理信息
     * 
     * @param changeId 项目变更管理主键
     * @return 结果
     */
    public int deleteBizProjectChangeByChangeId(Long changeId);
}
