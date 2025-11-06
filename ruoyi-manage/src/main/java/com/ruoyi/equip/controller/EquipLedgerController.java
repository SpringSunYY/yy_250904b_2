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
import com.ruoyi.equip.domain.EquipLedger;
import com.ruoyi.equip.service.IEquipLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备台账Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/equip/ledger")
public class EquipLedgerController extends BaseController
{
    @Autowired
    private IEquipLedgerService equipLedgerService;

    /**
     * 查询设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('equip:ledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipLedger equipLedger)
    {
        startPage();
        List<EquipLedger> list = equipLedgerService.selectEquipLedgerList(equipLedger);
        return getDataTable(list);
    }

    /**
     * 导出设备台账列表
     */
    @PreAuthorize("@ss.hasPermi('equip:ledger:export')")
    @Log(title = "设备台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipLedger equipLedger)
    {
        List<EquipLedger> list = equipLedgerService.selectEquipLedgerList(equipLedger);
        ExcelUtil<EquipLedger> util = new ExcelUtil<EquipLedger>(EquipLedger.class);
        util.exportExcel(response, list, "设备台账数据");
    }

    /**
     * 获取设备台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('equip:ledger:query')")
    @GetMapping(value = "/{equipId}")
    public AjaxResult getInfo(@PathVariable("equipId") Long equipId)
    {
        return success(equipLedgerService.selectEquipLedgerByEquipId(equipId));
    }

    /**
     * 新增设备台账
     */
    @PreAuthorize("@ss.hasPermi('equip:ledger:add')")
    @Log(title = "设备台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipLedger equipLedger)
    {
        return toAjax(equipLedgerService.insertEquipLedger(equipLedger));
    }

    /**
     * 修改设备台账
     */
    @PreAuthorize("@ss.hasPermi('equip:ledger:edit')")
    @Log(title = "设备台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipLedger equipLedger)
    {
        return toAjax(equipLedgerService.updateEquipLedger(equipLedger));
    }

    /**
     * 删除设备台账
     */
    @PreAuthorize("@ss.hasPermi('equip:ledger:remove')")
    @Log(title = "设备台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{equipIds}")
    public AjaxResult remove(@PathVariable Long[] equipIds)
    {
        return toAjax(equipLedgerService.deleteEquipLedgerByEquipIds(equipIds));
    }
}
