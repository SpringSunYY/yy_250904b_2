package com.ruoyi.inspe.service.impl;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.inspe.domain.EquipInspe;
import com.ruoyi.inspe.domain.EquipInspeDanger;
import com.ruoyi.inspe.mapper.EquipInspeDangerMapper;
import com.ruoyi.inspe.mapper.EquipInspeMapper;
import com.ruoyi.inspe.service.IEquipInspeDangerService;
import com.ruoyi.system.service.ISysDeptService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

/**
 * 设备隐患治理Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipInspeDangerServiceImpl implements IEquipInspeDangerService {
    @Autowired
    private EquipInspeDangerMapper equipInspeDangerMapper;

    @Autowired
    private EquipInspeMapper equipInspeMapper;

    @Autowired
    private EquipLedgerMapper equipLedgerMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询设备隐患治理
     *
     * @param dangerId 设备隐患治理主键
     * @return 设备隐患治理
     */
    @Override
    public EquipInspeDanger selectEquipInspeDangerByDangerId(Long dangerId) {
        return equipInspeDangerMapper.selectEquipInspeDangerByDangerId(dangerId);
    }

    /**
     * 查询设备隐患治理列表
     *
     * @param equipInspeDanger 设备隐患治理
     * @return 设备隐患治理集合
     */
    @Override
    public List<EquipInspeDanger> selectEquipInspeDangerList(EquipInspeDanger equipInspeDanger) {
        List<EquipInspeDanger> equipInspeDangers = equipInspeDangerMapper.selectEquipInspeDangerList(equipInspeDanger);
        for (EquipInspeDanger info : equipInspeDangers) {
            SysUser awardUser = sysUserService.selectUserById(info.getAwardUserId());
            if (StringUtils.isNotNull(awardUser)) {
                info.setAwardUserName(awardUser.getUserName());
            }
            SysUser punishUser = sysUserService.selectUserById(info.getPunishUserId());
            if (StringUtils.isNotNull(punishUser)) {
                info.setPunishUserName(punishUser.getUserName());
            }
            SysDept awardDept = sysDeptService.selectDeptById(info.getAwardDeptId());
            if (StringUtils.isNotNull(awardDept)) {
                info.setAwardDeptName(awardDept.getDeptName());
            }
            SysDept punishDept = sysDeptService.selectDeptById(info.getPunishDeptId());
            if (StringUtils.isNotNull(punishDept)) {
                info.setPunishDeptName(punishDept.getDeptName());
            }
        }
        return equipInspeDangers;
    }

    /**
     * 新增设备隐患治理
     *
     * @param equipInspeDanger 设备隐患治理
     * @return 结果
     */
    @Override
    public int insertEquipInspeDanger(EquipInspeDanger equipInspeDanger) {
        // 自动生成隐患编号：YH+年份+三位序号
        if (equipInspeDanger.getDangerNo() == null || equipInspeDanger.getDangerNo().isEmpty()) {
            // 获取当前年份
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            // 构造查询条件，查找当年已有的记录
            EquipInspeDanger queryParam = new EquipInspeDanger();
            // 使用params传递额外的查询条件
            queryParam.setParams(new HashMap<>());
            queryParam.getParams().put("dangerNoPrefix", "YH" + year);

            List<EquipInspeDanger> existingRecords = equipInspeDangerMapper.selectEquipInspeDangerList(queryParam);

            // 将当年已有的序号放入TreeSet中，自动排序
            TreeSet<Integer> existingNumbers = new TreeSet<>();
            for (EquipInspeDanger record : existingRecords) {
                String dangerNo = record.getDangerNo();
                if (dangerNo != null && dangerNo.startsWith("YH" + year)) {
                    try {
                        // 提取序号部分（后三位）
                        String sequenceStr = dangerNo.substring(6); // "YH"+年份(4位)+序号(3位) = 9位，取后3位
                        int sequence = Integer.parseInt(sequenceStr);
                        existingNumbers.add(sequence);
                    } catch (Exception e) {
                        // 忽略解析错误
                    }
                }
            }

            // 查找最小的可用序号
            int newCount = 1; // 默认从1开始
            for (int number : existingNumbers) {
                if (number == newCount) {
                    newCount++; // 如果当前序号已被使用，则递增
                } else if (number > newCount) {
                    // 找到了一个空缺的序号
                    break;
                }
            }

            // 生成隐患编号，格式为 YH+年份+序号(三位数)
            String dangerNo = "YH" + year + String.format("%03d", newCount);
            equipInspeDanger.setDangerNo(dangerNo);
        }
        initData(equipInspeDanger);
        equipInspeDanger.setCreateTime(DateUtils.getNowDate());
        return equipInspeDangerMapper.insertEquipInspeDanger(equipInspeDanger);
    }

    private void initData(EquipInspeDanger equipInspeDanger) {
        //如果传入inspeID，
        if (StringUtils.isNotEmpty(equipInspeDanger.getInspeId())) {
            EquipInspe equipInspe = equipInspeMapper.selectEquipInspeByInspeId(Long.parseLong(equipInspeDanger.getInspeId()));
            if (StringUtils.isNotNull(equipInspe) && StringUtils.isNotEmpty(equipInspe.getInspeNo())) {
                equipInspeDanger.setInspeNo(equipInspe.getInspeNo());
            }
        }
        //如果传入设备信息
        if (StringUtils.isNotEmpty(equipInspeDanger.getEquipId())) {
            EquipLedger ledger = equipLedgerMapper.selectEquipLedgerByEquipId(Long.parseLong(equipInspeDanger.getEquipId()));
            if (StringUtils.isNotNull(ledger)) {
                if (StringUtils.isNotEmpty(ledger.getEquName())) {
                    equipInspeDanger.setEquipName(ledger.getEquName());
                }
                if (StringUtils.isNotEmpty(ledger.getEquCode())) {
                    equipInspeDanger.setEquipCode(ledger.getEquCode());
                }
                equipInspeDanger.setEquipCode(equipInspeDanger.getEquipCode());
            }
        }
        //如果传入奖励用户
        if (StringUtils.isNotNull(equipInspeDanger.getAwardUserId())) {
            SysUser sysUser = sysUserService.selectUserById(equipInspeDanger.getAwardUserId());
            if (StringUtils.isNotNull(sysUser)) {
                equipInspeDanger.setAwardDeptId(sysUser.getDeptId());
            }
        }
        //如果传入处罚用户
        if (StringUtils.isNotNull(equipInspeDanger.getPunishUserId())) {
            SysUser sysUser = sysUserService.selectUserById(equipInspeDanger.getPunishUserId());
            if (StringUtils.isNotNull(sysUser)) {
                equipInspeDanger.setPunishDeptId(sysUser.getDeptId());
            }
        }
        //如果传入责任人id
        if (StringUtils.isNotNull(equipInspeDanger.getResponId())) {
            SysUser sysUser = sysUserService.selectUserById(equipInspeDanger.getResponId());
            if (StringUtils.isNotNull(sysUser)) {
                equipInspeDanger.setResponName(sysUser.getUserName());
            }
        }
    }

    /**
     * 修改设备隐患治理
     *
     * @param equipInspeDanger 设备隐患治理
     * @return 结果
     */
    @Override
    public int updateEquipInspeDanger(EquipInspeDanger equipInspeDanger) {
        initData(equipInspeDanger);
        equipInspeDanger.setUpdateTime(DateUtils.getNowDate());
        return equipInspeDangerMapper.updateEquipInspeDanger(equipInspeDanger);
    }

    /**
     * 批量删除设备隐患治理
     *
     * @param dangerIds 需要删除的设备隐患治理主键
     * @return 结果
     */
    @Override
    public int deleteEquipInspeDangerByDangerIds(Long[] dangerIds) {
        return equipInspeDangerMapper.deleteEquipInspeDangerByDangerIds(dangerIds);
    }

    /**
     * 删除设备隐患治理信息
     *
     * @param dangerId 设备隐患治理主键
     * @return 结果
     */
    @Override
    public int deleteEquipInspeDangerByDangerId(Long dangerId) {
        return equipInspeDangerMapper.deleteEquipInspeDangerByDangerId(dangerId);
    }
}
