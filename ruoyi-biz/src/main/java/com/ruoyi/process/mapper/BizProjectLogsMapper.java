package com.ruoyi.process.mapper;

import java.util.List;
import com.ruoyi.process.domain.BizProjectLogs;

/**
 * 项目日志Mapper接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface BizProjectLogsMapper 
{
    /**
     * 查询项目日志
     * 
     * @param logsId 项目日志主键
     * @return 项目日志
     */
    public BizProjectLogs selectBizProjectLogsByLogsId(Long logsId);

    /**
     * 查询项目日志列表
     * 
     * @param bizProjectLogs 项目日志
     * @return 项目日志集合
     */
    public List<BizProjectLogs> selectBizProjectLogsList(BizProjectLogs bizProjectLogs);

    /**
     * 新增项目日志
     * 
     * @param bizProjectLogs 项目日志
     * @return 结果
     */
    public int insertBizProjectLogs(BizProjectLogs bizProjectLogs);

    /**
     * 修改项目日志
     * 
     * @param bizProjectLogs 项目日志
     * @return 结果
     */
    public int updateBizProjectLogs(BizProjectLogs bizProjectLogs);

    /**
     * 删除项目日志
     * 
     * @param logsId 项目日志主键
     * @return 结果
     */
    public int deleteBizProjectLogsByLogsId(Long logsId);

    /**
     * 批量删除项目日志
     * 
     * @param logsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizProjectLogsByLogsIds(Long[] logsIds);
    
    /**
     * 获取指定年份的最大日志编号
     * 
     * @param year 年份
     * @return 最大日志编号
     */
    public String selectMaxLogsCodeByYear(String year);
}
