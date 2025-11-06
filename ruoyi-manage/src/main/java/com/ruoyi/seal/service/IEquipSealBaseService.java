package com.ruoyi.seal.service;

import java.util.List;
import com.ruoyi.seal.domain.EquipSealBase;

/**
 * 密封点Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipSealBaseService 
{
    /**
     * 查询密封点
     * 
     * @param baseId 密封点主键
     * @return 密封点
     */
    public EquipSealBase selectEquipSealBaseByBaseId(Long baseId);

    /**
     * 查询密封点列表
     * 
     * @param equipSealBase 密封点
     * @return 密封点集合
     */
    public List<EquipSealBase> selectEquipSealBaseList(EquipSealBase equipSealBase);

    /**
     * 新增密封点
     * 
     * @param equipSealBase 密封点
     * @return 结果
     */
    public int insertEquipSealBase(EquipSealBase equipSealBase);

    /**
     * 修改密封点
     * 
     * @param equipSealBase 密封点
     * @return 结果
     */
    public int updateEquipSealBase(EquipSealBase equipSealBase);

    /**
     * 批量删除密封点
     * 
     * @param baseIds 需要删除的密封点主键集合
     * @return 结果
     */
    public int deleteEquipSealBaseByBaseIds(Long[] baseIds);

    /**
     * 删除密封点信息
     * 
     * @param baseId 密封点主键
     * @return 结果
     */
    public int deleteEquipSealBaseByBaseId(Long baseId);
}
