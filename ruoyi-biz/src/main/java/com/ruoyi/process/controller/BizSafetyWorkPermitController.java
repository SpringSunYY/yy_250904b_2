package com.ruoyi.process.controller;

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
import com.ruoyi.process.domain.BizSafetyWorkPermit;
import com.ruoyi.process.service.IBizSafetyWorkPermitService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 特殊作业管理Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/process/permit")
public class BizSafetyWorkPermitController extends BaseController
{
    @Autowired
    private IBizSafetyWorkPermitService bizSafetyWorkPermitService;

    /**
     * 查询特殊作业管理列表
     */
    @PreAuthorize("@ss.hasPermi('process:permit:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSafetyWorkPermit bizSafetyWorkPermit)
    {
        startPage();
        List<BizSafetyWorkPermit> list = bizSafetyWorkPermitService.selectBizSafetyWorkPermitList(bizSafetyWorkPermit);
        return getDataTable(list);
    }

    /**
     * 导出特殊作业管理列表
     */
    @PreAuthorize("@ss.hasPermi('process:permit:export')")
    @Log(title = "特殊作业管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSafetyWorkPermit bizSafetyWorkPermit)
    {
        List<BizSafetyWorkPermit> list = bizSafetyWorkPermitService.selectBizSafetyWorkPermitList(bizSafetyWorkPermit);
        ExcelUtil<BizSafetyWorkPermit> util = new ExcelUtil<BizSafetyWorkPermit>(BizSafetyWorkPermit.class);
        util.exportExcel(response, list, "特殊作业管理数据");
    }

    /**
     * 获取特殊作业管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:permit:query')")
    @GetMapping(value = "/{permitId}")
    public AjaxResult getInfo(@PathVariable("permitId") Long permitId)
    {
        return success(bizSafetyWorkPermitService.selectBizSafetyWorkPermitByPermitId(permitId));
    }

    /**
     * 新增特殊作业管理
     */
    @PreAuthorize("@ss.hasPermi('process:permit:add')")
    @Log(title = "特殊作业管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSafetyWorkPermit bizSafetyWorkPermit)
    {
        return toAjax(bizSafetyWorkPermitService.insertBizSafetyWorkPermit(bizSafetyWorkPermit));
    }

    /**
     * 修改特殊作业管理
     */
    @PreAuthorize("@ss.hasPermi('process:permit:edit')")
    @Log(title = "特殊作业管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSafetyWorkPermit bizSafetyWorkPermit)
    {
        return toAjax(bizSafetyWorkPermitService.updateBizSafetyWorkPermit(bizSafetyWorkPermit));
    }

    /**
     * 删除特殊作业管理
     */
    @PreAuthorize("@ss.hasPermi('process:permit:remove')")
    @Log(title = "特殊作业管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{permitIds}")
    public AjaxResult remove(@PathVariable Long[] permitIds)
    {
        return toAjax(bizSafetyWorkPermitService.deleteBizSafetyWorkPermitByPermitIds(permitIds));
    }
}
