package com.ruoyi.personnel.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.personnel.mapper.BizSpecialOperationsMapper;
import com.ruoyi.personnel.domain.BizSpecialOperations;
import com.ruoyi.personnel.service.IBizSpecialOperationsService;

/**
 * 特种人员台账Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizSpecialOperationsServiceImpl implements IBizSpecialOperationsService 
{
    @Autowired
    private BizSpecialOperationsMapper bizSpecialOperationsMapper;

    /**
     * 查询特种人员台账
     * 
     * @param id 特种人员台账主键
     * @return 特种人员台账
     */
    @Override
    public BizSpecialOperations selectBizSpecialOperationsById(Long id)
    {
        return bizSpecialOperationsMapper.selectBizSpecialOperationsById(id);
    }

    /**
     * 查询特种人员台账列表
     * 
     * @param bizSpecialOperations 特种人员台账
     * @return 特种人员台账
     */
    @Override
    public List<BizSpecialOperations> selectBizSpecialOperationsList(BizSpecialOperations bizSpecialOperations)
    {
        return bizSpecialOperationsMapper.selectBizSpecialOperationsList(bizSpecialOperations);
    }

    /**
     * 新增特种人员台账
     * 
     * @param bizSpecialOperations 特种人员台账
     * @return 结果
     */
    @Override
    public int insertBizSpecialOperations(BizSpecialOperations bizSpecialOperations)
    {
        return bizSpecialOperationsMapper.insertBizSpecialOperations(bizSpecialOperations);
    }

    /**
     * 修改特种人员台账
     * 
     * @param bizSpecialOperations 特种人员台账
     * @return 结果
     */
    @Override
    public int updateBizSpecialOperations(BizSpecialOperations bizSpecialOperations)
    {
        return bizSpecialOperationsMapper.updateBizSpecialOperations(bizSpecialOperations);
    }

    /**
     * 批量删除特种人员台账
     * 
     * @param ids 需要删除的特种人员台账主键
     * @return 结果
     */
    @Override
    public int deleteBizSpecialOperationsByIds(Long[] ids)
    {
        return bizSpecialOperationsMapper.deleteBizSpecialOperationsByIds(ids);
    }

    /**
     * 删除特种人员台账信息
     * 
     * @param id 特种人员台账主键
     * @return 结果
     */
    @Override
    public int deleteBizSpecialOperationsById(Long id)
    {
        return bizSpecialOperationsMapper.deleteBizSpecialOperationsById(id);
    }
}
