package com.ruoyi.personnel.service;

import java.util.List;
import com.ruoyi.personnel.domain.BizSpecialOperations;

/**
 * 特种人员台账Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizSpecialOperationsService 
{
    /**
     * 查询特种人员台账
     * 
     * @param id 特种人员台账主键
     * @return 特种人员台账
     */
    public BizSpecialOperations selectBizSpecialOperationsById(Long id);

    /**
     * 查询特种人员台账列表
     * 
     * @param bizSpecialOperations 特种人员台账
     * @return 特种人员台账集合
     */
    public List<BizSpecialOperations> selectBizSpecialOperationsList(BizSpecialOperations bizSpecialOperations);

    /**
     * 新增特种人员台账
     * 
     * @param bizSpecialOperations 特种人员台账
     * @return 结果
     */
    public int insertBizSpecialOperations(BizSpecialOperations bizSpecialOperations);

    /**
     * 修改特种人员台账
     * 
     * @param bizSpecialOperations 特种人员台账
     * @return 结果
     */
    public int updateBizSpecialOperations(BizSpecialOperations bizSpecialOperations);

    /**
     * 批量删除特种人员台账
     * 
     * @param ids 需要删除的特种人员台账主键集合
     * @return 结果
     */
    public int deleteBizSpecialOperationsByIds(Long[] ids);

    /**
     * 删除特种人员台账信息
     * 
     * @param id 特种人员台账主键
     * @return 结果
     */
    public int deleteBizSpecialOperationsById(Long id);
}
