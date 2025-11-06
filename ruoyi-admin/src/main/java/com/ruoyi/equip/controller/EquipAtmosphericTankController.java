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
import com.ruoyi.equip.domain.EquipAtmosphericTank;
import com.ruoyi.equip.service.IEquipAtmosphericTankService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 常压储罐检查Controller
 * 
 * @author Laogao
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/equip/tank")
public class EquipAtmosphericTankController extends BaseController
{
    @Autowired
    private IEquipAtmosphericTankService equipAtmosphericTankService;

    /**
     * 查询常压储罐检查列表
     */
    @PreAuthorize("@ss.hasPermi('equip:tank:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipAtmosphericTank equipAtmosphericTank)
    {
        startPage();
        List<EquipAtmosphericTank> list = equipAtmosphericTankService.selectEquipAtmosphericTankList(equipAtmosphericTank);
        return getDataTable(list);
    }

    /**
     * 导出常压储罐检查列表
     */
    @PreAuthorize("@ss.hasPermi('equip:tank:export')")
    @Log(title = "常压储罐检查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipAtmosphericTank equipAtmosphericTank)
    {
        List<EquipAtmosphericTank> list = equipAtmosphericTankService.selectEquipAtmosphericTankList(equipAtmosphericTank);
        ExcelUtil<EquipAtmosphericTank> util = new ExcelUtil<EquipAtmosphericTank>(EquipAtmosphericTank.class);
        util.exportExcel(response, list, "常压储罐检查数据");
    }

    /**
     * 获取常压储罐检查详细信息
     */
    @PreAuthorize("@ss.hasPermi('equip:tank:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipAtmosphericTankService.selectEquipAtmosphericTankById(id));
    }

    /**
     * 新增常压储罐检查
     */
    @PreAuthorize("@ss.hasPermi('equip:tank:add')")
    @Log(title = "常压储罐检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipAtmosphericTank equipAtmosphericTank)
    {
        return toAjax(equipAtmosphericTankService.insertEquipAtmosphericTank(equipAtmosphericTank));
    }

    /**
     * 修改常压储罐检查
     */
    @PreAuthorize("@ss.hasPermi('equip:tank:edit')")
    @Log(title = "常压储罐检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipAtmosphericTank equipAtmosphericTank)
    {
        return toAjax(equipAtmosphericTankService.updateEquipAtmosphericTank(equipAtmosphericTank));
    }

    /**
     * 删除常压储罐检查
     */
    @PreAuthorize("@ss.hasPermi('equip:tank:remove')")
    @Log(title = "常压储罐检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipAtmosphericTankService.deleteEquipAtmosphericTankByIds(ids));
    }
}
