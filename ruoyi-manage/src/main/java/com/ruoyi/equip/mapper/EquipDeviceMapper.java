package com.ruoyi.equip.mapper;

import java.util.List;
import com.ruoyi.equip.domain.EquipDevice;
import com.ruoyi.equip.domain.EquipDeviceItem;

/**
 * 设备安装验收Mapper接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface EquipDeviceMapper 
{
    /**
     * 查询设备安装验收
     * 
     * @param deviceId 设备安装验收主键
     * @return 设备安装验收
     */
    public EquipDevice selectEquipDeviceByDeviceId(Long deviceId);

    /**
     * 查询设备安装验收列表
     * 
     * @param equipDevice 设备安装验收
     * @return 设备安装验收集合
     */
    public List<EquipDevice> selectEquipDeviceList(EquipDevice equipDevice);

    /**
     * 新增设备安装验收
     * 
     * @param equipDevice 设备安装验收
     * @return 结果
     */
    public int insertEquipDevice(EquipDevice equipDevice);

    /**
     * 修改设备安装验收
     * 
     * @param equipDevice 设备安装验收
     * @return 结果
     */
    public int updateEquipDevice(EquipDevice equipDevice);

    /**
     * 删除设备安装验收
     * 
     * @param deviceId 设备安装验收主键
     * @return 结果
     */
    public int deleteEquipDeviceByDeviceId(Long deviceId);

    /**
     * 批量删除设备安装验收
     * 
     * @param deviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipDeviceByDeviceIds(Long[] deviceIds);

    /**
     * 批量删除安装验收明细
     * 
     * @param deviceIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEquipDeviceItemByDeviceIds(Long[] deviceIds);
    
    /**
     * 批量新增安装验收明细
     * 
     * @param equipDeviceItemList 安装验收明细列表
     * @return 结果
     */
    public int batchEquipDeviceItem(List<EquipDeviceItem> equipDeviceItemList);
    

    /**
     * 通过设备安装验收主键删除安装验收明细信息
     * 
     * @param deviceId 设备安装验收ID
     * @return 结果
     */
    public int deleteEquipDeviceItemByDeviceId(Long deviceId);
}
