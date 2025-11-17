package com.ruoyi.contractor.mapper;

import java.util.List;
import com.ruoyi.contractor.domain.BizContractorProject;

/**
 * 发包项目信息Mapper接口
 * 
 * @author Laogao
 * @date 2025-10-23
 */
public interface BizContractorProjectMapper 
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
     * 删除发包项目信息
     * 
     * @param projectId 发包项目信息主键
     * @return 结果
     */
    public int deleteBizContractorProjectByProjectId(Long projectId);

    /**
     * 批量删除发包项目信息
     * 
     * @param projectIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizContractorProjectByProjectIds(Long[] projectIds);
    
    /**
     * 查询指定年份的最大项目编号
     * 
     * @param year 年份
     * @return 项目编号
     */
    public String selectMaxProjectCodeOfYear(String year);
}
