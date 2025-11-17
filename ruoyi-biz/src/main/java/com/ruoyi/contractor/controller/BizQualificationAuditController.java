package com.ruoyi.contractor.controller;

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
import com.ruoyi.contractor.domain.BizQualificationAudit;
import com.ruoyi.contractor.service.IBizQualificationAuditService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 承包商资格审查Controller
 * 
 * @author Laogao
 * @date 2025-10-23
 */
@RestController
@RequestMapping("/contractor/audit")
public class BizQualificationAuditController extends BaseController
{
    @Autowired
    private IBizQualificationAuditService bizQualificationAuditService;

    /**
     * 查询承包商资格审查列表
     */
    @PreAuthorize("@ss.hasPermi('contractor:audit:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizQualificationAudit bizQualificationAudit)
    {
        startPage();
        List<BizQualificationAudit> list = bizQualificationAuditService.selectBizQualificationAuditList(bizQualificationAudit);
        return getDataTable(list);
    }

    /**
     * 导出承包商资格审查列表
     */
    @PreAuthorize("@ss.hasPermi('contractor:audit:export')")
    @Log(title = "承包商资格审查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizQualificationAudit bizQualificationAudit)
    {
        List<BizQualificationAudit> list = bizQualificationAuditService.selectBizQualificationAuditList(bizQualificationAudit);
        ExcelUtil<BizQualificationAudit> util = new ExcelUtil<BizQualificationAudit>(BizQualificationAudit.class);
        util.exportExcel(response, list, "承包商资格审查数据");
    }

    /**
     * 获取承包商资格审查详细信息
     */
    @PreAuthorize("@ss.hasPermi('contractor:audit:query')")
    @GetMapping(value = "/{auditId}")
    public AjaxResult getInfo(@PathVariable("auditId") Long auditId)
    {
        return success(bizQualificationAuditService.selectBizQualificationAuditByAuditId(auditId));
    }

    /**
     * 新增承包商资格审查
     */
    @PreAuthorize("@ss.hasPermi('contractor:audit:add')")
    @Log(title = "承包商资格审查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizQualificationAudit bizQualificationAudit)
    {
        return toAjax(bizQualificationAuditService.insertBizQualificationAudit(bizQualificationAudit));
    }

    /**
     * 修改承包商资格审查
     */
    @PreAuthorize("@ss.hasPermi('contractor:audit:edit')")
    @Log(title = "承包商资格审查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizQualificationAudit bizQualificationAudit)
    {
        return toAjax(bizQualificationAuditService.updateBizQualificationAudit(bizQualificationAudit));
    }

    /**
     * 删除承包商资格审查
     */
    @PreAuthorize("@ss.hasPermi('contractor:audit:remove')")
    @Log(title = "承包商资格审查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{auditIds}")
    public AjaxResult remove(@PathVariable Long[] auditIds)
    {
        return toAjax(bizQualificationAuditService.deleteBizQualificationAuditByAuditIds(auditIds));
    }
}
