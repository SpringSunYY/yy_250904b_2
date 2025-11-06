package com.ruoyi.lubr.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.ruoyi.lubr.domain.EquipLubrPlan;
import com.ruoyi.lubr.service.IEquipLubrPlanService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 润滑计划Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/lubr/plan")
public class EquipLubrPlanController extends BaseController
{
    @Autowired
    private IEquipLubrPlanService equipLubrPlanService;

    /**
     * 查询润滑计划列表
     */
    @PreAuthorize("@ss.hasPermi('lubr:plan:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipLubrPlan equipLubrPlan)
    {
        startPage();
        List<EquipLubrPlan> list = equipLubrPlanService.selectEquipLubrPlanList(equipLubrPlan);
        return getDataTable(list);
    }

    /**
     * 导出润滑计划列表
     */
    @PreAuthorize("@ss.hasPermi('lubr:plan:export')")
    @Log(title = "润滑计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipLubrPlan equipLubrPlan)
    {
        List<EquipLubrPlan> list = equipLubrPlanService.selectEquipLubrPlanList(equipLubrPlan);
        ExcelUtil<EquipLubrPlan> util = new ExcelUtil<EquipLubrPlan>(EquipLubrPlan.class);
        util.exportExcel(response, list, "润滑计划数据");
    }

    /**
     * 获取润滑计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('lubr:plan:query')")
    @GetMapping(value = "/{planId}")
    public AjaxResult getInfo(@PathVariable("planId") Long planId)
    {
        return success(equipLubrPlanService.selectEquipLubrPlanByPlanId(planId));
    }

    /**
     * 新增润滑计划
     */
    @PreAuthorize("@ss.hasPermi('lubr:plan:add')")
    @Log(title = "润滑计划", businessType = BusinessType.INSERT)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult add(@RequestBody EquipLubrPlan equipLubrPlan)
    {
        return toAjax(equipLubrPlanService.insertEquipLubrPlan(equipLubrPlan));
    }

    /**
     * 修改润滑计划
     */
    @PreAuthorize("@ss.hasPermi('lubr:plan:edit')")
    @Log(title = "润滑计划", businessType = BusinessType.UPDATE)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult edit(@RequestBody EquipLubrPlan equipLubrPlan)
    {
        return toAjax(equipLubrPlanService.updateEquipLubrPlan(equipLubrPlan));
    }

    /**
     * 删除润滑计划
     */
    @PreAuthorize("@ss.hasPermi('lubr:plan:remove')")
    @Log(title = "润滑计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds)
    {
        return toAjax(equipLubrPlanService.deleteEquipLubrPlanByPlanIds(planIds));
    }
}
