package com.ruoyi.vulnerable.controller;

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
import com.ruoyi.vulnerable.domain.EquipVulnerableInspection;
import com.ruoyi.vulnerable.service.IEquipVulnerableInspectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 易损设备检查Controller
 * 
 * @author Laogao
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/vulnerable/inspection")
public class EquipVulnerableInspectionController extends BaseController
{
    @Autowired
    private IEquipVulnerableInspectionService equipVulnerableInspectionService;

    /**
     * 查询易损设备检查列表
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:inspection:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipVulnerableInspection equipVulnerableInspection)
    {
        startPage();
        List<EquipVulnerableInspection> list = equipVulnerableInspectionService.selectEquipVulnerableInspectionList(equipVulnerableInspection);
        return getDataTable(list);
    }

    /**
     * 导出易损设备检查列表
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:inspection:export')")
    @Log(title = "易损设备检查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipVulnerableInspection equipVulnerableInspection)
    {
        List<EquipVulnerableInspection> list = equipVulnerableInspectionService.selectEquipVulnerableInspectionList(equipVulnerableInspection);
        ExcelUtil<EquipVulnerableInspection> util = new ExcelUtil<EquipVulnerableInspection>(EquipVulnerableInspection.class);
        util.exportExcel(response, list, "易损设备检查数据");
    }

    /**
     * 获取易损设备检查详细信息
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:inspection:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipVulnerableInspectionService.selectEquipVulnerableInspectionById(id));
    }

    /**
     * 新增易损设备检查
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:inspection:add')")
    @Log(title = "易损设备检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipVulnerableInspection equipVulnerableInspection)
    {
        return toAjax(equipVulnerableInspectionService.insertEquipVulnerableInspection(equipVulnerableInspection));
    }

    /**
     * 修改易损设备检查
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:inspection:edit')")
    @Log(title = "易损设备检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipVulnerableInspection equipVulnerableInspection)
    {
        return toAjax(equipVulnerableInspectionService.updateEquipVulnerableInspection(equipVulnerableInspection));
    }

    /**
     * 删除易损设备检查
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:inspection:remove')")
    @Log(title = "易损设备检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipVulnerableInspectionService.deleteEquipVulnerableInspectionByIds(ids));
    }
}
