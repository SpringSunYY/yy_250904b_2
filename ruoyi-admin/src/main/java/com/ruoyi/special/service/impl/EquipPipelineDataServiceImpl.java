package com.ruoyi.special.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.special.mapper.EquipPipelineDataMapper;
import com.ruoyi.special.domain.EquipPipelineData;
import com.ruoyi.special.service.IEquipPipelineDataService;

/**
 * 管道数据Service业务层处理
 * 
 * @author Laogao
 * @date 2025-11-04
 */
@Service
public class EquipPipelineDataServiceImpl implements IEquipPipelineDataService 
{
    @Autowired
    private EquipPipelineDataMapper equipPipelineDataMapper;

    /**
     * 查询管道数据
     * 
     * @param pipelineId 管道数据主键
     * @return 管道数据
     */
    @Override
    public EquipPipelineData selectEquipPipelineDataByPipelineId(String pipelineId)
    {
        return equipPipelineDataMapper.selectEquipPipelineDataByPipelineId(pipelineId);
    }

    /**
     * 查询管道数据列表
     * 
     * @param equipPipelineData 管道数据
     * @return 管道数据
     */
    @Override
    public List<EquipPipelineData> selectEquipPipelineDataList(EquipPipelineData equipPipelineData)
    {
        return equipPipelineDataMapper.selectEquipPipelineDataList(equipPipelineData);
    }

    /**
     * 新增管道数据
     * 
     * @param equipPipelineData 管道数据
     * @return 结果
     */
    @Override
    public int insertEquipPipelineData(EquipPipelineData equipPipelineData)
    {
        if (equipPipelineData.getPipelineId() == null || equipPipelineData.getPipelineId().isEmpty()) {
            equipPipelineData.setPipelineId(java.util.UUID.randomUUID().toString());
        }
        return equipPipelineDataMapper.insertEquipPipelineData(equipPipelineData);
    }

    /**
     * 修改管道数据
     * 
     * @param equipPipelineData 管道数据
     * @return 结果
     */
    @Override
    public int updateEquipPipelineData(EquipPipelineData equipPipelineData)
    {
        return equipPipelineDataMapper.updateEquipPipelineData(equipPipelineData);
    }

    /**
     * 批量删除管道数据
     * 
     * @param pipelineIds 需要删除的管道数据主键
     * @return 结果
     */
    @Override
    public int deleteEquipPipelineDataByPipelineIds(String[] pipelineIds)
    {
        return equipPipelineDataMapper.deleteEquipPipelineDataByPipelineIds(pipelineIds);
    }

    /**
     * 删除管道数据信息
     * 
     * @param pipelineId 管道数据主键
     * @return 结果
     */
    @Override
    public int deleteEquipPipelineDataByPipelineId(String pipelineId)
    {
        return equipPipelineDataMapper.deleteEquipPipelineDataByPipelineId(pipelineId);
    }
}
