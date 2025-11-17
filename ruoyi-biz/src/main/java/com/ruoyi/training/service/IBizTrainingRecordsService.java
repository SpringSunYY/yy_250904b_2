package com.ruoyi.training.service;

import java.util.List;
import com.ruoyi.training.domain.BizTrainingRecords;

/**
 * 培训记录Service接口
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
public interface IBizTrainingRecordsService 
{
    /**
     * 查询培训记录
     * 
     * @param id 培训记录主键
     * @return 培训记录
     */
    public BizTrainingRecords selectBizTrainingRecordsById(Long id);

    /**
     * 查询培训记录列表
     * 
     * @param bizTrainingRecords 培训记录
     * @return 培训记录集合
     */
    public List<BizTrainingRecords> selectBizTrainingRecordsList(BizTrainingRecords bizTrainingRecords);

    /**
     * 新增培训记录
     * 
     * @param bizTrainingRecords 培训记录
     * @return 结果
     */
    public int insertBizTrainingRecords(BizTrainingRecords bizTrainingRecords);

    /**
     * 修改培训记录
     * 
     * @param bizTrainingRecords 培训记录
     * @return 结果
     */
    public int updateBizTrainingRecords(BizTrainingRecords bizTrainingRecords);

    /**
     * 批量删除培训记录
     * 
     * @param ids 需要删除的培训记录主键集合
     * @return 结果
     */
    public int deleteBizTrainingRecordsByIds(Long[] ids);

    /**
     * 删除培训记录信息
     * 
     * @param id 培训记录主键
     * @return 结果
     */
    public int deleteBizTrainingRecordsById(Long id);
}
