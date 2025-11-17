package com.ruoyi.contractor.service.impl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.contractor.mapper.BizContractorProjectMapper;
import com.ruoyi.contractor.domain.BizContractorProject;
import com.ruoyi.contractor.service.IBizContractorProjectService;

/**
 * 发包项目信息Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-23
 */
@Service
public class BizContractorProjectServiceImpl implements IBizContractorProjectService 
{
    @Autowired
    private BizContractorProjectMapper bizContractorProjectMapper;

    /**
     * 查询发包项目信息
     * 
     * @param projectId 发包项目信息主键
     * @return 发包项目信息
     */
    @Override
    public BizContractorProject selectBizContractorProjectByProjectId(Long projectId)
    {
        return bizContractorProjectMapper.selectBizContractorProjectByProjectId(projectId);
    }

    /**
     * 查询发包项目信息列表
     * 
     * @param bizContractorProject 发包项目信息
     * @return 发包项目信息
     */
    @Override
    public List<BizContractorProject> selectBizContractorProjectList(BizContractorProject bizContractorProject)
    {
        return bizContractorProjectMapper.selectBizContractorProjectList(bizContractorProject);
    }

    /**
     * 新增发包项目信息
     * 
     * @param bizContractorProject 发包项目信息
     * @return 结果
     */
    @Override
    public int insertBizContractorProject(BizContractorProject bizContractorProject)
    {
        // 设置创建人和创建时间
        bizContractorProject.setCreateTime(new Date());
        bizContractorProject.setCreateBy(bizContractorProject.getCreatedBy());
        
        // 如果项目代码为空，则生成一个唯一的项目代码
        if (bizContractorProject.getProjectCode() == null || bizContractorProject.getProjectCode().isEmpty()) {
            // 生成格式为 XM + 年份 + 三位序号 的项目编号
            String year = String.valueOf(java.time.Year.now().getValue());
            // 确保年份为4位数
            if (year.length() > 4) {
                year = year.substring(0, 4);
            }
            
            // 查询当年最大的项目编号以获取序号
            String maxProjectCode = bizContractorProjectMapper.selectMaxProjectCodeOfYear(year);
            int sequence = 1;
            
            if (maxProjectCode != null && !maxProjectCode.isEmpty()) {
                try {
                    // 检查编号格式是否正确（XM + 4位年份 + 3位序号 = 9位）
                    if (maxProjectCode.length() == 9 && maxProjectCode.startsWith("XM" + year)) {
                        // 从类似 XM2025002 的编号中提取序号部分
                        String sequenceStr = maxProjectCode.substring(6); // 去掉 "XM" + "年份"(4位)
                        int currentSequence = Integer.parseInt(sequenceStr);
                        // 确保序号不超过999
                        if (currentSequence < 999) {
                            sequence = currentSequence + 1;
                        } else {
                            // 序号已达最大值
                            throw new RuntimeException("当年项目编号已达到最大值 XM" + year + "999");
                        }
                    }
                } catch (RuntimeException e) {
                    throw e; // 重新抛出我们自定义的异常
                } catch (Exception e) {
                    sequence = 1; // 解析出错时从1开始
                }
            }
            
            // 格式化为3位数字，不足的前面补0
            String projectCode = "XM" + year + String.format("%03d", sequence);
            bizContractorProject.setProjectCode(projectCode);
        }
        return bizContractorProjectMapper.insertBizContractorProject(bizContractorProject);
    }

    /**
     * 修改发包项目信息
     * 
     * @param bizContractorProject 发包项目信息
     * @return 结果
     */
    @Override
    public int updateBizContractorProject(BizContractorProject bizContractorProject)
    {
        return bizContractorProjectMapper.updateBizContractorProject(bizContractorProject);
    }

    /**
     * 批量删除发包项目信息
     * 
     * @param projectIds 需要删除的发包项目信息主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorProjectByProjectIds(Long[] projectIds)
    {
        return bizContractorProjectMapper.deleteBizContractorProjectByProjectIds(projectIds);
    }

    /**
     * 删除发包项目信息信息
     * 
     * @param projectId 发包项目信息主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorProjectByProjectId(Long projectId)
    {
        return bizContractorProjectMapper.deleteBizContractorProjectByProjectId(projectId);
    }
}
