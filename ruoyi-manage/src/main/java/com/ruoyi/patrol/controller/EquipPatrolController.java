package com.ruoyi.patrol.controller;

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
import com.ruoyi.patrol.domain.EquipPatrol;
import com.ruoyi.patrol.service.IEquipPatrolService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 巡检记录Controller
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/patrol/patrol")
public class EquipPatrolController extends BaseController
{
    @Autowired
    private IEquipPatrolService equipPatrolService;

    /**
     * 查询巡检记录列表
     */
    @PreAuthorize("@ss.hasPermi('patrol:patrol:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipPatrol equipPatrol)
    {
        startPage();
        List<EquipPatrol> list = equipPatrolService.selectEquipPatrolList(equipPatrol);
        return getDataTable(list);
    }

    /**
     * 导出巡检记录列表
     */
    @PreAuthorize("@ss.hasPermi('patrol:patrol:export')")
    @Log(title = "巡检记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipPatrol equipPatrol)
    {
        List<EquipPatrol> list = equipPatrolService.selectEquipPatrolList(equipPatrol);
        ExcelUtil<EquipPatrol> util = new ExcelUtil<EquipPatrol>(EquipPatrol.class);
        util.exportExcel(response, list, "巡检记录数据");
    }

    /**
     * 获取巡检记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('patrol:patrol:query')")
    @GetMapping(value = "/{patrolId}")
    public AjaxResult getInfo(@PathVariable("patrolId") Long patrolId)
    {
        return success(equipPatrolService.selectEquipPatrolByPatrolId(patrolId));
    }

    /**
     * 新增巡检记录
     */
    @PreAuthorize("@ss.hasPermi('patrol:patrol:add')")
    @Log(title = "巡检记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipPatrol equipPatrol)
    {
        return toAjax(equipPatrolService.insertEquipPatrol(equipPatrol));
    }

    /**
     * 修改巡检记录
     */
    @PreAuthorize("@ss.hasPermi('patrol:patrol:edit')")
    @Log(title = "巡检记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipPatrol equipPatrol)
    {
        return toAjax(equipPatrolService.updateEquipPatrol(equipPatrol));
    }

    /**
     * 删除巡检记录
     */
    @PreAuthorize("@ss.hasPermi('patrol:patrol:remove')")
    @Log(title = "巡检记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{patrolIds}")
    public AjaxResult remove(@PathVariable Long[] patrolIds)
    {
        return toAjax(equipPatrolService.deleteEquipPatrolByPatrolIds(patrolIds));
    }
}
