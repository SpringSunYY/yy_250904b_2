package com.ruoyi.contents.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contents.mapper.BizContractorQualifiedMapper;
import com.ruoyi.contents.domain.BizContractorQualified;
import com.ruoyi.contents.service.IBizContractorQualifiedService;

/**
 * 合格承包商Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizContractorQualifiedServiceImpl implements IBizContractorQualifiedService 
{
    @Autowired
    private BizContractorQualifiedMapper bizContractorQualifiedMapper;

    /**
     * 查询合格承包商
     * 
     * @param qualId 合格承包商主键
     * @return 合格承包商
     */
    @Override
    public BizContractorQualified selectBizContractorQualifiedByQualId(Long qualId)
    {
        return bizContractorQualifiedMapper.selectBizContractorQualifiedByQualId(qualId);
    }

    /**
     * 查询合格承包商列表
     * 
     * @param bizContractorQualified 合格承包商
     * @return 合格承包商
     */
    @Override
    public List<BizContractorQualified> selectBizContractorQualifiedList(BizContractorQualified bizContractorQualified)
    {
        return bizContractorQualifiedMapper.selectBizContractorQualifiedList(bizContractorQualified);
    }

    /**
     * 新增合格承包商
     * 
     * @param bizContractorQualified 合格承包商
     * @return 结果
     */
    @Override
    public int insertBizContractorQualified(BizContractorQualified bizContractorQualified)
    {
        return bizContractorQualifiedMapper.insertBizContractorQualified(bizContractorQualified);
    }

    /**
     * 修改合格承包商
     * 
     * @param bizContractorQualified 合格承包商
     * @return 结果
     */
    @Override
    public int updateBizContractorQualified(BizContractorQualified bizContractorQualified)
    {
        return bizContractorQualifiedMapper.updateBizContractorQualified(bizContractorQualified);
    }

    /**
     * 批量删除合格承包商
     * 
     * @param qualIds 需要删除的合格承包商主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorQualifiedByQualIds(Long[] qualIds)
    {
        return bizContractorQualifiedMapper.deleteBizContractorQualifiedByQualIds(qualIds);
    }

    /**
     * 删除合格承包商信息
     * 
     * @param qualId 合格承包商主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorQualifiedByQualId(Long qualId)
    {
        return bizContractorQualifiedMapper.deleteBizContractorQualifiedByQualId(qualId);
    }
}
