package com.ruoyi.flowable.listener;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.flowable.common.constant.ProcessConstants;
import com.ruoyi.flowable.domain.vo.FileUrlVo;
import com.ruoyi.system.service.ISysUserService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.ExecutionListener;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.history.HistoricTaskInstance;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * 执行监听器 (通用反射插入流程实体)
 * <p>
 * 通过反射机制，根据流程配置的表达式动态获取实体类、Service Bean、和插入方法，
 * 实现流程结束时通用地将流程变量保存为业务数据。
 *
 * @author Tony
 * @date 2022/12/16
 */
@Data
@Slf4j
@Component
public class FlowCompleteExecutionListener implements ExecutionListener {
    /**
     * 流程设计器添加的参数
     */
    private Expression param;

    /**
     * 插入操作对应的 Service Bean 名称
     * 示例：equipPurchaseOrderServiceImpl
     */
    private Expression insertBean;

    /**
     * 插入数据的方法名
     * 示例：insertEquipPurchaseOrder
     */
    private Expression insertMethod;

    /**
     * 流程变量要转换的实体类完整路径
     * 示例：com.ruoyi.pur.domain.EquipPurchaseOrder
     */
    private Expression entityClass;

    @Override
    public void notify(DelegateExecution execution) {
        // 1. 获取配置的表达式参数并重命名变量
        String beanName = insertBean != null ? insertBean.getExpressionText() : null;
        String methodName = insertMethod != null ? insertMethod.getExpressionText() : null;
        String entityClassName = entityClass != null ? entityClass.getExpressionText() : null;
        //如果某个为空直接返回
        if (StringUtils.isEmpty(beanName) || StringUtils.isEmpty(methodName) || StringUtils.isEmpty(entityClassName)) {
            log.error("未配置 insertBean:{}、insertMethod:{}、entityClass:{} 表达式", beanName, methodName, entityClassName);
            return;
        }
        log.info("正在执行流程结束事件监听器,insertBean:{}、insertMethod:{}、entityClass:{} 表达式", beanName, methodName, entityClassName);

        // 如果不是结束事件，则不执行后续逻辑
        if (!ProcessConstants.END.equals(execution.getEventName())) {
            log.info("流程事件：{}", execution.getEventName());
            return;
        }

        // 2. 检查审批结果
        Map<String, Object> variables = execution.getVariables();
        if (variables.isEmpty()) {
            log.info("流程变量为空");
            return;
        }

        boolean allAgreed = true;
        for (Map.Entry<String, Object> entry : variables.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.startsWith(ProcessConstants.APPROVAL_RESULT)) {
                String valueStr = String.valueOf(value);
                if (!ProcessConstants.APPROVAL_RESULT_AGREE.equals(valueStr)) {
                    allAgreed = false;
                    break;
                }
            }
        }
        if (!allAgreed) {
            log.info("所有审批未通过，不执行插入操作");
            return;
        }

        // 3. 通用反射执行逻辑开始：实体转换
        Object entityObject = null;
        Object serviceBean = null;
        // 3.1 反射获取实体类对象
        try {
            // 动态加载实体类
            Class<?> entityCls = Class.forName(entityClassName);
            // 将流程变量转换为实体对象
            entityObject = JSONObject.parseObject(JSONObject.toJSONString(variables), entityCls);
            System.out.println("转换后的实体对象: " + entityObject.toString());
        } catch (Exception e) {
            log.error("流程变量转换为实体对象失败，实体类名: {}", entityClassName, e);
            // 不抛出异常，避免事务回滚
            return;
        }

        try {
            serviceBean = SpringUtils.getBean(beanName);
        } catch (Exception e) {
            log.error("通过 BeanName: {} 获取 Service Bean 失败", beanName, e);
            // 不继续执行后续逻辑
            return;
        }

        // 4. 辅助服务和通用字段处理
        ISysUserService sysUserService = SpringUtils.getBean(ISysUserService.class);
        RepositoryService repositoryService = SpringUtils.getBean(RepositoryService.class);

