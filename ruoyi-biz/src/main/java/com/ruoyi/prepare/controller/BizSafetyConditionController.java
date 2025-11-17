package com.ruoyi.prepare.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.prepare.domain.BizSafetyCondition;
import com.ruoyi.prepare.service.IBizSafetyConditionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 安全条件检查Controller
 *
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/prepare/condition")
public class BizSafetyConditionController extends BaseController
{
    @Autowired
    private IBizSafetyConditionService bizSafetyConditionService;

    // 用于存储每年的计数器，key为年份，value为该年份的计数器
    private static final Map<String, AtomicInteger> YEAR_COUNTER_MAP = new ConcurrentHashMap<>();

    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    /**
     * 查询安全条件检查列表
     */
    @PreAuthorize("@ss.hasPermi('prepare:condition:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSafetyCondition bizSafetyCondition)
    {
        startPage();
        List<BizSafetyCondition> list = bizSafetyConditionService.selectBizSafetyConditionList(bizSafetyCondition);
        return getDataTable(list);
    }

    /**
     * 导出安全条件检查列表
     */
    @PreAuthorize("@ss.hasPermi('prepare:condition:export')")
    @Log(title = "安全条件检查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSafetyCondition bizSafetyCondition)
    {
        List<BizSafetyCondition> list = bizSafetyConditionService.selectBizSafetyConditionList(bizSafetyCondition);
        ExcelUtil<BizSafetyCondition> util = new ExcelUtil<BizSafetyCondition>(BizSafetyCondition.class);
        util.exportExcel(response, list, "安全条件检查数据");
    }

    /**
     * 获取安全条件检查详细信息
     */
    @PreAuthorize("@ss.hasPermi('prepare:condition:query')")
    @GetMapping(value = "/{conditionId}")
    public AjaxResult getInfo(@PathVariable("conditionId") Long conditionId)
    {
        return success(bizSafetyConditionService.selectBizSafetyConditionByConditionId(conditionId));
    }

    /**
     * 新增安全条件检查
     */
    @PreAuthorize("@ss.hasPermi('prepare:condition:add')")
    @Log(title = "安全条件检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSafetyCondition bizSafetyCondition)
    {
        // 自动生成检查编号
        if (bizSafetyCondition.getCheckCode() == null || bizSafetyCondition.getCheckCode().isEmpty()) {
            String year = getCurrentYear();
            AtomicInteger counter = YEAR_COUNTER_MAP.computeIfAbsent(year, k -> new AtomicInteger(0));
            int count = counter.incrementAndGet();

            // 如果超过999，则重置为1
            if (count > 999) {
                counter.compareAndSet(count, 1);
                count = counter.get();
            }

            String checkCode = "JC" + year + String.format("%03d", count);
            bizSafetyCondition.setCheckCode(checkCode);
        }
        return toAjax(bizSafetyConditionService.insertBizSafetyCondition(bizSafetyCondition));
    }

    /**
     * 修改安全条件检查
     */
    @PreAuthorize("@ss.hasPermi('prepare:condition:edit')")
    @Log(title = "安全条件检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSafetyCondition bizSafetyCondition)
    {
        return toAjax(bizSafetyConditionService.updateBizSafetyCondition(bizSafetyCondition));
    }

    /**
     * 删除安全条件检查
     */
    @PreAuthorize("@ss.hasPermi('prepare:condition:remove')")
    @Log(title = "安全条件检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{conditionIds}")
    public AjaxResult remove(@PathVariable Long[] conditionIds)
    {
        return toAjax(bizSafetyConditionService.deleteBizSafetyConditionByConditionIds(conditionIds));
    }
}
