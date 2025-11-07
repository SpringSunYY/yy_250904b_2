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
import com.ruoyi.special.domain.EquipPressureVessel;
import com.ruoyi.special.service.IEquipPressureVesselService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 压力容器Controller
 * 
 * @author Laogao
 * @date 2025-11-04
 */
@RestController
@RequestMapping("/special/vessel")
public class EquipPressureVesselController extends BaseController
{
    @Autowired
    private IEquipPressureVesselService equipPressureVesselService;

    /**
     * 查询压力容器列表
     */
    @PreAuthorize("@ss.hasPermi('special:vessel:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipPressureVessel equipPressureVessel)
    {
        startPage();
        List<EquipPressureVessel> list = equipPressureVesselService.selectEquipPressureVesselList(equipPressureVessel);
        return getDataTable(list);
    }

    /**
     * 导出压力容器列表
     */
    @PreAuthorize("@ss.hasPermi('special:vessel:export')")
    @Log(title = "压力容器", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipPressureVessel equipPressureVessel)
    {
        List<EquipPressureVessel> list = equipPressureVesselService.selectEquipPressureVesselList(equipPressureVessel);
        ExcelUtil<EquipPressureVessel> util = new ExcelUtil<EquipPressureVessel>(EquipPressureVessel.class);
        util.exportExcel(response, list, "压力容器数据");
    }

    /**
     * 获取压力容器详细信息
     */
    @PreAuthorize("@ss.hasPermi('special:vessel:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipPressureVesselService.selectEquipPressureVesselById(id));
    }

    /**
     * 新增压力容器
     */
    @PreAuthorize("@ss.hasPermi('special:vessel:add')")
    @Log(title = "压力容器", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipPressureVessel equipPressureVessel)
    {
        return toAjax(equipPressureVesselService.insertEquipPressureVessel(equipPressureVessel));
    }

    /**
     * 修改压力容器
     */
    @PreAuthorize("@ss.hasPermi('special:vessel:edit')")
    @Log(title = "压力容器", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipPressureVessel equipPressureVessel)
    {
        return toAjax(equipPressureVesselService.updateEquipPressureVessel(equipPressureVessel));
    }

    /**
     * 删除压力容器
     */
    @PreAuthorize("@ss.hasPermi('special:vessel:remove')")
    @Log(title = "压力容器", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipPressureVesselService.deleteEquipPressureVesselByIds(ids));
    }
}