        handleCommonFields(execution, entityObject, sysUserService, repositoryService);
        log.info("实体：{}", entityObject.toString());
        // 5. 反射调用插入方法
        try {
            Class<?> entityCls = entityObject.getClass();
            // 查找方法，参数类型为转换后的实体类类型
            Method insertMethod = null;
            if (methodName != null) {
                insertMethod = serviceBean.getClass().getMethod(methodName, entityCls);
            }
            // 调用方法执行插入
            Object result = insertMethod.invoke(serviceBean, entityObject);
            log.info("通过反射成功调用方法：{}.{}，结果：{}", beanName, methodName, result);

        } catch (NoSuchMethodException e) {
            log.error("Service Bean: {} 中找不到方法名: {}，或参数类型不匹配: {}", beanName, methodName, entityObject.getClass().getName(), e);
        } catch (Exception e) {
            log.error("通过反射调用方法: {}.{} 失败", beanName, methodName, e);
            // 不传播异常，避免事务回滚
        }
    }


    /**
     * 处理流程实体对象中通用的字段，如：部署ID、流程实例ID、申请人信息、文件格式转换等。
     */
    private void handleCommonFields(DelegateExecution execution, Object entityObject, ISysUserService sysUserService, RepositoryService repositoryService) {

        //设置任务ID
        try {
            // 在执行监听器中无法直接获取taskId，需要通过查询历史任务实例获取
            List<HistoricTaskInstance> taskInstances = SpringUtils.getBean(HistoryService.class)
                    .createHistoricTaskInstanceQuery()
                    .processInstanceId(execution.getProcessInstanceId())
                    .orderByTaskCreateTime()
                    .desc()
                    .listPage(0, 1);

            if (!taskInstances.isEmpty()) {
                String taskId = taskInstances.get(0).getId();
                setFieldValue(entityObject, "setTaskId", taskId, String.class);
            }
        } catch (Exception e) {
            log.error("设置任务ID失败", e);
        }

        // 设置流程实例ID
        String processInstanceId = execution.getProcessInstanceId();
        try {
            setFieldValue(entityObject, "setProcessInstanceId", processInstanceId, String.class);
        } catch (Exception e) {
            log.error("设置流程实例ID失败", e);
        }

        // 获取并设置部署ID
        String deployId = null;
        try {
            String processDefinitionId = execution.getProcessDefinitionId();
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();

            if (processDefinition != null) {
                deployId = processDefinition.getDeploymentId();
                setFieldValue(entityObject, "setDeployId", deployId, String.class);
            }
        } catch (Exception e) {
            log.error("获取或设置部署ID失败", e);
        }

        try {
            Object initiatorObj = execution.getVariable(ProcessConstants.PROCESS_INITIATOR);
            if (StringUtils.isNull(initiatorObj)) {
                return;
            }
            Long userId = null;

            if (initiatorObj instanceof Long) {
                userId = (Long) initiatorObj;
            } else if (initiatorObj instanceof String) {
                userId = Long.parseLong((String) initiatorObj);
            }
            if (StringUtils.isNull(userId)) {
                return;
            }
            SysUser user = sysUserService.selectUserById(userId);
            if (StringUtils.isNull(user)) {
                return;
            }
            String userName = user.getUserName();
            Long deptId = user.getDeptId();
            SysDept dept = user.getDept();
            String deptName = dept != null ? dept.getDeptName() : "未知部门";

            log.info("申请人信息：用户ID={}, 账号={}, 部门名称={}", userId, userName, deptName);

            // 尝试设置申请人信息
            setFieldValue(entityObject, "setApplyUserId", userId, Long.class);
            setFieldValue(entityObject, "setApplyUserName", userName, String.class);
            setFieldValue(entityObject, "setDeptName", deptName, String.class);
            setFieldValue(entityObject, "setDeptId", deptId, Long.class);

        } catch (Exception e) {
            log.error("获取或设置申请人信息失败", e);
        }
        try {
            // 1. 尝试通过反射获取 getAppendix() 方法
            Method getAppendixMethod = entityObject.getClass().getMethod("getAppendix");
            Object appendixObj = getAppendixMethod.invoke(entityObject);

            if (StringUtils.isNull(appendixObj) || StringUtils.isEmpty(String.valueOf(appendixObj))) {
                return;
            }
            String appendixJson = String.valueOf(appendixObj);

            // 2. 执行附件 JSON 转换逻辑
            try {
                List<FileUrlVo> fileUrlVos = JSONArray.parseArray(appendixJson, FileUrlVo.class);
                StringBuffer url = new StringBuffer();
                fileUrlVos.forEach(fileUrlVo -> {
                    url.append(fileUrlVo.getUrl()).append(",");
                });

                if (url.length() > 0) {
                    url.deleteCharAt(url.length() - 1);
                    String convertedUrl = url.toString();

                    // 3. 通过反射设置 setAppendix(String) 方法
                    setFieldValue(entityObject, "setAppendix", convertedUrl, String.class);
                    log.info("通用附件字段转换成功");
                }
            } catch (Exception e) {
                log.error("附件JSON解析或转换失败，原始内容：{}", appendixJson, e);
                // 不传播异常，避免事务回滚
            }
        } catch (NoSuchMethodException e) {
            log.debug("实体类: {} 没有 getAppendix() 或 setAppendix(String) 方法，跳过附件转换。", entityObject.getClass().getName());
        } catch (Exception e) {
            log.error("通用附件转换逻辑执行失败", e);
            // 不传播异常，避免事务回滚
        }
    }

    /**
     * 通过反射尝试设置实体对象的字段值
     */
    private void setFieldValue(Object target, String methodName, Object value, Class<?> paramType) {
        if (value == null) {
            return;
        }
        try {
            Method method = target.getClass().getMethod(methodName, paramType);
            method.invoke(target, value);
            log.debug("反射设置字段成功: {}.{}", target.getClass().getSimpleName(), methodName);
        } catch (NoSuchMethodException e) {
            // 这是预期的，如果实体类没有该通用字段，跳过即可
            log.debug("实体类: {} 没有方法: {} ({})，跳过设置。", target.getClass().getName(), methodName, paramType.getName());
        } catch (Exception e) {
            log.error("通过反射调用方法: {}.{} 失败", target.getClass().getName(), methodName, e);
        }
    }
}
