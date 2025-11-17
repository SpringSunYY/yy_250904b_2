package com.ruoyi.contents.service;

import java.util.List;
import com.ruoyi.contents.domain.BizContractorQualified;

/**
 * 合格承包商Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizContractorQualifiedService 
{
    /**
     * 查询合格承包商
     * 
     * @param qualId 合格承包商主键
     * @return 合格承包商
     */
    public BizContractorQualified selectBizContractorQualifiedByQualId(Long qualId);

    /**
     * 查询合格承包商列表
     * 
     * @param bizContractorQualified 合格承包商
     * @return 合格承包商集合
     */
    public List<BizContractorQualified> selectBizContractorQualifiedList(BizContractorQualified bizContractorQualified);

    /**
     * 新增合格承包商
     * 
     * @param bizContractorQualified 合格承包商
     * @return 结果
     */
    public int insertBizContractorQualified(BizContractorQualified bizContractorQualified);

    /**
     * 修改合格承包商
     * 
     * @param bizContractorQualified 合格承包商
     * @return 结果
     */
    public int updateBizContractorQualified(BizContractorQualified bizContractorQualified);

    /**
     * 批量删除合格承包商
     * 
     * @param qualIds 需要删除的合格承包商主键集合
     * @return 结果
     */
    public int deleteBizContractorQualifiedByQualIds(Long[] qualIds);

    /**
     * 删除合格承包商信息
     * 
     * @param qualId 合格承包商主键
     * @return 结果
     */
    public int deleteBizContractorQualifiedByQualId(Long qualId);
}
