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
import com.ruoyi.process.domain.BizProjectChange;
import com.ruoyi.process.service.IBizProjectChangeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 项目变更管理Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/process/change")
public class BizProjectChangeController extends BaseController
{
    @Autowired
    private IBizProjectChangeService bizProjectChangeService;

    /**
     * 查询项目变更管理列表
     */
    @PreAuthorize("@ss.hasPermi('process:change:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProjectChange bizProjectChange)
    {
        startPage();
        List<BizProjectChange> list = bizProjectChangeService.selectBizProjectChangeList(bizProjectChange);
        return getDataTable(list);
    }

    /**
     * 导出项目变更管理列表
     */
    @PreAuthorize("@ss.hasPermi('process:change:export')")
    @Log(title = "项目变更管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizProjectChange bizProjectChange)
    {
        List<BizProjectChange> list = bizProjectChangeService.selectBizProjectChangeList(bizProjectChange);
        ExcelUtil<BizProjectChange> util = new ExcelUtil<BizProjectChange>(BizProjectChange.class);
        util.exportExcel(response, list, "项目变更管理数据");
    }

    /**
     * 获取项目变更管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:change:query')")
    @GetMapping(value = "/{changeId}")
    public AjaxResult getInfo(@PathVariable("changeId") Long changeId)
    {
        return success(bizProjectChangeService.selectBizProjectChangeByChangeId(changeId));
    }

    /**
     * 新增项目变更管理
     */
    @PreAuthorize("@ss.hasPermi('process:change:add')")
    @Log(title = "项目变更管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProjectChange bizProjectChange)
    {
        return toAjax(bizProjectChangeService.insertBizProjectChange(bizProjectChange));
    }

    /**
     * 修改项目变更管理
     */
    @PreAuthorize("@ss.hasPermi('process:change:edit')")
    @Log(title = "项目变更管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProjectChange bizProjectChange)
    {
        return toAjax(bizProjectChangeService.updateBizProjectChange(bizProjectChange));
    }

    /**
     * 删除项目变更管理
     */
    @PreAuthorize("@ss.hasPermi('process:change:remove')")
    @Log(title = "项目变更管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{changeIds}")
    public AjaxResult remove(@PathVariable Long[] changeIds)
    {
        return toAjax(bizProjectChangeService.deleteBizProjectChangeByChangeIds(changeIds));
    }
}
