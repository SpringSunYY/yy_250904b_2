package com.ruoyi.contents.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contents.mapper.BizContractorBlacklistMapper;
import com.ruoyi.contents.domain.BizContractorBlacklist;
import com.ruoyi.contents.service.IBizContractorBlacklistService;

/**
 * 承包商黑名单Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizContractorBlacklistServiceImpl implements IBizContractorBlacklistService 
{
    @Autowired
    private BizContractorBlacklistMapper bizContractorBlacklistMapper;

    /**
     * 查询承包商黑名单
     * 
     * @param blackId 承包商黑名单主键
     * @return 承包商黑名单
     */
    @Override
    public BizContractorBlacklist selectBizContractorBlacklistByBlackId(Long blackId)
    {
        return bizContractorBlacklistMapper.selectBizContractorBlacklistByBlackId(blackId);
    }

    /**
     * 查询承包商黑名单列表
     * 
     * @param bizContractorBlacklist 承包商黑名单
     * @return 承包商黑名单
     */
    @Override
    public List<BizContractorBlacklist> selectBizContractorBlacklistList(BizContractorBlacklist bizContractorBlacklist)
    {
        return bizContractorBlacklistMapper.selectBizContractorBlacklistList(bizContractorBlacklist);
    }

    /**
     * 新增承包商黑名单
     * 
     * @param bizContractorBlacklist 承包商黑名单
     * @return 结果
     */
    @Override
    public int insertBizContractorBlacklist(BizContractorBlacklist bizContractorBlacklist)
    {
        return bizContractorBlacklistMapper.insertBizContractorBlacklist(bizContractorBlacklist);
    }

    /**
     * 修改承包商黑名单
     * 
     * @param bizContractorBlacklist 承包商黑名单
     * @return 结果
     */
    @Override
    public int updateBizContractorBlacklist(BizContractorBlacklist bizContractorBlacklist)
    {
        return bizContractorBlacklistMapper.updateBizContractorBlacklist(bizContractorBlacklist);
    }

    /**
     * 批量删除承包商黑名单
     * 
     * @param blackIds 需要删除的承包商黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorBlacklistByBlackIds(Long[] blackIds)
    {
        return bizContractorBlacklistMapper.deleteBizContractorBlacklistByBlackIds(blackIds);
    }

    /**
     * 删除承包商黑名单信息
     * 
     * @param blackId 承包商黑名单主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorBlacklistByBlackId(Long blackId)
    {
        return bizContractorBlacklistMapper.deleteBizContractorBlacklistByBlackId(blackId);
    }
}
