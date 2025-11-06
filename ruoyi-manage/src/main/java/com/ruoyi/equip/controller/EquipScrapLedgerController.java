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
import com.ruoyi.equip.domain.EquipScrapLedger;
import com.ruoyi.equip.service.IEquipScrapLedgerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备报废管理Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/equip/scrapledger")
public class EquipScrapLedgerController extends BaseController
{
    @Autowired
    private IEquipScrapLedgerService equipScrapLedgerService;

    /**
     * 查询设备报废管理列表
     */
    @PreAuthorize("@ss.hasPermi('equip:scrapledger:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipScrapLedger equipScrapLedger)
    {
        startPage();
        List<EquipScrapLedger> list = equipScrapLedgerService.selectEquipScrapLedgerList(equipScrapLedger);
        return getDataTable(list);
    }

    /**
     * 导出设备报废管理列表
     */
    @PreAuthorize("@ss.hasPermi('equip:scrapledger:export')")
    @Log(title = "设备报废管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipScrapLedger equipScrapLedger)
    {
        List<EquipScrapLedger> list = equipScrapLedgerService.selectEquipScrapLedgerList(equipScrapLedger);
        ExcelUtil<EquipScrapLedger> util = new ExcelUtil<EquipScrapLedger>(EquipScrapLedger.class);
        util.exportExcel(response, list, "设备报废管理数据");
    }

    /**
     * 获取设备报废管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('equip:scrapledger:query')")
    @GetMapping(value = "/{scrapId}")
    public AjaxResult getInfo(@PathVariable("scrapId") Long scrapId)
    {
        return success(equipScrapLedgerService.selectEquipScrapLedgerByScrapId(scrapId));
    }

    /**
     * 新增设备报废管理
     */
    @PreAuthorize("@ss.hasPermi('equip:scrapledger:add')")
    @Log(title = "设备报废管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipScrapLedger equipScrapLedger)
    {
        return toAjax(equipScrapLedgerService.insertEquipScrapLedger(equipScrapLedger));
    }

    /**
     * 修改设备报废管理
     */
    @PreAuthorize("@ss.hasPermi('equip:scrapledger:edit')")
    @Log(title = "设备报废管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipScrapLedger equipScrapLedger)
    {
        return toAjax(equipScrapLedgerService.updateEquipScrapLedger(equipScrapLedger));
    }

    /**
     * 删除设备报废管理
     */
    @PreAuthorize("@ss.hasPermi('equip:scrapledger:remove')")
    @Log(title = "设备报废管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{scrapIds}")
    public AjaxResult remove(@PathVariable Long[] scrapIds)
    {
        return toAjax(equipScrapLedgerService.deleteEquipScrapLedgerByScrapIds(scrapIds));
    }
}
