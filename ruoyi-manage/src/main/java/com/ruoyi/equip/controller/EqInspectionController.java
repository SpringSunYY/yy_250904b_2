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
import com.ruoyi.equip.domain.EqInspection;
import com.ruoyi.equip.service.IEqInspectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备检验检测Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/equip/inspection")
public class EqInspectionController extends BaseController
{
    @Autowired
    private IEqInspectionService eqInspectionService;

    /**
     * 查询设备检验检测列表
     */
    @PreAuthorize("@ss.hasPermi('equip:inspection:list')")
    @GetMapping("/list")
    public TableDataInfo list(EqInspection eqInspection)
    {
        startPage();
        List<EqInspection> list = eqInspectionService.selectEqInspectionList(eqInspection);
        return getDataTable(list);
    }

    /**
     * 导出设备检验检测列表
     */
    @PreAuthorize("@ss.hasPermi('equip:inspection:export')")
    @Log(title = "设备检验检测", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EqInspection eqInspection)
    {
        List<EqInspection> list = eqInspectionService.selectEqInspectionList(eqInspection);
        ExcelUtil<EqInspection> util = new ExcelUtil<EqInspection>(EqInspection.class);
        util.exportExcel(response, list, "设备检验检测数据");
    }

    /**
     * 获取设备检验检测详细信息
     */
    @PreAuthorize("@ss.hasPermi('equip:inspection:query')")
    @GetMapping(value = "/{inspectionId}")
    public AjaxResult getInfo(@PathVariable("inspectionId") Long inspectionId)
    {
        return success(eqInspectionService.selectEqInspectionByInspectionId(inspectionId));
    }

    /**
     * 新增设备检验检测
     */
    @PreAuthorize("@ss.hasPermi('equip:inspection:add')")
    @Log(title = "设备检验检测", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EqInspection eqInspection)
    {
        return toAjax(eqInspectionService.insertEqInspection(eqInspection));
    }

    /**
     * 修改设备检验检测
     */
    @PreAuthorize("@ss.hasPermi('equip:inspection:edit')")
    @Log(title = "设备检验检测", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EqInspection eqInspection)
    {
        return toAjax(eqInspectionService.updateEqInspection(eqInspection));
    }

    /**
     * 删除设备检验检测
     */
    @PreAuthorize("@ss.hasPermi('equip:inspection:remove')")
    @Log(title = "设备检验检测", businessType = BusinessType.DELETE)
	@DeleteMapping("/{inspectionIds}")
    public AjaxResult remove(@PathVariable Long[] inspectionIds)
    {
        return toAjax(eqInspectionService.deleteEqInspectionByInspectionIds(inspectionIds));
    }
}
