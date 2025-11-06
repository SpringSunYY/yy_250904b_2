package com.ruoyi.repair.controller;

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
import com.ruoyi.repair.domain.EquipRepairPlan;
import com.ruoyi.repair.service.IEquipRepairPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备检修计划Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/repair/plan")
public class EquipRepairPlanController extends BaseController
{
    @Autowired
    private IEquipRepairPlanService equipRepairPlanService;

    /**
     * 查询设备检修计划列表
     */
    @PreAuthorize("@ss.hasPermi('repair:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipRepairPlan equipRepairPlan)
    {
        startPage();
        List<EquipRepairPlan> list = equipRepairPlanService.selectEquipRepairPlanList(equipRepairPlan);
        return getDataTable(list);
    }

    /**
     * 导出设备检修计划列表
     */
    @PreAuthorize("@ss.hasPermi('repair:plan:export')")
    @Log(title = "设备检修计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipRepairPlan equipRepairPlan)
    {
        List<EquipRepairPlan> list = equipRepairPlanService.selectEquipRepairPlanList(equipRepairPlan);
        ExcelUtil<EquipRepairPlan> util = new ExcelUtil<EquipRepairPlan>(EquipRepairPlan.class);
        util.exportExcel(response, list, "设备检修计划数据");
    }

    /**
     * 获取设备检修计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:plan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return success(equipRepairPlanService.selectEquipRepairPlanByPlanId(planId));
    }

    /**
     * 新增设备检修计划
     */
    @PreAuthorize("@ss.hasPermi('repair:plan:add')")
    @Log(title = "设备检修计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipRepairPlan equipRepairPlan)
    {
        return toAjax(equipRepairPlanService.insertEquipRepairPlan(equipRepairPlan));
    }

    /**
     * 修改设备检修计划
     */
    @PreAuthorize("@ss.hasPermi('repair:plan:edit')")
    @Log(title = "设备检修计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipRepairPlan equipRepairPlan)
    {
        return toAjax(equipRepairPlanService.updateEquipRepairPlan(equipRepairPlan));
    }

    /**
     * 删除设备检修计划
     */
    @PreAuthorize("@ss.hasPermi('repair:plan:remove')")
    @Log(title = "设备检修计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(equipRepairPlanService.deleteEquipRepairPlanByPlanIds(planIds));
    }
}
