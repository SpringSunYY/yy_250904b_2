package com.ruoyi.contents.service;

import java.util.List;
import com.ruoyi.contents.domain.BizContractorBlacklist;

/**
 * 承包商黑名单Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizContractorBlacklistService 
{
    /**
     * 查询承包商黑名单
     * 
     * @param blackId 承包商黑名单主键
     * @return 承包商黑名单
     */
    public BizContractorBlacklist selectBizContractorBlacklistByBlackId(Long blackId);

    /**
     * 查询承包商黑名单列表
     * 
     * @param bizContractorBlacklist 承包商黑名单
     * @return 承包商黑名单集合
     */
    public List<BizContractorBlacklist> selectBizContractorBlacklistList(BizContractorBlacklist bizContractorBlacklist);

    /**
     * 新增承包商黑名单
     * 
     * @param bizContractorBlacklist 承包商黑名单
     * @return 结果
     */
    public int insertBizContractorBlacklist(BizContractorBlacklist bizContractorBlacklist);

    /**
     * 修改承包商黑名单
     * 
     * @param bizContractorBlacklist 承包商黑名单
     * @return 结果
     */
    public int updateBizContractorBlacklist(BizContractorBlacklist bizContractorBlacklist);

    /**
     * 批量删除承包商黑名单
     * 
     * @param blackIds 需要删除的承包商黑名单主键集合
     * @return 结果
     */
    public int deleteBizContractorBlacklistByBlackIds(Long[] blackIds);

    /**
     * 删除承包商黑名单信息
     * 
     * @param blackId 承包商黑名单主键
     * @return 结果
     */
    public int deleteBizContractorBlacklistByBlackId(Long blackId);
}
