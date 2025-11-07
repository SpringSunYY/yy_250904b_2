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
import com.ruoyi.special.domain.EquipVehicleInventory;
import com.ruoyi.special.service.IEquipVehicleInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 场(厂)内专用机动车辆Controller
 * 
 * @author Laogao
 * @date 2025-11-04
 */
@RestController
@RequestMapping("/special/inventory")
public class EquipVehicleInventoryController extends BaseController
{
    @Autowired
    private IEquipVehicleInventoryService equipVehicleInventoryService;

    /**
     * 查询场(厂)内专用机动车辆列表
     */
    @PreAuthorize("@ss.hasPermi('special:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipVehicleInventory equipVehicleInventory)
    {
        startPage();
        List<EquipVehicleInventory> list = equipVehicleInventoryService.selectEquipVehicleInventoryList(equipVehicleInventory);
        return getDataTable(list);
    }

    /**
     * 导出场(厂)内专用机动车辆列表
     */
    @PreAuthorize("@ss.hasPermi('special:inventory:export')")
    @Log(title = "场(厂)内专用机动车辆", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipVehicleInventory equipVehicleInventory)
    {
        List<EquipVehicleInventory> list = equipVehicleInventoryService.selectEquipVehicleInventoryList(equipVehicleInventory);
        ExcelUtil<EquipVehicleInventory> util = new ExcelUtil<EquipVehicleInventory>(EquipVehicleInventory.class);
        util.exportExcel(response, list, "场(厂)内专用机动车辆数据");
    }

    /**
     * 获取场(厂)内专用机动车辆详细信息
     */
    @PreAuthorize("@ss.hasPermi('special:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipVehicleInventoryService.selectEquipVehicleInventoryById(id));
    }

    /**
     * 新增场(厂)内专用机动车辆
     */
    @PreAuthorize("@ss.hasPermi('special:inventory:add')")
    @Log(title = "场(厂)内专用机动车辆", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipVehicleInventory equipVehicleInventory)
    {
        return toAjax(equipVehicleInventoryService.insertEquipVehicleInventory(equipVehicleInventory));
    }

    /**
     * 修改场(厂)内专用机动车辆
     */
    @PreAuthorize("@ss.hasPermi('special:inventory:edit')")
    @Log(title = "场(厂)内专用机动车辆", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipVehicleInventory equipVehicleInventory)
    {
        return toAjax(equipVehicleInventoryService.updateEquipVehicleInventory(equipVehicleInventory));
    }

    /**
     * 删除场(厂)内专用机动车辆
     */
    @PreAuthorize("@ss.hasPermi('special:inventory:remove')")
    @Log(title = "场(厂)内专用机动车辆", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipVehicleInventoryService.deleteEquipVehicleInventoryByIds(ids));
    }
}
