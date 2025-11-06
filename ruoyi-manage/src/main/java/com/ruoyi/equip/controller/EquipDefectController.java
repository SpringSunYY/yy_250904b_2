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
import com.ruoyi.equip.domain.EquipDefect;
import com.ruoyi.equip.service.IEquipDefectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备缺陷管理Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/equip/defect")
public class EquipDefectController extends BaseController
{
    @Autowired
    private IEquipDefectService equipDefectService;

    /**
     * 查询设备缺陷管理列表
     */
    @PreAuthorize("@ss.hasPermi('equip:defect:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipDefect equipDefect)
    {
        startPage();
        List<EquipDefect> list = equipDefectService.selectEquipDefectList(equipDefect);
        return getDataTable(list);
    }

    /**
     * 导出设备缺陷管理列表
     */
    @PreAuthorize("@ss.hasPermi('equip:defect:export')")
    @Log(title = "设备缺陷管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipDefect equipDefect)
    {
        List<EquipDefect> list = equipDefectService.selectEquipDefectList(equipDefect);
        ExcelUtil<EquipDefect> util = new ExcelUtil<EquipDefect>(EquipDefect.class);
        util.exportExcel(response, list, "设备缺陷管理数据");
    }

    /**
     * 获取设备缺陷管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('equip:defect:query')")
    @GetMapping(value = "/{defectId}")
    public AjaxResult getInfo(@PathVariable("defectId") Long defectId)
    {
        return success(equipDefectService.selectEquipDefectByDefectId(defectId));
    }

    /**
     * 新增设备缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('equip:defect:add')")
    @Log(title = "设备缺陷管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipDefect equipDefect)
    {
        return toAjax(equipDefectService.insertEquipDefect(equipDefect));
    }

    /**
     * 修改设备缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('equip:defect:edit')")
    @Log(title = "设备缺陷管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipDefect equipDefect)
    {
        return toAjax(equipDefectService.updateEquipDefect(equipDefect));
    }

    /**
     * 删除设备缺陷管理
     */
    @PreAuthorize("@ss.hasPermi('equip:defect:remove')")
    @Log(title = "设备缺陷管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{defectIds}")
    public AjaxResult remove(@PathVariable Long[] defectIds)
    {
        return toAjax(equipDefectService.deleteEquipDefectByDefectIds(defectIds));
    }
}
