package com.ruoyi.training.controller;

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
import com.ruoyi.training.domain.BizTrainingRecords;
import com.ruoyi.training.service.IBizTrainingRecordsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 培训记录Controller
 * 
 * @author ruoyi
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/training/records")
public class BizTrainingRecordsController extends BaseController
{
    @Autowired
    private IBizTrainingRecordsService bizTrainingRecordsService;

    /**
     * 查询培训记录列表
     */
    @PreAuthorize("@ss.hasPermi('training:records:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizTrainingRecords bizTrainingRecords)
    {
        startPage();
        List<BizTrainingRecords> list = bizTrainingRecordsService.selectBizTrainingRecordsList(bizTrainingRecords);
        return getDataTable(list);
    }

    /**
     * 导出培训记录列表
     */
    @PreAuthorize("@ss.hasPermi('training:records:export')")
    @Log(title = "培训记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizTrainingRecords bizTrainingRecords)
    {
        List<BizTrainingRecords> list = bizTrainingRecordsService.selectBizTrainingRecordsList(bizTrainingRecords);
        ExcelUtil<BizTrainingRecords> util = new ExcelUtil<BizTrainingRecords>(BizTrainingRecords.class);
        util.exportExcel(response, list, "培训记录数据");
    }

    /**
     * 获取培训记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('training:records:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bizTrainingRecordsService.selectBizTrainingRecordsById(id));
    }

    /**
     * 新增培训记录
     */
    @PreAuthorize("@ss.hasPermi('training:records:add')")
    @Log(title = "培训记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizTrainingRecords bizTrainingRecords)
    {
        return toAjax(bizTrainingRecordsService.insertBizTrainingRecords(bizTrainingRecords));
    }

    /**
     * 修改培训记录
     */
    @PreAuthorize("@ss.hasPermi('training:records:edit')")
    @Log(title = "培训记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizTrainingRecords bizTrainingRecords)
    {
        return toAjax(bizTrainingRecordsService.updateBizTrainingRecords(bizTrainingRecords));
    }

    /**
     * 删除培训记录
     */
    @PreAuthorize("@ss.hasPermi('training:records:remove')")
    @Log(title = "培训记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizTrainingRecordsService.deleteBizTrainingRecordsByIds(ids));
    }
}
