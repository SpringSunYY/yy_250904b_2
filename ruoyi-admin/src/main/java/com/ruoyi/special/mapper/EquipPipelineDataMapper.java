package com.ruoyi.special.mapper;

import java.util.List;
import com.ruoyi.special.domain.EquipPipelineData;

/**
 * 管道数据Mapper接口
 * 
 * @author Laogao
 * @date 2025-11-04
 */
public interface EquipPipelineDataMapper 
{
    /**
     * 查询管道数据
     * 
     * @param pipelineId 管道数据主键
     * @return 管道数据
     */
    public EquipPipelineData selectEquipPipelineDataByPipelineId(String pipelineId);

    /**
     * 查询管道数据列表
     * 
     * @param equipPipelineData 管道数据
     * @return 管道数据集合
     */
    public List<EquipPipelineData> selectEquipPipelineDataList(EquipPipelineData equipPipelineData);

    /**
     * 新增管道数据
     * 
     * @param equipPipelineData 管道数据
     * @return 结果
     */
    public int insertEquipPipelineData(EquipPipelineData equipPipelineData);

    /**
     * 修改管道数据
     * 
     * @param equipPipelineData 管道数据
     * @return 结果
     */
    public int updateEquipPipelineData(EquipPipelineData equipPipelineData);

    /**
     * 删除管道数据
     * 
     * @param pipelineId 管道数据主键
     * @return 结果
     */
    public int deleteEquipPipelineDataByPipelineId(String pipelineId);

    /**
     * 批量删除管道数据
     * 
     * @param pipelineIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipPipelineDataByPipelineIds(String[] pipelineIds);
}
