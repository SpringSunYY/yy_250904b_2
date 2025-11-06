package com.ruoyi.anticorrosion.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.anticorrosion.mapper.EquipAnticorrosionMapper;
import com.ruoyi.anticorrosion.domain.EquipAnticorrosion;
import com.ruoyi.anticorrosion.service.IEquipAnticorrosionService;

/**
 * 防腐蚀检测Service业务层处理
 * 
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipAnticorrosionServiceImpl implements IEquipAnticorrosionService 
{
    @Autowired
    private EquipAnticorrosionMapper equipAnticorrosionMapper;

    /**
     * 查询防腐蚀检测
     * 
     * @param id 防腐蚀检测主键
     * @return 防腐蚀检测
     */
    @Override
    public EquipAnticorrosion selectEquipAnticorrosionById(Long id)
    {
        return equipAnticorrosionMapper.selectEquipAnticorrosionById(id);
    }

    /**
     * 查询防腐蚀检测列表
     * 
     * @param equipAnticorrosion 防腐蚀检测
     * @return 防腐蚀检测
     */
    @Override
    public List<EquipAnticorrosion> selectEquipAnticorrosionList(EquipAnticorrosion equipAnticorrosion)
    {
        return equipAnticorrosionMapper.selectEquipAnticorrosionList(equipAnticorrosion);
    }

    /**
     * 新增防腐蚀检测
     * 
     * @param equipAnticorrosion 防腐蚀检测
     * @return 结果
     */
    @Override
    public int insertEquipAnticorrosion(EquipAnticorrosion equipAnticorrosion)
    {
        // 自动生成记录编号
        if (equipAnticorrosion.getRecordNo() == null || equipAnticorrosion.getRecordNo().isEmpty()) {
            String recordNo = "FSCJ" + DateUtils.dateTimeNow("yyyyMMddHHmmss"); // FSCJ代表"防腐蚀检测"
            equipAnticorrosion.setRecordNo(recordNo);
        }
        equipAnticorrosion.setCreateTime(DateUtils.getNowDate());
        return equipAnticorrosionMapper.insertEquipAnticorrosion(equipAnticorrosion);
    }

    /**
     * 修改防腐蚀检测
     * 
     * @param equipAnticorrosion 防腐蚀检测
     * @return 结果
     */
    @Override
    public int updateEquipAnticorrosion(EquipAnticorrosion equipAnticorrosion)
    {
        equipAnticorrosion.setUpdateTime(DateUtils.getNowDate());
        return equipAnticorrosionMapper.updateEquipAnticorrosion(equipAnticorrosion);
    }

    /**
     * 批量删除防腐蚀检测
     * 
     * @param ids 需要删除的防腐蚀检测主键
     * @return 结果
     */
    @Override
    public int deleteEquipAnticorrosionByIds(Long[] ids)
    {
        return equipAnticorrosionMapper.deleteEquipAnticorrosionByIds(ids);
    }

    /**
     * 删除防腐蚀检测信息
     * 
     * @param id 防腐蚀检测主键
     * @return 结果
     */
    @Override
    public int deleteEquipAnticorrosionById(Long id)
    {
        return equipAnticorrosionMapper.deleteEquipAnticorrosionById(id);
    }
}
