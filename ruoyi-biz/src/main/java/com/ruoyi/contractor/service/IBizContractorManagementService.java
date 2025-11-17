package com.ruoyi.contractor.service;

import java.util.List;
import com.ruoyi.contractor.domain.BizContractorManagement;

/**
 * 承包商制度Service接口
 * 
 * @author Laogao
 * @date 2025-10-23
 */
public interface IBizContractorManagementService 
{
    /**
     * 查询承包商制度
     * 
     * @param nanageId 承包商制度主键
     * @return 承包商制度
     */
    public BizContractorManagement selectBizContractorManagementByNanageId(Long nanageId);

    /**
     * 查询承包商制度列表
     * 
     * @param bizContractorManagement 承包商制度
     * @return 承包商制度集合
     */
    public List<BizContractorManagement> selectBizContractorManagementList(BizContractorManagement bizContractorManagement);

    /**
     * 新增承包商制度
     * 
     * @param bizContractorManagement 承包商制度
     * @return 结果
     */
    public int insertBizContractorManagement(BizContractorManagement bizContractorManagement);

    /**
     * 修改承包商制度
     * 
     * @param bizContractorManagement 承包商制度
     * @return 结果
     */
    public int updateBizContractorManagement(BizContractorManagement bizContractorManagement);

    /**
     * 批量删除承包商制度
     * 
     * @param nanageIds 需要删除的承包商制度主键集合
     * @return 结果
     */
    public int deleteBizContractorManagementByNanageIds(Long[] nanageIds);

    /**
     * 删除承包商制度信息
     * 
     * @param nanageId 承包商制度主键
     * @return 结果
     */
    public int deleteBizContractorManagementByNanageId(Long nanageId);
}
