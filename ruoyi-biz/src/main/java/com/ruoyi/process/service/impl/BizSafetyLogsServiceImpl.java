package com.ruoyi.process.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizSafetyLogsMapper;
import com.ruoyi.process.domain.BizSafetyLogs;
import com.ruoyi.process.service.IBizSafetyLogsService;

/**
 * 安全日志Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizSafetyLogsServiceImpl implements IBizSafetyLogsService 
{
    @Autowired
    private BizSafetyLogsMapper bizSafetyLogsMapper;

    /**
     * 查询安全日志
     * 
     * @param logId 安全日志主键
     * @return 安全日志
     */
    @Override
    public BizSafetyLogs selectBizSafetyLogsByLogId(Long logId)
    {
        return bizSafetyLogsMapper.selectBizSafetyLogsByLogId(logId);
    }

    /**
     * 根据日志编号查询安全日志
     * 
     * @param logCode 安全日志编号
     * @return 安全日志
     */
    @Override
    public BizSafetyLogs selectBizSafetyLogsByLogCode(String logCode)
    {
        return bizSafetyLogsMapper.selectBizSafetyLogsByLogCode(logCode);
    }

    /**
     * 查询安全日志列表
     * 
     * @param bizSafetyLogs 安全日志
     * @return 安全日志
     */
    @Override
    public List<BizSafetyLogs> selectBizSafetyLogsList(BizSafetyLogs bizSafetyLogs)
    {
        return bizSafetyLogsMapper.selectBizSafetyLogsList(bizSafetyLogs);
    }

    /**
     * 新增安全日志
     * 
     * @param bizSafetyLogs 安全日志
     * @return 结果
     */
    @Override
    public int insertBizSafetyLogs(BizSafetyLogs bizSafetyLogs)
    {
        return bizSafetyLogsMapper.insertBizSafetyLogs(bizSafetyLogs);
    }

    /**
     * 修改安全日志
     * 
     * @param bizSafetyLogs 安全日志
     * @return 结果
     */
    @Override
    public int updateBizSafetyLogs(BizSafetyLogs bizSafetyLogs)
    {
        return bizSafetyLogsMapper.updateBizSafetyLogs(bizSafetyLogs);
    }

    /**
     * 批量删除安全日志
     * 
     * @param logIds 需要删除的安全日志主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyLogsByLogIds(Long[] logIds)
    {
        return bizSafetyLogsMapper.deleteBizSafetyLogsByLogIds(logIds);
    }

    /**
     * 删除安全日志信息
     * 
     * @param logId 安全日志主键
     * @return 结果
     */
    @Override
    public int deleteBizSafetyLogsByLogId(Long logId)
    {
        return bizSafetyLogsMapper.deleteBizSafetyLogsByLogId(logId);
    }
}
