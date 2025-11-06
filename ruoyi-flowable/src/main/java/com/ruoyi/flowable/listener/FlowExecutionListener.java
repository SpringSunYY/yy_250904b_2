package com.ruoyi.flowable.listener;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.domain.vo.FileUrlVo;
import com.ruoyi.flowable.service.impl.FlowTaskServiceImpl;
import com.ruoyi.pur.domain.EquipPurchaseOrder;
import com.ruoyi.pur.domain.EquipPurchaserSupplier;
import com.ruoyi.pur.service.IEquipPurchaseOrderService;
import com.ruoyi.pur.service.IEquipPurchaserSupplierService;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 执行监听器
 * <p>
 * 执行监听器允许在执行过程中执行Java代码。
 * 执行监听器可以捕获事件的类型：
 * 流程实例启动，结束
 * 输出流捕获
 * 获取启动，结束
 * 路由开始，结束
 * 中间事件开始，结束
 * 触发开始事件，触发结束事件
 *
 * @author Tony
 * @date 2022/12/16
 */
@Slf4j
@Component
public class FlowExecutionListener implements ExecutionListener {
    /**
     * 流程设计器添加的参数
     */
    private Expression param;


    @Override
    public void notify(DelegateExecution execution) {
    }
}
