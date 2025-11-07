package com.ruoyi.quartz.task;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.special.service.IEquipSpecialInspectionService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 定时任务
 *
 * @Project: equ
 * @Author: YY
 * @CreateTime: 2025-11-07  17:55
 * @Version: 1.0
 */
@Component("manageTask")
public class ManageTask {
    @Resource
    private IEquipSpecialInspectionService equipSpecialInspectionService;

    public void autoRemindNotice(Integer days) {
        System.out.println("开始执行定时任务");
        if (StringUtils.isNull(days)) {
            days = 30;
        }
        equipSpecialInspectionService.autoRemindNotice(days);
    }
}
