package com.ruoyi.training.mapper;

import java.util.List;

import com.ruoyi.statistics.domain.ro.BizStatisticsRo;
import com.ruoyi.training.domain.BizTrainingAttendance;
import org.apache.ibatis.annotations.Param;

/**
 * 人员参训记录Mapper接口
 *
 * @author Laogao
 * @date 2025-10-24
 */
public interface BizTrainingAttendanceMapper
{
    /**
     * 查询人员参训记录
     *
     * @param id 人员参训记录主键
     * @return 人员参训记录
     */
    public BizTrainingAttendance selectBizTrainingAttendanceById(Long id);

    /**
     * 查询人员参训记录列表
     *
     * @param bizTrainingAttendance 人员参训记录
     * @return 人员参训记录集合
     */
    public List<BizTrainingAttendance> selectBizTrainingAttendanceList(BizTrainingAttendance bizTrainingAttendance);

    /**
     * 新增人员参训记录
     *
     * @param bizTrainingAttendance 人员参训记录
     * @return 结果
     */
    public int insertBizTrainingAttendance(BizTrainingAttendance bizTrainingAttendance);

    /**
     * 修改人员参训记录
     *
     * @param bizTrainingAttendance 人员参训记录
     * @return 结果
     */
    public int updateBizTrainingAttendance(BizTrainingAttendance bizTrainingAttendance);

    /**
     * 删除人员参训记录
     *
     * @param id 人员参训记录主键
     * @return 结果
     */
    public int deleteBizTrainingAttendanceById(Long id);

    /**
     * 批量删除人员参训记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizTrainingAttendanceByIds(Long[] ids);

    List<BizStatisticsRo<Long>> selectTrainingRecordHistory(@Param("trainingIds") List<Long> trainingIds);
}
