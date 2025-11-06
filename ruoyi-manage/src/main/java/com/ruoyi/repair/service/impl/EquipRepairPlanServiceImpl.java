package com.ruoyi.repair.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.service.IEquipLedgerService;
import com.ruoyi.repair.domain.EquipRepairPlan;
import com.ruoyi.repair.mapper.EquipRepairPlanMapper;
import com.ruoyi.repair.service.IEquipRepairPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 设备检修计划Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipRepairPlanServiceImpl implements IEquipRepairPlanService {
    @Autowired
    private EquipRepairPlanMapper equipRepairPlanMapper;

    @Autowired
    private IEquipLedgerService equipLedgerService;

    /**
     * 查询设备检修计划
     *
     * @param planId 设备检修计划主键
     * @return 设备检修计划
     */
    @Override
    public EquipRepairPlan selectEquipRepairPlanByPlanId(Long planId) {
        return equipRepairPlanMapper.selectEquipRepairPlanByPlanId(planId);
    }

    /**
     * 查询设备检修计划列表
     *
     * @param equipRepairPlan 设备检修计划
     * @return 设备检修计划
     */
    @Override
    public List<EquipRepairPlan> selectEquipRepairPlanList(EquipRepairPlan equipRepairPlan) {
        return equipRepairPlanMapper.selectEquipRepairPlanList(equipRepairPlan);
    }

    /**
     * 新增设备检修计划
     *
     * @param equipRepairPlan 设备检修计划
     * @return 结果
     */
    @Override
    public int insertEquipRepairPlan(EquipRepairPlan equipRepairPlan) {
        //生成计划编号
        /*
         // 生成计划编号格式：类型代码+年份+月份+三位序号
      // 例如：NDJH202510001（NDJH表示年度计划，2025表示年份，10表示月份，001表示序号）
      const planTypeMap = {
        '1': 'NDJH',  // 年度计划
        '2': 'YDJH',  // 月度计划
        '3': 'ZBJH'   // 周边计划
      }

      const typeCode = planTypeMap[this.form.planType] || 'JH'
      const year = this.form.planYear || new Date().getFullYear()
      const month = this.form.planMonth || (new Date().getMonth() + 1).toString().padStart(2, '0')

      // 使用时间戳作为序号的一部分，确保唯一性
      const timestamp = Date.now().toString().slice(-5)

      this.form.planNo = `${typeCode}${year}${month}${timestamp}`
         */
        HashMap<String, String> map = new HashMap<>();
        map.put("1", "NDJH");
        map.put("2", "YDJH");
        map.put("3", "ZDJH");
        String typeCode = map.get(equipRepairPlan.getPlanType());
        if (StringUtils.isEmpty(typeCode)) {
            typeCode = "JH";
        }
        String year = equipRepairPlan.getPlanYear();
        if (StringUtils.isEmpty(year)) {
            year = DateUtils.dateTimeNow(DateUtils.YYYY);
        }
        String month = equipRepairPlan.getPlanMonth();
        if (StringUtils.isEmpty(month)) {
            //获取当前月
            LocalDate localDate = LocalDate.now();
            int monthValue = localDate.getMonthValue();
            month = String.format("%02d", monthValue);
        }
        long currentTimeMillis = System.currentTimeMillis();
        String timestamp = String.valueOf(currentTimeMillis).substring(0, 5);
        String planNo = typeCode + year + month + timestamp;
        equipRepairPlan.setPlanNo(planNo);

        //如果有设备
        if (StringUtils.isNotEmpty(equipRepairPlan.getEquipId())) {
            EquipLedger equipLedger = equipLedgerService.selectEquipLedgerByEquipId(Long.parseLong(equipRepairPlan.getEquipId()));
            if (StringUtils.isNotNull(equipLedger)) {
                equipRepairPlan.setEquipName(equipLedger.getEquName());
                equipRepairPlan.setEquipCode(equipLedger.getEquCode());
                equipRepairPlan.setEquipLevels(equipLedger.getEquipLevels());
            }
        }
        equipRepairPlan.setCreateTime(DateUtils.getNowDate());
        return equipRepairPlanMapper.insertEquipRepairPlan(equipRepairPlan);
    }

    /**
     * 修改设备检修计划
     *
     * @param equipRepairPlan 设备检修计划
     * @return 结果
     */
    @Override
    public int updateEquipRepairPlan(EquipRepairPlan equipRepairPlan) {
        equipRepairPlan.setUpdateTime(DateUtils.getNowDate());
        return equipRepairPlanMapper.updateEquipRepairPlan(equipRepairPlan);
    }

    /**
     * 批量删除设备检修计划
     *
     * @param planIds 需要删除的设备检修计划主键
     * @return 结果
     */
    @Override
    public int deleteEquipRepairPlanByPlanIds(Long[] planIds) {
        return equipRepairPlanMapper.deleteEquipRepairPlanByPlanIds(planIds);
    }

    /**
     * 删除设备检修计划信息
     *
     * @param planId 设备检修计划主键
     * @return 结果
     */
    @Override
    public int deleteEquipRepairPlanByPlanId(Long planId) {
        return equipRepairPlanMapper.deleteEquipRepairPlanByPlanId(planId);
    }
}
