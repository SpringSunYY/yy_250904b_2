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
import com.ruoyi.patrol.domain.EquipPatrolItem;
import com.ruoyi.patrol.service.IEquipPatrolItemService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备巡检项Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/patrol/item")
public class EquipPatrolItemController extends BaseController
{
    @Autowired
    private IEquipPatrolItemService equipPatrolItemService;

    /**
     * 查询设备巡检项列表
     */
    @PreAuthorize("@ss.hasPermi('patrol:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipPatrolItem equipPatrolItem)
    {
        startPage();
        List<EquipPatrolItem> list = equipPatrolItemService.selectEquipPatrolItemList(equipPatrolItem);
        return getDataTable(list);
    }

    /**
     * 导出设备巡检项列表
     */
    @PreAuthorize("@ss.hasPermi('patrol:item:export')")
    @Log(title = "设备巡检项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipPatrolItem equipPatrolItem)
    {
        List<EquipPatrolItem> list = equipPatrolItemService.selectEquipPatrolItemList(equipPatrolItem);
        ExcelUtil<EquipPatrolItem> util = new ExcelUtil<EquipPatrolItem>(EquipPatrolItem.class);
        util.exportExcel(response, list, "设备巡检项数据");
    }

    /**
     * 获取设备巡检项详细信息
     */
    @PreAuthorize("@ss.hasPermi('patrol:item:query')")
    @GetMapping(value = "/{itemId}")
    public AjaxResult getInfo(@PathVariable("itemId") Long itemId)
    {
        return success(equipPatrolItemService.selectEquipPatrolItemByItemId(itemId));
    }

    /**
     * 新增设备巡检项
     */
    @PreAuthorize("@ss.hasPermi('patrol:item:add')")
    @Log(title = "设备巡检项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipPatrolItem equipPatrolItem)
    {
        return toAjax(equipPatrolItemService.insertEquipPatrolItem(equipPatrolItem));
    }

    /**
     * 修改设备巡检项
     */
    @PreAuthorize("@ss.hasPermi('patrol:item:edit')")
    @Log(title = "设备巡检项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipPatrolItem equipPatrolItem)
    {
        return toAjax(equipPatrolItemService.updateEquipPatrolItem(equipPatrolItem));
    }

    /**
     * 删除设备巡检项
     */
    @PreAuthorize("@ss.hasPermi('patrol:item:remove')")
    @Log(title = "设备巡检项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{itemIds}")
    public AjaxResult remove(@PathVariable Long[] itemIds)
    {
        return toAjax(equipPatrolItemService.deleteEquipPatrolItemByItemIds(itemIds));
    }
}
