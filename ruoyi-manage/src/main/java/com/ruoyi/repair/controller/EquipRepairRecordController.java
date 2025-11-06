package com.ruoyi.repair.controller;

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
import com.ruoyi.repair.domain.EquipRepairRecord;
import com.ruoyi.repair.service.IEquipRepairRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 检维修记录Controller
 * 
 * @author laogao
 * @date 2025-09-30
 */
@RestController
@RequestMapping("/repair/record")
public class EquipRepairRecordController extends BaseController
{
    @Autowired
    private IEquipRepairRecordService equipRepairRecordService;

    /**
     * 查询检维修记录列表
     */
    @PreAuthorize("@ss.hasPermi('repair:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipRepairRecord equipRepairRecord)
    {
        startPage();
        List<EquipRepairRecord> list = equipRepairRecordService.selectEquipRepairRecordList(equipRepairRecord);
        return getDataTable(list);
    }

    /**
     * 导出检维修记录列表
     */
    @PreAuthorize("@ss.hasPermi('repair:record:export')")
    @Log(title = "检维修记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipRepairRecord equipRepairRecord)
    {
        List<EquipRepairRecord> list = equipRepairRecordService.selectEquipRepairRecordList(equipRepairRecord);
        ExcelUtil<EquipRepairRecord> util = new ExcelUtil<EquipRepairRecord>(EquipRepairRecord.class);
        util.exportExcel(response, list, "检维修记录数据");
    }

    /**
     * 获取检维修记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('repair:record:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable("recordId") Long recordId)
    {
        return success(equipRepairRecordService.selectEquipRepairRecordByRecordId(recordId));
    }

    /**
     * 新增检维修记录
     */
    @PreAuthorize("@ss.hasPermi('repair:record:add')")
    @Log(title = "检维修记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipRepairRecord equipRepairRecord)
    {
        return toAjax(equipRepairRecordService.insertEquipRepairRecord(equipRepairRecord));
    }

    /**
     * 修改检维修记录
     */
    @PreAuthorize("@ss.hasPermi('repair:record:edit')")
    @Log(title = "检维修记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipRepairRecord equipRepairRecord)
    {
        return toAjax(equipRepairRecordService.updateEquipRepairRecord(equipRepairRecord));
    }

    /**
     * 删除检维修记录
     */
    @PreAuthorize("@ss.hasPermi('repair:record:remove')")
    @Log(title = "检维修记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{recordIds}")
    public AjaxResult remove(@PathVariable Long[] recordIds)
    {
        return toAjax(equipRepairRecordService.deleteEquipRepairRecordByRecordIds(recordIds));
    }
}
