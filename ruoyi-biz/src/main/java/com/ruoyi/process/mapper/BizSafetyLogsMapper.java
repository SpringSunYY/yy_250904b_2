package com.ruoyi.process.mapper;

import java.util.List;
import com.ruoyi.process.domain.BizSafetyLogs;

/**
 * 安全日志Mapper接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface BizSafetyLogsMapper 
{
    /**
     * 查询安全日志
     * 
     * @param logId 安全日志主键
     * @return 安全日志
     */
    public BizSafetyLogs selectBizSafetyLogsByLogId(Long logId);

    /**
     * 根据日志编号查询安全日志
     * 
     * @param logCode 安全日志编号
     * @return 安全日志
     */
    public BizSafetyLogs selectBizSafetyLogsByLogCode(String logCode);

    /**
     * 查询安全日志列表
     * 
     * @param bizSafetyLogs 安全日志
     * @return 安全日志集合
     */
    public List<BizSafetyLogs> selectBizSafetyLogsList(BizSafetyLogs bizSafetyLogs);

    /**
     * 新增安全日志
     * 
     * @param bizSafetyLogs 安全日志
     * @return 结果
     */
    public int insertBizSafetyLogs(BizSafetyLogs bizSafetyLogs);

    /**
     * 修改安全日志
     * 
     * @param bizSafetyLogs 安全日志
     * @return 结果
     */
    public int updateBizSafetyLogs(BizSafetyLogs bizSafetyLogs);

    /**
     * 删除安全日志
     * 
     * @param logId 安全日志主键
     * @return 结果
     */
    public int deleteBizSafetyLogsByLogId(Long logId);

    /**
     * 批量删除安全日志
     * 
     * @param logIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSafetyLogsByLogIds(Long[] logIds);
}
