package com.ruoyi.equip.service;

import java.util.List;
import com.ruoyi.equip.domain.EquipDevice;

/**
 * 设备安装验收Service接口
 * 
 * @author laogao
 * @date 2025-09-23
 */
public interface IEquipDeviceService 
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
     * 批量删除设备安装验收
     * 
     * @param deviceIds 需要删除的设备安装验收主键集合
     * @return 结果
     */
    public int deleteEquipDeviceByDeviceIds(Long[] deviceIds);

    /**
     * 删除设备安装验收信息
     * 
     * @param deviceId 设备安装验收主键
     * @return 结果
     */
    public int deleteEquipDeviceByDeviceId(Long deviceId);
}
