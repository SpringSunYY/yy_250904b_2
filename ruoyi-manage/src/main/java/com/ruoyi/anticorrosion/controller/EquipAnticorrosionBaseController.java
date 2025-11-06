package com.ruoyi.anticorrosion.controller;

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
import com.ruoyi.anticorrosion.domain.EquipAnticorrosionBase;
import com.ruoyi.anticorrosion.service.IEquipAnticorrosionBaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 防腐基础信息Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/anticorrosion/base")
public class EquipAnticorrosionBaseController extends BaseController
{
    @Autowired
    private IEquipAnticorrosionBaseService equipAnticorrosionBaseService;

    /**
     * 查询防腐基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:base:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipAnticorrosionBase equipAnticorrosionBase)
    {
        startPage();
        List<EquipAnticorrosionBase> list = equipAnticorrosionBaseService.selectEquipAnticorrosionBaseList(equipAnticorrosionBase);
        return getDataTable(list);
    }

    /**
     * 导出防腐基础信息列表
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:base:export')")
    @Log(title = "防腐基础信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipAnticorrosionBase equipAnticorrosionBase)
    {
        List<EquipAnticorrosionBase> list = equipAnticorrosionBaseService.selectEquipAnticorrosionBaseList(equipAnticorrosionBase);
        ExcelUtil<EquipAnticorrosionBase> util = new ExcelUtil<EquipAnticorrosionBase>(EquipAnticorrosionBase.class);
        util.exportExcel(response, list, "防腐基础信息数据");
    }

    /**
     * 获取防腐基础信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:base:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipAnticorrosionBaseService.selectEquipAnticorrosionBaseById(id));
    }

    /**
     * 新增防腐基础信息
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:base:add')")
    @Log(title = "防腐基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipAnticorrosionBase equipAnticorrosionBase)
    {
        return toAjax(equipAnticorrosionBaseService.insertEquipAnticorrosionBase(equipAnticorrosionBase));
    }

    /**
     * 修改防腐基础信息
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:base:edit')")
    @Log(title = "防腐基础信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipAnticorrosionBase equipAnticorrosionBase)
    {
        return toAjax(equipAnticorrosionBaseService.updateEquipAnticorrosionBase(equipAnticorrosionBase));
    }

    /**
     * 删除防腐基础信息
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:base:remove')")
    @Log(title = "防腐基础信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipAnticorrosionBaseService.deleteEquipAnticorrosionBaseByIds(ids));
    }
}
