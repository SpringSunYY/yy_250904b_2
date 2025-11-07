package com.ruoyi.equip.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.equip.domain.EquipDevice;
import com.ruoyi.equip.domain.EquipDeviceItem;
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.mapper.EquipDeviceMapper;
import com.ruoyi.equip.service.IEquipDeviceService;
import com.ruoyi.pur.domain.EquipPurchaseOrder;
import com.ruoyi.pur.mapper.EquipPurchaseOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 设备安装验收Service业务层处理
 *
 * @author laogao
 * @date 2025-09-23
 */
@Service
public class EquipDeviceServiceImpl implements IEquipDeviceService {
    @Autowired
    private EquipDeviceMapper equipDeviceMapper;

    @Resource
    private EquipPurchaseOrderMapper equipPurchaseOrderMapper;

    // 使用原子Long保证线程安全
    private static final AtomicLong sequence = new AtomicLong(1);

    /**
     * 查询设备安装验收
     *
     * @param deviceId 设备安装验收主键
     * @return 设备安装验收
     */
    @Override
    public EquipDevice selectEquipDeviceByDeviceId(Long deviceId) {
        return equipDeviceMapper.selectEquipDeviceByDeviceId(deviceId);
    }

    /**
     * 查询设备安装验收列表
     *
     * @param equipDevice 设备安装验收
     * @return 设备安装验收
     */
    @Override
    public List<EquipDevice> selectEquipDeviceList(EquipDevice equipDevice) {
        return equipDeviceMapper.selectEquipDeviceList(equipDevice);
    }

    /**
     * 新增设备安装验收
     *
     * @param equipDevice 设备安装验收
     * @return 结果
     */
    @Transactional
    @Override
    public int insertEquipDevice(EquipDevice equipDevice) {
        // 自动生成验收单号：前缀+日期+序号
        if (equipDevice.getDeviceNo() == null) {
            String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
            String prefix = "YS";
            // 使用原子操作保证序号唯一性
            long seq = sequence.getAndIncrement() % 10000;
            String deviceNo = prefix + dateStr + String.format("%04d", seq);
            // 直接设置字符串类型的设备单号，避免类型转换
            equipDevice.setDeviceNo(deviceNo);

        }
        initDate(equipDevice);
        equipDevice.setCreateTime(DateUtils.getNowDate());
        int rows = equipDeviceMapper.insertEquipDevice(equipDevice);
        insertEquipDeviceItem(equipDevice);
        return rows;
    }

    private void initDate(EquipDevice equipDevice) {
        //如果传入设备编号
        if (StringUtils.isNotNull(equipDevice.getOrderId())) {
            EquipPurchaseOrder equipPurchaseOrder = equipPurchaseOrderMapper.selectEquipPurchaseOrderByOrderId(equipDevice.getOrderId());
            if (StringUtils.isNotNull(equipPurchaseOrder)) {
                equipDevice.setEquipName(equipPurchaseOrder.getEquipName());
                equipDevice.setOrderNo(equipPurchaseOrder.getOrderNo());
            }
        }
    }

    /**
     * 修改设备安装验收
     *
     * @param equipDevice 设备安装验收
     * @return 结果
     */
    @Transactional
    @Override
    public int updateEquipDevice(EquipDevice equipDevice) {
        initDate(equipDevice);
        equipDeviceMapper.deleteEquipDeviceItemByDeviceId(equipDevice.getDeviceId());
        insertEquipDeviceItem(equipDevice);
        return equipDeviceMapper.updateEquipDevice(equipDevice);
    }

    /**
     * 批量删除设备安装验收
     *
     * @param deviceIds 需要删除的设备安装验收主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipDeviceByDeviceIds(Long[] deviceIds) {
        equipDeviceMapper.deleteEquipDeviceItemByDeviceIds(deviceIds);
        return equipDeviceMapper.deleteEquipDeviceByDeviceIds(deviceIds);
    }

    /**
     * 删除设备安装验收信息
     *
     * @param deviceId 设备安装验收主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteEquipDeviceByDeviceId(Long deviceId) {
        equipDeviceMapper.deleteEquipDeviceItemByDeviceId(deviceId);
        return equipDeviceMapper.deleteEquipDeviceByDeviceId(deviceId);
    }

    /**
     * 新增安装验收明细信息
     *
     * @param equipDevice 设备安装验收对象
     */
    public void insertEquipDeviceItem(EquipDevice equipDevice) {
        List<EquipDeviceItem> equipDeviceItemList = equipDevice.getEquipDeviceItemList();
        Long deviceId = equipDevice.getDeviceId();
        if (StringUtils.isNotNull(equipDeviceItemList)) {
            List<EquipDeviceItem> list = new ArrayList<EquipDeviceItem>();
            for (EquipDeviceItem equipDeviceItem : equipDeviceItemList) {
                equipDeviceItem.setDeviceId(deviceId);
                list.add(equipDeviceItem);
            }
            if (list.size() > 0) {
                equipDeviceMapper.batchEquipDeviceItem(list);
            }
        }
    }
}
