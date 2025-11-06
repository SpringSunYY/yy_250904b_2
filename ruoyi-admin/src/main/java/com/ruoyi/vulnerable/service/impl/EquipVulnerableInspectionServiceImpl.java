package com.ruoyi.vulnerable.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.vulnerable.mapper.EquipVulnerableInspectionMapper;
import com.ruoyi.vulnerable.domain.EquipVulnerableInspection;
import com.ruoyi.vulnerable.service.IEquipVulnerableInspectionService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.common.core.domain.entity.SysUser;

/**
 * 易损设备检查Service业务层处理
 *
 * @author Laogao
 * @date 2025-11-05
 */
@Service
public class EquipVulnerableInspectionServiceImpl implements IEquipVulnerableInspectionService
{
    @Autowired
    private EquipVulnerableInspectionMapper equipVulnerableInspectionMapper;

    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询易损设备检查
     *
     * @param id 易损设备检查主键
     * @return 易损设备检查
     */
    @Override
    public EquipVulnerableInspection selectEquipVulnerableInspectionById(Long id)
    {
        EquipVulnerableInspection inspection = equipVulnerableInspectionMapper.selectEquipVulnerableInspectionById(id);
        resolveInspectorsName(inspection);
        return inspection;
    }

    /**
     * 查询易损设备检查列表
     *
     * @param equipVulnerableInspection 易损设备检查
     * @return 易损设备检查
     */
    @Override
    public List<EquipVulnerableInspection> selectEquipVulnerableInspectionList(EquipVulnerableInspection equipVulnerableInspection)
    {
        List<EquipVulnerableInspection> list = equipVulnerableInspectionMapper.selectEquipVulnerableInspectionList(equipVulnerableInspection);
        // 解析检查人员姓名
        list.forEach(this::resolveInspectorsName);
        return list;
    }

    /**
     * 新增易损设备检查
     *
     * @param equipVulnerableInspection 易损设备检查
     * @return 结果
     */
    @Override
    public int insertEquipVulnerableInspection(EquipVulnerableInspection equipVulnerableInspection)
    {
        // 自动生成检查编号
        if (equipVulnerableInspection.getInspectionNumber() == null || equipVulnerableInspection.getInspectionNumber().isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = sdf.format(new Date());
            Random random = new Random();
            int randomNum = random.nextInt(900) + 100; // 生成100-999的随机数
            String inspectionNumber = "INSP-" + timestamp + "-" + randomNum;
            equipVulnerableInspection.setInspectionNumber(inspectionNumber);
        }
        return equipVulnerableInspectionMapper.insertEquipVulnerableInspection(equipVulnerableInspection);
    }

    /**
     * 修改易损设备检查
     *
     * @param equipVulnerableInspection 易损设备检查
     * @return 结果
     */
    @Override
    public int updateEquipVulnerableInspection(EquipVulnerableInspection equipVulnerableInspection)
    {
        return equipVulnerableInspectionMapper.updateEquipVulnerableInspection(equipVulnerableInspection);
    }

    /**
     * 批量删除易损设备检查
     *
     * @param ids 需要删除的易损设备检查主键
     * @return 结果
     */
    @Override
    public int deleteEquipVulnerableInspectionByIds(Long[] ids)
    {
        return equipVulnerableInspectionMapper.deleteEquipVulnerableInspectionByIds(ids);
    }

    /**
     * 删除易损设备检查信息
     *
     * @param id 易损设备检查主键
     * @return 结果
     */
    @Override
    public int deleteEquipVulnerableInspectionById(Long id)
    {
        return equipVulnerableInspectionMapper.deleteEquipVulnerableInspectionById(id);
    }

    /**
     * 解析检查人员姓名
     *
     * @param inspection 易损设备检查
     */
    private void resolveInspectorsName(EquipVulnerableInspection inspection) {
        if (inspection != null && inspection.getInspector() != null && !inspection.getInspector().isEmpty()) {
            // 检查是否是ID列表
            if (inspection.getInspector().matches("[0-9,]+")) {
                String[] userIds = inspection.getInspector().split(",");
                StringBuilder inspectorNames = new StringBuilder();
                for (int i = 0; i < userIds.length; i++) {
                    try {
                        Long userId = Long.valueOf(userIds[i]);
                        SysUser user = userMapper.selectUserById(userId);
                        if (user != null) {
                            if (inspectorNames.length() > 0) {
                                inspectorNames.append(",");
                            }
                            inspectorNames.append(user.getNickName());
                        }
                    } catch (NumberFormatException e) {
                        // 如果转换失败，则直接使用原始值
                        if (inspectorNames.length() > 0) {
                            inspectorNames.append(",");
                        }
                        inspectorNames.append(userIds[i]);
                    }
                }
                inspection.setInspector(inspectorNames.toString());
            }
        }
    }
}
