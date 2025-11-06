package com.ruoyi.equip.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipRuntimeDatail;
import com.ruoyi.equip.domain.EquipRuntimeStat;
import com.ruoyi.equip.mapper.EquipRuntimeStatMapper;
import com.ruoyi.equip.service.IEquipRuntimeStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备运行管理Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipRuntimeStatServiceImpl implements IEquipRuntimeStatService {
    @Autowired
    private EquipRuntimeStatMapper equipRuntimeStatMapper;

    /**
     * 查询设备运行管理
     *
     * @param statId 设备运行管理主键
     * @return 设备运行管理
     */
    @Override
    public EquipRuntimeStat selectEquipRuntimeStatByStatId(Long statId) {
        EquipRuntimeStat equipRuntimeStat = equipRuntimeStatMapper.selectEquipRuntimeStatByStatId(statId);

        // 实时计算运行时长：对于无停机时间但有开机时间的记录，计算从开机到当前时间的运行时长
        if (equipRuntimeStat != null) {
            // 如果有开机时间但无停机时间，则实时计算运行时长
            if (equipRuntimeStat.getStartTime() != null && equipRuntimeStat.getStopTime() == null) {
                long hoursBetween = DateUtils.getHoursBetween(equipRuntimeStat.getStartTime(), DateUtils.getNowDate());
                equipRuntimeStat.setTotalRuntime(hoursBetween);
            }
        }

        return equipRuntimeStat;
    }

    /**
     * 查询设备运行管理列表
     *
     * @param equipRuntimeStat 设备运行管理
     * @return 设备运行管理
     */
    @Override
    public List<EquipRuntimeStat> selectEquipRuntimeStatList(EquipRuntimeStat equipRuntimeStat) {
        List<EquipRuntimeStat> list = equipRuntimeStatMapper.selectEquipRuntimeStatList(equipRuntimeStat);

        // 实时计算运行时长：对于无停机时间但有开机时间的记录，计算从开机到当前时间的运行时长
        for (EquipRuntimeStat stat : list) {
            // 如果有开机时间但无停机时间，则实时计算运行时长
            if (stat.getStartTime() != null && stat.getStopTime() == null) {
                long diffInMillies = System.currentTimeMillis() - stat.getStartTime().getTime();
                long diffInHours = diffInMillies / (60 * 60 * 1000);
                stat.setTotalRuntime(diffInHours);
            }
        }

        return list;
    }

    /**
     * 新增设备运行管理
     *
     * @param equipRuntimeStat 设备运行管理
     * @return 结果
     */
    @Transactional
    @Override
    public int insertEquipRuntimeStat(EquipRuntimeStat equipRuntimeStat) {
        // 自动设置状态：有停机时间则为"已停机"，无停机时间则为"运行中"
        if (equipRuntimeStat.getStopTime() != null) {
            // 有停机时间，设置状态为"已停机"
            equipRuntimeStat.setStatus("1"); // 假设"1"代表"已停机"
        } else {
            // 无停机时间，设置状态为"运行中"
            equipRuntimeStat.setStatus("0"); // 假设"0"代表"运行中"
        }

        // 验证日期格式和空值检查
        if (equipRuntimeStat.getStopTime() != null && equipRuntimeStat.getStartTime() == null) {
            // 停机记录验证：确保设备ID不为空
            if (equipRuntimeStat.getDeviceId() == null) {
                throw new IllegalArgumentException("设备ID不能为空");
            }

            EquipRuntimeStat latestStartRecord = equipRuntimeStatMapper.selectLatestStartRecordByDeviceId(equipRuntimeStat.getDeviceId());
            if (latestStartRecord != null) {
                // 更新最近的开机记录，添加停机信息
                latestStartRecord.setStopTime(equipRuntimeStat.getStopTime());
                latestStartRecord.setStopRecordId(equipRuntimeStat.getStopRecordId());
                latestStartRecord.setShutdownReason(equipRuntimeStat.getShutdownReason());
                latestStartRecord.setRemark(equipRuntimeStat.getRemark());
                // 更新状态
                latestStartRecord.setStatus("1"); // 已停机

                // 计算运行时长（小时）
                if (latestStartRecord.getStopTime() != null && latestStartRecord.getStartTime() != null) {
                    long diffInMillies = latestStartRecord.getStopTime().getTime() - latestStartRecord.getStartTime().getTime();
                    long diffInHours = diffInMillies / (60 * 60 * 1000);
                    latestStartRecord.setTotalRuntime(diffInHours);
                }

                // 更新记录
                return updateEquipRuntimeStat(latestStartRecord);
            } else {
                // 如果没有找到开机记录，则按正常流程插入新记录
                if (StringUtils.isEmpty(equipRuntimeStat.getStatNo())) {
                    String statNo = "STAT" + System.currentTimeMillis();
                    equipRuntimeStat.setStatNo(statNo);
                }
                equipRuntimeStat.setCreateTime(DateUtils.getNowDate());
                int rows = equipRuntimeStatMapper.insertEquipRuntimeStat(equipRuntimeStat);
                insertEquipRuntimeDatail(equipRuntimeStat);
                return rows;
            }
        } else {
            // 开机记录验证：确保必要字段不为空
            if (equipRuntimeStat.getDeviceId() == null) {
                throw new IllegalArgumentException("设备ID不能为空");
            }

            if (equipRuntimeStat.getStartTime() == null) {
                throw new IllegalArgumentException("开机时间不能为空");
            }

            // 如果不是停机记录（即开机记录），则按正常流程插入新记录
            if (StringUtils.isEmpty(equipRuntimeStat.getStatNo())) {
                String statNo = "STAT" + System.currentTimeMillis();
                equipRuntimeStat.setStatNo(statNo);
            }
            equipRuntimeStat.setCreateTime(DateUtils.getNowDate());
            int rows = equipRuntimeStatMapper.insertEquipRuntimeStat(equipRuntimeStat);
            insertEquipRuntimeDatail(equipRuntimeStat);
            return rows;
        }
    }

    /**
     * 修改设备运行管理
     *
     * @param equipRuntimeStat 设备运行管理
     * @return 结果
     */
    @Transactional
    @Override
    public int updateEquipRuntimeStat(EquipRuntimeStat equipRuntimeStat) {
        // 自动设置状态和计算运行时长
        if (equipRuntimeStat.getStopTime() != null) {
            // 有停机时间，设置状态为"已停机"
            equipRuntimeStat.setStatus("1"); // 已停机
            // 计算运行时长（小时）
            if (equipRuntimeStat.getStopTime() != null && equipRuntimeStat.getStartTime() != null) {
                long diffInMillies = equipRuntimeStat.getStopTime().getTime() - equipRuntimeStat.getStartTime().getTime();
                long diffInHours = diffInMillies / (60 * 60 * 1000);
                equipRuntimeStat.setTotalRuntime(diffInHours);
            }
        } else {
            // 无停机时间，设置状态为"运行中"
            equipRuntimeStat.setStatus("0"); // 运行中
            // 如果有开机时间但无停机时间，计算从开机时间到当前时间的运行时长
            if (equipRuntimeStat.getStartTime() != null) {
                long diffInMillies = System.currentTimeMillis() - equipRuntimeStat.getStartTime().getTime();
                long diffInHours = diffInMillies / (60 * 60 * 1000);
                equipRuntimeStat.setTotalRuntime(diffInHours);
            }
        }

        equipRuntimeStat.setUpdateTime(DateUtils.getNowDate());
        equipRuntimeStatMapper.deleteEquipRuntimeDatailByStatId(equipRuntimeStat.getStatId());
        insertEquipRuntimeDatail(equipRuntimeStat);
        return equipRuntimeStatMapper.updateEquipRuntimeStat(equipRuntimeStat);
    }

    /**
     * 批量删除设备运行管理
     *
     * @param statIds 需要删除的设备运行管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipRuntimeStatByStatIds(Long[] statIds) {
        equipRuntimeStatMapper.deleteEquipRuntimeDatailByStatIds(statIds);
        return equipRuntimeStatMapper.deleteEquipRuntimeStatByStatIds(statIds);
    }

    /**
     * 删除设备运行管理信息
     *
     * @param statId 设备运行管理主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipRuntimeStatByStatId(Long statId) {
        equipRuntimeStatMapper.deleteEquipRuntimeDatailByStatId(statId);
        return equipRuntimeStatMapper.deleteEquipRuntimeStatByStatId(statId);
    }

    /**
     * 新增设备运行明细信息
     *
     * @param equipRuntimeStat 设备运行管理对象
     */
    public void insertEquipRuntimeDatail(EquipRuntimeStat equipRuntimeStat) {
        List<EquipRuntimeDatail> equipRuntimeDatailList = equipRuntimeStat.getEquipRuntimeDatailList();
        Long statId = equipRuntimeStat.getStatId();
        if (StringUtils.isNotNull(equipRuntimeDatailList)) {
            List<EquipRuntimeDatail> list = new ArrayList<EquipRuntimeDatail>();
            for (EquipRuntimeDatail equipRuntimeDatail : equipRuntimeDatailList) {
                equipRuntimeDatail.setStatId(String.valueOf(statId));

                list.add(equipRuntimeDatail);
            }
            if (list.size() > 0) {
                equipRuntimeStatMapper.batchEquipRuntimeDatail(list);
            }
        }
    }
}
