package com.ruoyi.inspe.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.inspe.mapper.EquipInspeMapper;
import com.ruoyi.inspe.domain.EquipInspe;
import com.ruoyi.inspe.service.IEquipInspeService;

import javax.annotation.Resource;

/**
 * 设备检查记录Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipInspeServiceImpl implements IEquipInspeService
{
    @Autowired
    private EquipInspeMapper equipInspeMapper;

    /**
     * 查询设备检查记录
     *
     * @param inspeId 设备检查记录主键
     * @return 设备检查记录
     */
    @Override
    public EquipInspe selectEquipInspeByInspeId(Long inspeId)
    {
        return equipInspeMapper.selectEquipInspeByInspeId(inspeId);
    }

    /**
     * 查询设备检查记录列表
     *
     * @param equipInspe 设备检查记录
     * @return 设备检查记录
     */
    @Override
    public List<EquipInspe> selectEquipInspeList(EquipInspe equipInspe)
    {
        return equipInspeMapper.selectEquipInspeList(equipInspe);
    }

    /**
     * 新增设备检查记录
     *
     * @param equipInspe 设备检查记录
     * @return 结果
     */
    @Override
    public int insertEquipInspe(EquipInspe equipInspe)
    {
        // 自动生成检查单号：JC+年月日+次数
        if (equipInspe.getInspeNo() == null || equipInspe.getInspeNo().isEmpty()) {
            // 获取当前日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String currentDate = sdf.format(new Date());

            // 构造查询条件，查找当天已有的记录数
            EquipInspe queryParam = new EquipInspe();
            queryParam.setInspeNo("JC" + currentDate);
            List<EquipInspe> existingRecords = equipInspeMapper.selectEquipInspeList(queryParam);

            // 计算当天的序号
            int count = existingRecords.size() + 1;

            // 生成检查单号，格式为 JC+年月日+序号(两位数)
            String inspeNo = "JC" + currentDate + String.format("%02d", count);
            equipInspe.setInspeNo(inspeNo);
        }
        equipInspe.setCreateTime(DateUtils.getNowDate());
        return equipInspeMapper.insertEquipInspe(equipInspe);
    }

    /**
     * 修改设备检查记录
     *
     * @param equipInspe 设备检查记录
     * @return 结果
     */
    @Override
    public int updateEquipInspe(EquipInspe equipInspe)
    {
        equipInspe.setUpdateTime(DateUtils.getNowDate());
        return equipInspeMapper.updateEquipInspe(equipInspe);
    }

    /**
     * 批量删除设备检查记录
     *
     * @param inspeIds 需要删除的设备检查记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipInspeByInspeIds(Long[] inspeIds)
    {
        return equipInspeMapper.deleteEquipInspeByInspeIds(inspeIds);
    }

    /**
     * 删除设备检查记录信息
     *
     * @param inspeId 设备检查记录主键
     * @return 结果
     */
    @Override
    public int deleteEquipInspeByInspeId(Long inspeId)
    {
        return equipInspeMapper.deleteEquipInspeByInspeId(inspeId);
    }
}
