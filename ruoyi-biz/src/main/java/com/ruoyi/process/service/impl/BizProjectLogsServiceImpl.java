package com.ruoyi.process.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizProjectLogsMapper;
import com.ruoyi.process.domain.BizProjectLogs;
import com.ruoyi.process.service.IBizProjectLogsService;

/**
 * 项目日志Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizProjectLogsServiceImpl implements IBizProjectLogsService 
{
    @Autowired
    private BizProjectLogsMapper bizProjectLogsMapper;

    /**
     * 查询项目日志
     * 
     * @param logsId 项目日志主键
     * @return 项目日志
     */
    @Override
    public BizProjectLogs selectBizProjectLogsByLogsId(Long logsId)
    {
        return bizProjectLogsMapper.selectBizProjectLogsByLogsId(logsId);
    }

    /**
     * 查询项目日志列表
     * 
     * @param bizProjectLogs 项目日志
     * @return 项目日志
     */
    @Override
    public List<BizProjectLogs> selectBizProjectLogsList(BizProjectLogs bizProjectLogs)
    {
        return bizProjectLogsMapper.selectBizProjectLogsList(bizProjectLogs);
    }

    /**
     * 新增项目日志
     * 
     * @param bizProjectLogs 项目日志
     * @return 结果
     */
    @Override
    public int insertBizProjectLogs(BizProjectLogs bizProjectLogs)
    {
        return bizProjectLogsMapper.insertBizProjectLogs(bizProjectLogs);
    }

    /**
     * 修改项目日志
     * 
     * @param bizProjectLogs 项目日志
     * @return 结果
     */
    @Override
    public int updateBizProjectLogs(BizProjectLogs bizProjectLogs)
    {
        return bizProjectLogsMapper.updateBizProjectLogs(bizProjectLogs);
    }

    /**
     * 批量删除项目日志
     * 
     * @param logsIds 需要删除的项目日志主键
     * @return 结果
     */
    @Override
    public int deleteBizProjectLogsByLogsIds(Long[] logsIds)
    {
        return bizProjectLogsMapper.deleteBizProjectLogsByLogsIds(logsIds);
    }

    /**
     * 删除项目日志信息
     * 
     * @param logsId 项目日志主键
     * @return 结果
     */
    @Override
    public int deleteBizProjectLogsByLogsId(Long logsId)
    {
        return bizProjectLogsMapper.deleteBizProjectLogsByLogsId(logsId);
    }
    
    /**
     * 获取指定年份的最大日志编号
     * 
     * @param year 年份
     * @return 最大日志编号
     */
    @Override
    public String selectMaxLogsCodeByYear(String year) {
        return bizProjectLogsMapper.selectMaxLogsCodeByYear(year);
    }
}
