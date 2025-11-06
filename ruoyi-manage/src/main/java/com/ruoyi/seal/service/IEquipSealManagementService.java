package com.ruoyi.seal.service;

import java.util.List;
import com.ruoyi.seal.domain.EquipSealManagement;

/**
 * 泄露处置Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipSealManagementService 
{
    /**
     * 查询泄露处置
     * 
     * @param manageId 泄露处置主键
     * @return 泄露处置
     */
    public EquipSealManagement selectEquipSealManagementByManageId(Long manageId);

    /**
     * 查询泄露处置列表
     * 
     * @param equipSealManagement 泄露处置
     * @return 泄露处置集合
     */
    public List<EquipSealManagement> selectEquipSealManagementList(EquipSealManagement equipSealManagement);

    /**
     * 新增泄露处置
     * 
     * @param equipSealManagement 泄露处置
     * @return 结果
     */
    public int insertEquipSealManagement(EquipSealManagement equipSealManagement);

    /**
     * 修改泄露处置
     * 
     * @param equipSealManagement 泄露处置
     * @return 结果
     */
    public int updateEquipSealManagement(EquipSealManagement equipSealManagement);

    /**
     * 批量删除泄露处置
     * 
     * @param manageIds 需要删除的泄露处置主键集合
     * @return 结果
     */
    public int deleteEquipSealManagementByManageIds(Long[] manageIds);

    /**
     * 删除泄露处置信息
     * 
     * @param manageId 泄露处置主键
     * @return 结果
     */
    public int deleteEquipSealManagementByManageId(Long manageId);
}
