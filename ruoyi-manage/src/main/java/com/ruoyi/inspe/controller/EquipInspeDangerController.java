package com.ruoyi.inspe.controller;

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
import com.ruoyi.inspe.domain.EquipInspeDanger;
import com.ruoyi.inspe.service.IEquipInspeDangerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备隐患治理Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/inspe/danger")
public class EquipInspeDangerController extends BaseController
{
    @Autowired
    private IEquipInspeDangerService equipInspeDangerService;

    /**
     * 查询设备隐患治理列表
     */
    @PreAuthorize("@ss.hasPermi('inspe:danger:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipInspeDanger equipInspeDanger)
    {
        startPage();
        List<EquipInspeDanger> list = equipInspeDangerService.selectEquipInspeDangerList(equipInspeDanger);
        return getDataTable(list);
    }

    /**
     * 导出设备隐患治理列表
     */
    @PreAuthorize("@ss.hasPermi('inspe:danger:export')")
    @Log(title = "设备隐患治理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipInspeDanger equipInspeDanger)
    {
        List<EquipInspeDanger> list = equipInspeDangerService.selectEquipInspeDangerList(equipInspeDanger);
        ExcelUtil<EquipInspeDanger> util = new ExcelUtil<EquipInspeDanger>(EquipInspeDanger.class);
        util.exportExcel(response, list, "设备隐患治理数据");
    }

    /**
     * 获取设备隐患治理详细信息
     */
    @PreAuthorize("@ss.hasPermi('inspe:danger:query')")
    @GetMapping(value = "/{dangerId}")
    public AjaxResult getInfo(@PathVariable("dangerId") Long dangerId)
    {
        return success(equipInspeDangerService.selectEquipInspeDangerByDangerId(dangerId));
    }

    /**
     * 新增设备隐患治理
     */
    @PreAuthorize("@ss.hasPermi('inspe:danger:add')")
    @Log(title = "设备隐患治理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipInspeDanger equipInspeDanger)
    {
        return toAjax(equipInspeDangerService.insertEquipInspeDanger(equipInspeDanger));
    }

    /**
     * 修改设备隐患治理
     */
    @PreAuthorize("@ss.hasPermi('inspe:danger:edit')")
    @Log(title = "设备隐患治理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipInspeDanger equipInspeDanger)
    {
        return toAjax(equipInspeDangerService.updateEquipInspeDanger(equipInspeDanger));
    }

    /**
     * 删除设备隐患治理
     */
    @PreAuthorize("@ss.hasPermi('inspe:danger:remove')")
    @Log(title = "设备隐患治理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{dangerIds}")
    public AjaxResult remove(@PathVariable Long[] dangerIds)
    {
        return toAjax(equipInspeDangerService.deleteEquipInspeDangerByDangerIds(dangerIds));
    }
}
