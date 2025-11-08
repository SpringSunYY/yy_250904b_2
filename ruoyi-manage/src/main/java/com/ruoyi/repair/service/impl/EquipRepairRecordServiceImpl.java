package com.ruoyi.repair.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.mapper.EquipLedgerMapper;
import com.ruoyi.repair.domain.EquipRepairDetail;
import com.ruoyi.repair.domain.EquipRepairPlan;
import com.ruoyi.repair.domain.EquipRepairRecord;
import com.ruoyi.repair.mapper.EquipRepairPlanMapper;
import com.ruoyi.repair.mapper.EquipRepairRecordMapper;
import com.ruoyi.repair.service.IEquipRepairRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 检维修记录Service业务层处理
 *
 * @author laogao
 * @date 2025-09-30
 */
@Service
public class EquipRepairRecordServiceImpl implements IEquipRepairRecordService {
    @Autowired
    private EquipRepairRecordMapper equipRepairRecordMapper;

    @Autowired
    private EquipLedgerMapper equipLedgerMapper;

    @Autowired
    private EquipRepairPlanMapper equipRepairPlanMapper;

    /**
     * 查询检维修记录
     *
     * @param recordId 检维修记录主键
     * @return 检维修记录
     */
    @Override
    public EquipRepairRecord selectEquipRepairRecordByRecordId(Long recordId) {
        return equipRepairRecordMapper.selectEquipRepairRecordByRecordId(recordId);
    }

    /**
     * 查询检维修记录列表
     *
     * @param equipRepairRecord 检维修记录
     * @return 检维修记录
     */
    @Override
    public List<EquipRepairRecord> selectEquipRepairRecordList(EquipRepairRecord equipRepairRecord) {
        return equipRepairRecordMapper.selectEquipRepairRecordList(equipRepairRecord);
    }

    /**
     * 新增检维修记录
     *
     * @param equipRepairRecord 检维修记录
     * @return 结果
     */
    @Transactional
    @Override
    public int insertEquipRepairRecord(EquipRepairRecord equipRepairRecord) {
        // 自动生成维修单号
        if (StringUtils.isEmpty(equipRepairRecord.getRecordNo())) {
            String recordNo = "WX" + DateUtils.dateTimeNow("yyyyMMddHHmmss") + (int) (Math.random() * 9000 + 1000);
            equipRepairRecord.setRecordNo(recordNo);
        }
        initDate(equipRepairRecord);
        equipRepairRecord.setCreateTime(DateUtils.getNowDate());
        int rows = equipRepairRecordMapper.insertEquipRepairRecord(equipRepairRecord);
        insertEquipRepairDetail(equipRepairRecord);
        return rows;
    }

    private void initDate(EquipRepairRecord equipRepairRecord) {
        //如果有计划
        if (StringUtils.isNotNull(equipRepairRecord.getPlanId())) {
            EquipRepairPlan equipRepairPlan = equipRepairPlanMapper.selectEquipRepairPlanByPlanId(equipRepairRecord.getPlanId());
            if (StringUtils.isNotNull(equipRepairPlan)) {
                equipRepairRecord.setPlanNo(equipRepairPlan.getPlanNo());
                if (StringUtils.isNotEmpty(equipRepairPlan.getEquipId())) {
                    equipRepairRecord.setEquipId(equipRepairPlan.getEquipId());
                }
            }
        }
        //如果有设备
        if (StringUtils.isNotEmpty(equipRepairRecord.getEquipId())) {
            EquipLedger equipLedger = equipLedgerMapper.selectEquipLedgerByEquipId(Long.parseLong(equipRepairRecord.getEquipId()));
            if (StringUtils.isNotNull(equipLedger)) {
                equipRepairRecord.setEquipName(equipLedger.getEquName());
                equipRepairRecord.setEquipCode(equipLedger.getEquCode());
                equipRepairRecord.setEquipLevels(equipLedger.getEquipLevels());
            }
        }
    }

    /**
     * 修改检维修记录
     *
     * @param equipRepairRecord 检维修记录
     * @return 结果
     */
    @Transactional
    @Override
    public int updateEquipRepairRecord(EquipRepairRecord equipRepairRecord) {
        initDate(equipRepairRecord);
        equipRepairRecord.setUpdateTime(DateUtils.getNowDate());
        equipRepairRecordMapper.deleteEquipRepairDetailByRecordId(equipRepairRecord.getRecordId());
        insertEquipRepairDetail(equipRepairRecord);
        return equipRepairRecordMapper.updateEquipRepairRecord(equipRepairRecord);
    }

    /**
     * 批量删除检维修记录
     *
     * @param recordIds 需要删除的检维修记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipRepairRecordByRecordIds(Long[] recordIds) {
        equipRepairRecordMapper.deleteEquipRepairDetailByRecordIds(recordIds);
        return equipRepairRecordMapper.deleteEquipRepairRecordByRecordIds(recordIds);
    }

    /**
     * 删除检维修记录信息
     *
     * @param recordId 检维修记录主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipRepairRecordByRecordId(Long recordId) {
        equipRepairRecordMapper.deleteEquipRepairDetailByRecordId(recordId);
        return equipRepairRecordMapper.deleteEquipRepairRecordByRecordId(recordId);
    }

    /**
     * 新增设备维修明细信息
     *
     * @param equipRepairRecord 检维修记录对象
     */
    public void insertEquipRepairDetail(EquipRepairRecord equipRepairRecord) {
        List<EquipRepairDetail> equipRepairDetailList = equipRepairRecord.getEquipRepairDetailList();
        Long recordId = equipRepairRecord.getRecordId();
        if (StringUtils.isNotNull(equipRepairDetailList)) {
            List<EquipRepairDetail> list = new ArrayList<EquipRepairDetail>();
            for (EquipRepairDetail equipRepairDetail : equipRepairDetailList) {
                equipRepairDetail.setRecordId(recordId);
                list.add(equipRepairDetail);
            }
            if (list.size() > 0) {
                equipRepairRecordMapper.batchEquipRepairDetail(list);
            }
        }
    }
}
