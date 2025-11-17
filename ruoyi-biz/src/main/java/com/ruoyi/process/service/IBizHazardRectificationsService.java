package com.ruoyi.process.service;

import java.util.List;
import com.ruoyi.process.domain.BizHazardRectifications;

/**
 * 隐患整改记录Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizHazardRectificationsService 
{
    /**
     * 查询隐患整改记录
     * 
     * @param id 隐患整改记录主键
     * @return 隐患整改记录
     */
    public BizHazardRectifications selectBizHazardRectificationsById(Long id);

    /**
     * 查询隐患整改记录列表
     * 
     * @param bizHazardRectifications 隐患整改记录
     * @return 隐患整改记录集合
     */
    public List<BizHazardRectifications> selectBizHazardRectificationsList(BizHazardRectifications bizHazardRectifications);

    /**
     * 新增隐患整改记录
     * 
     * @param bizHazardRectifications 隐患整改记录
     * @return 结果
     */
    public int insertBizHazardRectifications(BizHazardRectifications bizHazardRectifications);

    /**
     * 修改隐患整改记录
     * 
     * @param bizHazardRectifications 隐患整改记录
     * @return 结果
     */
    public int updateBizHazardRectifications(BizHazardRectifications bizHazardRectifications);

    /**
     * 批量删除隐患整改记录
     * 
     * @param ids 需要删除的隐患整改记录主键集合
     * @return 结果
     */
    public int deleteBizHazardRectificationsByIds(Long[] ids);

    /**
     * 删除隐患整改记录信息
     * 
     * @param id 隐患整改记录主键
     * @return 结果
     */
    public int deleteBizHazardRectificationsById(Long id);
}
