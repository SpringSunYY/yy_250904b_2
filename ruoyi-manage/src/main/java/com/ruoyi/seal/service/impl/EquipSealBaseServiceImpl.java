package com.ruoyi.seal.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.seal.mapper.EquipSealBaseMapper;
import com.ruoyi.seal.domain.EquipSealBase;
import com.ruoyi.seal.service.IEquipSealBaseService;

/**
 * 密封点Service业务层处理
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipSealBaseServiceImpl implements IEquipSealBaseService 
{
    @Autowired
    private EquipSealBaseMapper equipSealBaseMapper;

    /**
     * 查询密封点
     * 
     * @param baseId 密封点主键
     * @return 密封点
     */
    @Override
    public EquipSealBase selectEquipSealBaseByBaseId(Long baseId)
    {
        return equipSealBaseMapper.selectEquipSealBaseByBaseId(baseId);
    }

    /**
     * 查询密封点列表
     * 
     * @param equipSealBase 密封点
     * @return 密封点
     */
    @Override
    public List<EquipSealBase> selectEquipSealBaseList(EquipSealBase equipSealBase)
    {
        return equipSealBaseMapper.selectEquipSealBaseList(equipSealBase);
    }

    /**
     * 新增密封点
     * 
     * @param equipSealBase 密封点
     * @return 结果
     */
    @Override
    public int insertEquipSealBase(EquipSealBase equipSealBase)
    {
        equipSealBase.setCreateTime(DateUtils.getNowDate());
        return equipSealBaseMapper.insertEquipSealBase(equipSealBase);
    }

    /**
     * 修改密封点
     * 
     * @param equipSealBase 密封点
     * @return 结果
     */
    @Override
    public int updateEquipSealBase(EquipSealBase equipSealBase)
    {
        equipSealBase.setUpdateTime(DateUtils.getNowDate());
        return equipSealBaseMapper.updateEquipSealBase(equipSealBase);
    }

    /**
     * 批量删除密封点
     * 
     * @param baseIds 需要删除的密封点主键
     * @return 结果
     */
    @Override
    public int deleteEquipSealBaseByBaseIds(Long[] baseIds)
    {
        return equipSealBaseMapper.deleteEquipSealBaseByBaseIds(baseIds);
    }

    /**
     * 删除密封点信息
     * 
     * @param baseId 密封点主键
     * @return 结果
     */
    @Override
    public int deleteEquipSealBaseByBaseId(Long baseId)
    {
        return equipSealBaseMapper.deleteEquipSealBaseByBaseId(baseId);
    }
}
