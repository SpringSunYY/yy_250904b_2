package com.ruoyi.special.service;

import java.util.List;
import com.ruoyi.special.domain.EquipSpecialBoiler;

/**
 * 锅炉Service接口
 * 
 * @author Laogao
 * @date 2025-11-05
 */
public interface IEquipSpecialBoilerService 
{
    /**
     * 查询锅炉
     * 
     * @param boilerId 锅炉主键
     * @return 锅炉
     */
    public EquipSpecialBoiler selectEquipSpecialBoilerByBoilerId(Long boilerId);

    /**
     * 查询锅炉列表
     * 
     * @param equipSpecialBoiler 锅炉
     * @return 锅炉集合
     */
    public List<EquipSpecialBoiler> selectEquipSpecialBoilerList(EquipSpecialBoiler equipSpecialBoiler);

    /**
     * 新增锅炉
     * 
     * @param equipSpecialBoiler 锅炉
     * @return 结果
     */
    public int insertEquipSpecialBoiler(EquipSpecialBoiler equipSpecialBoiler);

    /**
     * 修改锅炉
     * 
     * @param equipSpecialBoiler 锅炉
     * @return 结果
     */
    public int updateEquipSpecialBoiler(EquipSpecialBoiler equipSpecialBoiler);

    /**
     * 批量删除锅炉
     * 
     * @param boilerIds 需要删除的锅炉主键集合
     * @return 结果
     */
    public int deleteEquipSpecialBoilerByBoilerIds(Long[] boilerIds);

    /**
     * 删除锅炉信息
     * 
     * @param boilerId 锅炉主键
     * @return 结果
     */
    public int deleteEquipSpecialBoilerByBoilerId(Long boilerId);
}
