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
import com.ruoyi.inspe.domain.EquipInspe;
import com.ruoyi.inspe.service.IEquipInspeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备检查记录Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/inspe/inspe")
public class EquipInspeController extends BaseController
{
    @Autowired
    private IEquipInspeService equipInspeService;

    /**
     * 查询设备检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('inspe:inspe:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipInspe equipInspe)
    {
        startPage();
        List<EquipInspe> list = equipInspeService.selectEquipInspeList(equipInspe);
        return getDataTable(list);
    }

    /**
     * 导出设备检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('inspe:inspe:export')")
    @Log(title = "设备检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipInspe equipInspe)
    {
        List<EquipInspe> list = equipInspeService.selectEquipInspeList(equipInspe);
        ExcelUtil<EquipInspe> util = new ExcelUtil<EquipInspe>(EquipInspe.class);
        util.exportExcel(response, list, "设备检查记录数据");
    }

    /**
     * 获取设备检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('inspe:inspe:query')")
    @GetMapping(value = "/{inspeId}")
    public AjaxResult getInfo(@PathVariable("inspeId") Long inspeId)
    {
        return success(equipInspeService.selectEquipInspeByInspeId(inspeId));
    }

    /**
     * 新增设备检查记录
     */
    @PreAuthorize("@ss.hasPermi('inspe:inspe:add')")
    @Log(title = "设备检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipInspe equipInspe)
    {
        return toAjax(equipInspeService.insertEquipInspe(equipInspe));
    }

    /**
     * 修改设备检查记录
     */
    @PreAuthorize("@ss.hasPermi('inspe:inspe:edit')")
    @Log(title = "设备检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipInspe equipInspe)
    {
        return toAjax(equipInspeService.updateEquipInspe(equipInspe));
    }

    /**
     * 删除设备检查记录
     */
    @PreAuthorize("@ss.hasPermi('inspe:inspe:remove')")
    @Log(title = "设备检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{inspeIds}")
    public AjaxResult remove(@PathVariable Long[] inspeIds)
    {
        return toAjax(equipInspeService.deleteEquipInspeByInspeIds(inspeIds));
    }
}
