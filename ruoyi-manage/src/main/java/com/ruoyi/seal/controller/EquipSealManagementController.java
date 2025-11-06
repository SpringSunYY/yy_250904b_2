package com.ruoyi.seal.controller;

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
import com.ruoyi.seal.domain.EquipSealManagement;
import com.ruoyi.seal.service.IEquipSealManagementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 泄露处置Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/seal/management")
public class EquipSealManagementController extends BaseController
{
    @Autowired
    private IEquipSealManagementService equipSealManagementService;

    /**
     * 查询泄露处置列表
     */
    @PreAuthorize("@ss.hasPermi('seal:management:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipSealManagement equipSealManagement)
    {
        startPage();
        List<EquipSealManagement> list = equipSealManagementService.selectEquipSealManagementList(equipSealManagement);
        return getDataTable(list);
    }

    /**
     * 导出泄露处置列表
     */
    @PreAuthorize("@ss.hasPermi('seal:management:export')")
    @Log(title = "泄露处置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipSealManagement equipSealManagement)
    {
        List<EquipSealManagement> list = equipSealManagementService.selectEquipSealManagementList(equipSealManagement);
        ExcelUtil<EquipSealManagement> util = new ExcelUtil<EquipSealManagement>(EquipSealManagement.class);
        util.exportExcel(response, list, "泄露处置数据");
    }

    /**
     * 获取泄露处置详细信息
     */
    @PreAuthorize("@ss.hasPermi('seal:management:query')")
    @GetMapping(value = "/{manageId}")
    public AjaxResult getInfo(@PathVariable("manageId") Long manageId)
    {
        return success(equipSealManagementService.selectEquipSealManagementByManageId(manageId));
    }

    /**
     * 新增泄露处置
     */
    @PreAuthorize("@ss.hasPermi('seal:management:add')")
    @Log(title = "泄露处置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipSealManagement equipSealManagement)
    {
        return toAjax(equipSealManagementService.insertEquipSealManagement(equipSealManagement));
    }

    /**
     * 修改泄露处置
     */
    @PreAuthorize("@ss.hasPermi('seal:management:edit')")
    @Log(title = "泄露处置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipSealManagement equipSealManagement)
    {
        return toAjax(equipSealManagementService.updateEquipSealManagement(equipSealManagement));
    }

    /**
     * 删除泄露处置
     */
    @PreAuthorize("@ss.hasPermi('seal:management:remove')")
    @Log(title = "泄露处置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{manageIds}")
    public AjaxResult remove(@PathVariable Long[] manageIds)
    {
        return toAjax(equipSealManagementService.deleteEquipSealManagementByManageIds(manageIds));
    }
}
