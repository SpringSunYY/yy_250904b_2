package com.ruoyi.special.controller;

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
import com.ruoyi.special.domain.EquipSpecialBoiler;
import com.ruoyi.special.service.IEquipSpecialBoilerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 锅炉Controller
 * 
 * @author Laogao
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/special/boiler")
public class EquipSpecialBoilerController extends BaseController
{
    @Autowired
    private IEquipSpecialBoilerService equipSpecialBoilerService;

    /**
     * 查询锅炉列表
     */
    @PreAuthorize("@ss.hasPermi('special:boiler:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipSpecialBoiler equipSpecialBoiler)
    {
        startPage();
        List<EquipSpecialBoiler> list = equipSpecialBoilerService.selectEquipSpecialBoilerList(equipSpecialBoiler);
        return getDataTable(list);
    }

    /**
     * 导出锅炉列表
     */
    @PreAuthorize("@ss.hasPermi('special:boiler:export')")
    @Log(title = "锅炉", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipSpecialBoiler equipSpecialBoiler)
    {
        List<EquipSpecialBoiler> list = equipSpecialBoilerService.selectEquipSpecialBoilerList(equipSpecialBoiler);
        ExcelUtil<EquipSpecialBoiler> util = new ExcelUtil<EquipSpecialBoiler>(EquipSpecialBoiler.class);
        util.exportExcel(response, list, "锅炉数据");
    }

    /**
     * 获取锅炉详细信息
     */
    @PreAuthorize("@ss.hasPermi('special:boiler:query')")
    @GetMapping(value = "/{boilerId}")
    public AjaxResult getInfo(@PathVariable("boilerId") Long boilerId)
    {
        return success(equipSpecialBoilerService.selectEquipSpecialBoilerByBoilerId(boilerId));
    }

    /**
     * 新增锅炉
     */
    @PreAuthorize("@ss.hasPermi('special:boiler:add')")
    @Log(title = "锅炉", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipSpecialBoiler equipSpecialBoiler)
    {
        return toAjax(equipSpecialBoilerService.insertEquipSpecialBoiler(equipSpecialBoiler));
    }

    /**
     * 修改锅炉
     */
    @PreAuthorize("@ss.hasPermi('special:boiler:edit')")
    @Log(title = "锅炉", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipSpecialBoiler equipSpecialBoiler)
    {
        return toAjax(equipSpecialBoilerService.updateEquipSpecialBoiler(equipSpecialBoiler));
    }

    /**
     * 删除锅炉
     */
    @PreAuthorize("@ss.hasPermi('special:boiler:remove')")
    @Log(title = "锅炉", businessType = BusinessType.DELETE)
	@DeleteMapping("/{boilerIds}")
    public AjaxResult remove(@PathVariable Long[] boilerIds)
    {
        return toAjax(equipSpecialBoilerService.deleteEquipSpecialBoilerByBoilerIds(boilerIds));
    }
}
