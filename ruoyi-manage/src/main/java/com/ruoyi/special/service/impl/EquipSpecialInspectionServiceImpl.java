package com.ruoyi.special.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.special.domain.EquipSpecialInspection;
import com.ruoyi.special.mapper.EquipSpecialInspectionMapper;
import com.ruoyi.special.service.IEquipSpecialInspectionService;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 检验检测Service业务层处理
 *
 * @author ruoyi
 * @date 2025-11-04
 */
@Service
public class EquipSpecialInspectionServiceImpl implements IEquipSpecialInspectionService {
    @Autowired
    private EquipSpecialInspectionMapper equipSpecialInspectionMapper;

    @Resource
    private ISysNoticeService sysNoticeService;

    /**
     * 查询检验检测
     *
     * @param id 检验检测主键
     * @return 检验检测
     */
    @Override
    public EquipSpecialInspection selectEquipSpecialInspectionById(Long id) {
        return equipSpecialInspectionMapper.selectEquipSpecialInspectionById(id);
    }

    /**
     * 查询检验检测列表
     *
     * @param equipSpecialInspection 检验检测
     * @return 检验检测
     */
    @Override
    public List<EquipSpecialInspection> selectEquipSpecialInspectionList(EquipSpecialInspection equipSpecialInspection) {
        return equipSpecialInspectionMapper.selectEquipSpecialInspectionList(equipSpecialInspection);
    }

    /**
     * 新增检验检测
     *
     * @param equipSpecialInspection 检验检测
     * @return 结果
     */
    @Override
    public int insertEquipSpecialInspection(EquipSpecialInspection equipSpecialInspection) {
        return equipSpecialInspectionMapper.insertEquipSpecialInspection(equipSpecialInspection);
    }

    /**
     * 修改检验检测
     *
     * @param equipSpecialInspection 检验检测
     * @return 结果
     */
    @Override
    public int updateEquipSpecialInspection(EquipSpecialInspection equipSpecialInspection) {
        return equipSpecialInspectionMapper.updateEquipSpecialInspection(equipSpecialInspection);
    }

    /**
     * 批量删除检验检测
     *
     * @param ids 需要删除的检验检测主键
     * @return 结果
     */
    @Override
    public int deleteEquipSpecialInspectionByIds(Long[] ids) {
        return equipSpecialInspectionMapper.deleteEquipSpecialInspectionByIds(ids);
    }

    /**
     * 删除检验检测信息
     *
     * @param id 检验检测主键
     * @return 结果
     */
    @Override
    public int deleteEquipSpecialInspectionById(Long id) {
        return equipSpecialInspectionMapper.deleteEquipSpecialInspectionById(id);
    }

    @Override
    public void autoRemindNotice(Integer days) {
        //首先查询到数据，距离今天的数据
        Date nowDate = DateUtils.getNowDate();
        Date endDate = DateUtils.addDays(nowDate, days);
        List<EquipSpecialInspection> list = equipSpecialInspectionMapper.selectEquipSecialInspectionByNextInspectionDate(endDate);
        for (EquipSpecialInspection equipSpecialInspection : list) {
            SysNotice sysNotice = new SysNotice();
            sysNotice.setNoticeTitle("设备检验即将到达下一检验日期提醒");
            sysNotice.setNoticeContent(StringUtils.format(
                    "设备名称：{}，即将到达下一检查日期：{}，请相关人员立即查看。"
                    , equipSpecialInspection.getEquipmentName(), DateUtils.dateTime(equipSpecialInspection.getNextInspectionDate())));
            sysNotice.setNoticeType("1");
            sysNotice.setStatus("0");
            sysNotice.setCreateTime(nowDate);
            sysNotice.setCreateBy("system");
            sysNoticeService.insertNotice(sysNotice);
        }
    }
}
