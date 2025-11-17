package com.ruoyi.training.mapper;

import java.util.List;
import com.ruoyi.training.domain.BizTrainingRecords;
import org.apache.ibatis.annotations.Param;

/**
 * 培训记录Mapper接口
 *
 * @author ruoyi
 * @date 2025-10-24
 */
public interface BizTrainingRecordsMapper
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
     * 删除培训记录
     *
     * @param id 培训记录主键
     * @return 结果
     */
    public int deleteBizTrainingRecordsById(Long id);

    /**
     * 批量删除培训记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizTrainingRecordsByIds(Long[] ids);

    List<BizTrainingRecords> selectBizTrainingRecordsListByStatistics(@Param("choiceId") Long choiceId, @Param("trainingType") String trainingType, @Param("dateType") String dateType, @Param("startDate") String startDate, @Param("endDate") String endDate);
}
