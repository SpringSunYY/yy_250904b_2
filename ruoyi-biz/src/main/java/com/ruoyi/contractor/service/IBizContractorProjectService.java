package com.ruoyi.contractor.service;

import java.util.List;
import com.ruoyi.contractor.domain.BizContractorProject;

/**
 * 发包项目信息Service接口
 * 
 * @author Laogao
 * @date 2025-10-23
 */
public interface IBizContractorProjectService 
{
    /**
     * 查询发包项目信息
     * 
     * @param projectId 发包项目信息主键
     * @return 发包项目信息
     */
    public BizContractorProject selectBizContractorProjectByProjectId(Long projectId);

    /**
     * 查询发包项目信息列表
     * 
     * @param bizContractorProject 发包项目信息
     * @return 发包项目信息集合
     */
    public List<BizContractorProject> selectBizContractorProjectList(BizContractorProject bizContractorProject);

    /**
     * 新增发包项目信息
     * 
     * @param bizContractorProject 发包项目信息
     * @return 结果
     */
    public int insertBizContractorProject(BizContractorProject bizContractorProject);

    /**
     * 修改发包项目信息
     * 
     * @param bizContractorProject 发包项目信息
     * @return 结果
     */
    public int updateBizContractorProject(BizContractorProject bizContractorProject);

    /**
     * 批量删除发包项目信息
     * 
     * @param projectIds 需要删除的发包项目信息主键集合
     * @return 结果
     */
    public int deleteBizContractorProjectByProjectIds(Long[] projectIds);

    /**
     * 删除发包项目信息信息
     * 
     * @param projectId 发包项目信息主键
     * @return 结果
     */
    public int deleteBizContractorProjectByProjectId(Long projectId);
}
