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
import com.ruoyi.seal.domain.EquipSealBase;
import com.ruoyi.seal.service.IEquipSealBaseService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 密封点Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/seal/base")
public class EquipSealBaseController extends BaseController
{
    @Autowired
    private IEquipSealBaseService equipSealBaseService;

    /**
     * 查询密封点列表
     */
    @PreAuthorize("@ss.hasPermi('seal:base:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipSealBase equipSealBase)
    {
        startPage();
        List<EquipSealBase> list = equipSealBaseService.selectEquipSealBaseList(equipSealBase);
        return getDataTable(list);
    }

    /**
     * 导出密封点列表
     */
    @PreAuthorize("@ss.hasPermi('seal:base:export')")
    @Log(title = "密封点", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipSealBase equipSealBase)
    {
        List<EquipSealBase> list = equipSealBaseService.selectEquipSealBaseList(equipSealBase);
        ExcelUtil<EquipSealBase> util = new ExcelUtil<EquipSealBase>(EquipSealBase.class);
        util.exportExcel(response, list, "密封点数据");
    }

    /**
     * 获取密封点详细信息
     */
    @PreAuthorize("@ss.hasPermi('seal:base:query')")
    @GetMapping(value = "/{baseId}")
    public AjaxResult getInfo(@PathVariable("baseId") Long baseId)
    {
        return success(equipSealBaseService.selectEquipSealBaseByBaseId(baseId));
    }

    /**
     * 新增密封点
     */
    @PreAuthorize("@ss.hasPermi('seal:base:add')")
    @Log(title = "密封点", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipSealBase equipSealBase)
    {
        return toAjax(equipSealBaseService.insertEquipSealBase(equipSealBase));
    }

    /**
     * 修改密封点
     */
    @PreAuthorize("@ss.hasPermi('seal:base:edit')")
    @Log(title = "密封点", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipSealBase equipSealBase)
    {
        return toAjax(equipSealBaseService.updateEquipSealBase(equipSealBase));
    }

    /**
     * 删除密封点
     */
    @PreAuthorize("@ss.hasPermi('seal:base:remove')")
    @Log(title = "密封点", businessType = BusinessType.DELETE)
	@DeleteMapping("/{baseIds}")
    public AjaxResult remove(@PathVariable Long[] baseIds)
    {
        return toAjax(equipSealBaseService.deleteEquipSealBaseByBaseIds(baseIds));
    }
}
