package com.ruoyi.equip.controller;

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
import com.ruoyi.equip.domain.EquipRuntimeStat;
import com.ruoyi.equip.service.IEquipRuntimeStatService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备运行管理Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/equip/stat")
public class EquipRuntimeStatController extends BaseController
{
    @Autowired
    private IEquipRuntimeStatService equipRuntimeStatService;

    /**
     * 查询设备运行管理列表
     */
    @PreAuthorize("@ss.hasPermi('equip:stat:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipRuntimeStat equipRuntimeStat)
    {
        startPage();
        List<EquipRuntimeStat> list = equipRuntimeStatService.selectEquipRuntimeStatList(equipRuntimeStat);
        return getDataTable(list);
    }

    /**
     * 导出设备运行管理列表
     */
    @PreAuthorize("@ss.hasPermi('equip:stat:export')")
    @Log(title = "设备运行管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipRuntimeStat equipRuntimeStat)
    {
        List<EquipRuntimeStat> list = equipRuntimeStatService.selectEquipRuntimeStatList(equipRuntimeStat);
        ExcelUtil<EquipRuntimeStat> util = new ExcelUtil<EquipRuntimeStat>(EquipRuntimeStat.class);
        util.exportExcel(response, list, "设备运行管理数据");
    }

    /**
     * 获取设备运行管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('equip:stat:query')")
    @GetMapping(value = "/{statId}")
    public AjaxResult getInfo(@PathVariable("statId") Long statId)
    {
        return success(equipRuntimeStatService.selectEquipRuntimeStatByStatId(statId));
    }

    /**
     * 新增设备运行管理
     */
    @PreAuthorize("@ss.hasPermi('equip:stat:add')")
    @Log(title = "设备运行管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipRuntimeStat equipRuntimeStat)
    {
        return toAjax(equipRuntimeStatService.insertEquipRuntimeStat(equipRuntimeStat));
    }

    /**
     * 修改设备运行管理
     */
    @PreAuthorize("@ss.hasPermi('equip:stat:edit')")
    @Log(title = "设备运行管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipRuntimeStat equipRuntimeStat)
    {
        return toAjax(equipRuntimeStatService.updateEquipRuntimeStat(equipRuntimeStat));
    }

    /**
     * 删除设备运行管理
     */
    @PreAuthorize("@ss.hasPermi('equip:stat:remove')")
    @Log(title = "设备运行管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{statIds}")
    public AjaxResult remove(@PathVariable Long[] statIds)
    {
        return toAjax(equipRuntimeStatService.deleteEquipRuntimeStatByStatIds(statIds));
    }
}
