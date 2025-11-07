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
import com.ruoyi.special.domain.EquipSpecialInspection;
import com.ruoyi.special.service.IEquipSpecialInspectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检验检测Controller
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
@RestController
@RequestMapping("/special/inspection")
public class EquipSpecialInspectionController extends BaseController
{
    @Autowired
    private IEquipSpecialInspectionService equipSpecialInspectionService;

    /**
     * 查询检验检测列表
     */
    @PreAuthorize("@ss.hasPermi('special:inspection:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipSpecialInspection equipSpecialInspection)
    {
        startPage();
        List<EquipSpecialInspection> list = equipSpecialInspectionService.selectEquipSpecialInspectionList(equipSpecialInspection);
        return getDataTable(list);
    }

    /**
     * 导出检验检测列表
     */
    @PreAuthorize("@ss.hasPermi('special:inspection:export')")
    @Log(title = "检验检测", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipSpecialInspection equipSpecialInspection)
    {
        List<EquipSpecialInspection> list = equipSpecialInspectionService.selectEquipSpecialInspectionList(equipSpecialInspection);
        ExcelUtil<EquipSpecialInspection> util = new ExcelUtil<EquipSpecialInspection>(EquipSpecialInspection.class);
        util.exportExcel(response, list, "检验检测数据");
    }

    /**
     * 获取检验检测详细信息
     */
    @PreAuthorize("@ss.hasPermi('special:inspection:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipSpecialInspectionService.selectEquipSpecialInspectionById(id));
    }

    /**
     * 新增检验检测
     */
    @PreAuthorize("@ss.hasPermi('special:inspection:add')")
    @Log(title = "检验检测", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipSpecialInspection equipSpecialInspection)
    {
        return toAjax(equipSpecialInspectionService.insertEquipSpecialInspection(equipSpecialInspection));
    }

    /**
     * 修改检验检测
     */
    @PreAuthorize("@ss.hasPermi('special:inspection:edit')")
    @Log(title = "检验检测", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipSpecialInspection equipSpecialInspection)
    {
        return toAjax(equipSpecialInspectionService.updateEquipSpecialInspection(equipSpecialInspection));
    }

    /**
     * 删除检验检测
     */
    @PreAuthorize("@ss.hasPermi('special:inspection:remove')")
    @Log(title = "检验检测", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipSpecialInspectionService.deleteEquipSpecialInspectionByIds(ids));
    }
}
