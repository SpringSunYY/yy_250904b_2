package com.ruoyi.lubr.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.ruoyi.lubr.domain.EquipLubrRecord2;
import com.ruoyi.lubr.service.IEquipLubrRecord2Service;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备润滑记录Controller
 * 
 * @author laogao
 * @date 2025-09-30
 */
@RestController
@RequestMapping("/lubr/record2")
public class EquipLubrRecord2Controller extends BaseController
{
    @Autowired
    private IEquipLubrRecord2Service equipLubrRecord2Service;

    /**
     * 查询设备润滑记录列表
     */
    @PreAuthorize("@ss.hasPermi('lubr:record2:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipLubrRecord2 equipLubrRecord2)
    {
        startPage();
        List<EquipLubrRecord2> list = equipLubrRecord2Service.selectEquipLubrRecord2List(equipLubrRecord2);
        return getDataTable(list);
    }

    /**
     * 导出设备润滑记录列表
     */
    @PreAuthorize("@ss.hasPermi('lubr:record2:export')")
    @Log(title = "设备润滑记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipLubrRecord2 equipLubrRecord2)
    {
        List<EquipLubrRecord2> list = equipLubrRecord2Service.selectEquipLubrRecord2List(equipLubrRecord2);
        ExcelUtil<EquipLubrRecord2> util = new ExcelUtil<EquipLubrRecord2>(EquipLubrRecord2.class);
        util.exportExcel(response, list, "设备润滑记录数据");
    }

    /**
     * 获取设备润滑记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('lubr:record2:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(equipLubrRecord2Service.selectEquipLubrRecord2ByRecordId(recordId));
    }

    /**
     * 新增设备润滑记录
     */
    @PreAuthorize("@ss.hasPermi('lubr:record2:add')")
    @Log(title = "设备润滑记录", businessType = BusinessType.INSERT)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult add(@RequestBody EquipLubrRecord2 equipLubrRecord2)
    {
        return toAjax(equipLubrRecord2Service.insertEquipLubrRecord2(equipLubrRecord2));
    }

    /**
     * 修改设备润滑记录
     */
    @PreAuthorize("@ss.hasPermi('lubr:record2:edit')")
    @Log(title = "设备润滑记录", businessType = BusinessType.UPDATE)
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public AjaxResult edit(@RequestBody EquipLubrRecord2 equipLubrRecord2)
    {
        return toAjax(equipLubrRecord2Service.updateEquipLubrRecord2(equipLubrRecord2));
    }

    /**
     * 删除设备润滑记录
     */
    @PreAuthorize("@ss.hasPermi('lubr:record2:remove')")
    @Log(title = "设备润滑记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(equipLubrRecord2Service.deleteEquipLubrRecord2ByRecordIds(recordIds));
    }
}
