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
import com.ruoyi.vulnerable.domain.EquipmentVulnerableInfo;
import com.ruoyi.vulnerable.service.IEquipmentVulnerableInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 易损设备信息Controller
 * 
 * @author Laogao
 * @date 2025-11-05
 */
@RestController
@RequestMapping("/vulnerable/info")
public class EquipmentVulnerableInfoController extends BaseController
{
    @Autowired
    private IEquipmentVulnerableInfoService equipmentVulnerableInfoService;

    /**
     * 查询易损设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipmentVulnerableInfo equipmentVulnerableInfo)
    {
        startPage();
        List<EquipmentVulnerableInfo> list = equipmentVulnerableInfoService.selectEquipmentVulnerableInfoList(equipmentVulnerableInfo);
        return getDataTable(list);
    }

    /**
     * 导出易损设备信息列表
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:info:export')")
    @Log(title = "易损设备信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentVulnerableInfo equipmentVulnerableInfo)
    {
        List<EquipmentVulnerableInfo> list = equipmentVulnerableInfoService.selectEquipmentVulnerableInfoList(equipmentVulnerableInfo);
        ExcelUtil<EquipmentVulnerableInfo> util = new ExcelUtil<EquipmentVulnerableInfo>(EquipmentVulnerableInfo.class);
        util.exportExcel(response, list, "易损设备信息数据");
    }

    /**
     * 获取易损设备信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipmentVulnerableInfoService.selectEquipmentVulnerableInfoById(id));
    }

    /**
     * 新增易损设备信息
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:info:add')")
    @Log(title = "易损设备信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentVulnerableInfo equipmentVulnerableInfo)
    {
        return toAjax(equipmentVulnerableInfoService.insertEquipmentVulnerableInfo(equipmentVulnerableInfo));
    }

    /**
     * 修改易损设备信息
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:info:edit')")
    @Log(title = "易损设备信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentVulnerableInfo equipmentVulnerableInfo)
    {
        return toAjax(equipmentVulnerableInfoService.updateEquipmentVulnerableInfo(equipmentVulnerableInfo));
    }

    /**
     * 删除易损设备信息
     */
    @PreAuthorize("@ss.hasPermi('vulnerable:info:remove')")
    @Log(title = "易损设备信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipmentVulnerableInfoService.deleteEquipmentVulnerableInfoByIds(ids));
    }
}
