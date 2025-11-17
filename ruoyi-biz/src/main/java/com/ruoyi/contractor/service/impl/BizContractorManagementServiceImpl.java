package com.ruoyi.contractor.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contractor.mapper.BizContractorManagementMapper;
import com.ruoyi.contractor.domain.BizContractorManagement;
import com.ruoyi.contractor.service.IBizContractorManagementService;

/**
 * 承包商制度Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-23
 */
@Service
public class BizContractorManagementServiceImpl implements IBizContractorManagementService 
{
    @Autowired
    private BizContractorManagementMapper bizContractorManagementMapper;

    /**
     * 查询承包商制度
     * 
     * @param nanageId 承包商制度主键
     * @return 承包商制度
     */
    @Override
    public BizContractorManagement selectBizContractorManagementByNanageId(Long nanageId)
    {
        return bizContractorManagementMapper.selectBizContractorManagementByNanageId(nanageId);
    }

    /**
     * 查询承包商制度列表
     * 
     * @param bizContractorManagement 承包商制度
     * @return 承包商制度
     */
    @Override
    public List<BizContractorManagement> selectBizContractorManagementList(BizContractorManagement bizContractorManagement)
    {
        return bizContractorManagementMapper.selectBizContractorManagementList(bizContractorManagement);
    }

    /**
     * 新增承包商制度
     * 
     * @param bizContractorManagement 承包商制度
     * @return 结果
     */
    @Override
    public int insertBizContractorManagement(BizContractorManagement bizContractorManagement)
    {
        return bizContractorManagementMapper.insertBizContractorManagement(bizContractorManagement);
    }

    /**
     * 修改承包商制度
     * 
     * @param bizContractorManagement 承包商制度
     * @return 结果
     */
    @Override
    public int updateBizContractorManagement(BizContractorManagement bizContractorManagement)
    {
        return bizContractorManagementMapper.updateBizContractorManagement(bizContractorManagement);
    }

    /**
     * 批量删除承包商制度
     * 
     * @param nanageIds 需要删除的承包商制度主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorManagementByNanageIds(Long[] nanageIds)
    {
        return bizContractorManagementMapper.deleteBizContractorManagementByNanageIds(nanageIds);
    }

    /**
     * 删除承包商制度信息
     * 
     * @param nanageId 承包商制度主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorManagementByNanageId(Long nanageId)
    {
        return bizContractorManagementMapper.deleteBizContractorManagementByNanageId(nanageId);
    }
}
