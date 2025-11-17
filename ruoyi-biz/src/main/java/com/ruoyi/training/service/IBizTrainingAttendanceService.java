package com.ruoyi.training.service;

import java.util.List;
import com.ruoyi.training.domain.BizTrainingAttendance;

/**
 * 人员参训记录Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizTrainingAttendanceService 
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
     * 批量删除人员参训记录
     * 
     * @param ids 需要删除的人员参训记录主键集合
     * @return 结果
     */
    public int deleteBizTrainingAttendanceByIds(Long[] ids);

    /**
     * 删除人员参训记录信息
     * 
     * @param id 人员参训记录主键
     * @return 结果
     */
    public int deleteBizTrainingAttendanceById(Long id);
}
