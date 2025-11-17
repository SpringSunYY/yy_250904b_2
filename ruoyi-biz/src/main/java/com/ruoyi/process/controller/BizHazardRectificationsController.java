package com.ruoyi.process.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.ruoyi.process.domain.BizHazardRectifications;
import com.ruoyi.process.service.IBizHazardRectificationsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 隐患整改记录Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/process/rectifications")
public class BizHazardRectificationsController extends BaseController
{
    @Autowired
    private IBizHazardRectificationsService bizHazardRectificationsService;
    
    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    /**
     * 查询隐患整改记录列表
     */
    @PreAuthorize("@ss.hasPermi('process:rectifications:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizHazardRectifications bizHazardRectifications)
    {
        startPage();
        List<BizHazardRectifications> list = bizHazardRectificationsService.selectBizHazardRectificationsList(bizHazardRectifications);
        return getDataTable(list);
    }

    /**
     * 导出隐患整改记录列表
     */
    @PreAuthorize("@ss.hasPermi('process:rectifications:export')")
    @Log(title = "隐患整改记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizHazardRectifications bizHazardRectifications)
    {
        List<BizHazardRectifications> list = bizHazardRectificationsService.selectBizHazardRectificationsList(bizHazardRectifications);
        ExcelUtil<BizHazardRectifications> util = new ExcelUtil<BizHazardRectifications>(BizHazardRectifications.class);
        util.exportExcel(response, list, "隐患整改记录数据");
    }

    /**
     * 获取隐患整改记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:rectifications:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bizHazardRectificationsService.selectBizHazardRectificationsById(id));
    }

    /**
     * 新增隐患整改记录
     */
    @PreAuthorize("@ss.hasPermi('process:rectifications:add')")
    @Log(title = "隐患整改记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizHazardRectifications bizHazardRectifications)
    {
        try {
            return toAjax(bizHazardRectificationsService.insertBizHazardRectifications(bizHazardRectifications));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改隐患整改记录
     */
    @PreAuthorize("@ss.hasPermi('process:rectifications:edit')")
    @Log(title = "隐患整改记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizHazardRectifications bizHazardRectifications)
    {
        try {
            return toAjax(bizHazardRectificationsService.updateBizHazardRectifications(bizHazardRectifications));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除隐患整改记录
     */
    @PreAuthorize("@ss.hasPermi('process:rectifications:remove')")
    @Log(title = "隐患整改记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizHazardRectificationsService.deleteBizHazardRectificationsByIds(ids));
    }
}
