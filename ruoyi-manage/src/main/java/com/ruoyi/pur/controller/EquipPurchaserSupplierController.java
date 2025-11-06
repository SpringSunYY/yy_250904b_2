package com.ruoyi.pur.controller;

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
import com.ruoyi.pur.domain.EquipPurchaserSupplier;
import com.ruoyi.pur.service.IEquipPurchaserSupplierService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 供应商管理Controller
 * 
 * @author ruoyi
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/pur/supplier")
public class EquipPurchaserSupplierController extends BaseController
{
    @Autowired
    private IEquipPurchaserSupplierService equipPurchaserSupplierService;

    /**
     * 查询供应商管理列表
     */
    @PreAuthorize("@ss.hasPermi('pur:supplier:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipPurchaserSupplier equipPurchaserSupplier)
    {
        startPage();
        List<EquipPurchaserSupplier> list = equipPurchaserSupplierService.selectEquipPurchaserSupplierList(equipPurchaserSupplier);
        return getDataTable(list);
    }

    /**
     * 查询所有供应商列表用于字典
     */
    @PreAuthorize("@ss.hasPermi('pur:supplier:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll(EquipPurchaserSupplier equipPurchaserSupplier)
    {
        List<EquipPurchaserSupplier> list = equipPurchaserSupplierService.selectEquipPurchaserSupplierList(equipPurchaserSupplier);
        return AjaxResult.success(list);
    }

    /**
     * 导出供应商管理列表
     */
    @PreAuthorize("@ss.hasPermi('pur:supplier:export')")
    @Log(title = "供应商管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipPurchaserSupplier equipPurchaserSupplier)
    {
        List<EquipPurchaserSupplier> list = equipPurchaserSupplierService.selectEquipPurchaserSupplierList(equipPurchaserSupplier);
        ExcelUtil<EquipPurchaserSupplier> util = new ExcelUtil<EquipPurchaserSupplier>(EquipPurchaserSupplier.class);
        util.exportExcel(response, list, "供应商管理数据");
    }

    /**
     * 获取供应商管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('pur:supplier:query')")
    @GetMapping(value = "/{supplierId}")
    public AjaxResult getInfo(@PathVariable("supplierId") Long supplierId)
    {
        return success(equipPurchaserSupplierService.selectEquipPurchaserSupplierBySupplierId(supplierId));
    }

    /**
     * 新增供应商管理
     */
    @PreAuthorize("@ss.hasPermi('pur:supplier:add')")
    @Log(title = "供应商管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipPurchaserSupplier equipPurchaserSupplier)
    {
        return toAjax(equipPurchaserSupplierService.insertEquipPurchaserSupplier(equipPurchaserSupplier));
    }

    /**
     * 修改供应商管理
     */
    @PreAuthorize("@ss.hasPermi('pur:supplier:edit')")
    @Log(title = "供应商管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipPurchaserSupplier equipPurchaserSupplier)
    {
        return toAjax(equipPurchaserSupplierService.updateEquipPurchaserSupplier(equipPurchaserSupplier));
    }

    /**
     * 删除供应商管理
     */
    @PreAuthorize("@ss.hasPermi('pur:supplier:remove')")
    @Log(title = "供应商管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{supplierIds}")
    public AjaxResult remove(@PathVariable Long[] supplierIds)
    {
        return toAjax(equipPurchaserSupplierService.deleteEquipPurchaserSupplierBySupplierIds(supplierIds));
    }
}
