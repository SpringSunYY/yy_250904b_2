package com.ruoyi.personnel.controller;

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
import com.ruoyi.personnel.domain.BizSpecialOperations;
import com.ruoyi.personnel.service.IBizSpecialOperationsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 特种人员台账Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/personnel/operations")
public class BizSpecialOperationsController extends BaseController
{
    @Autowired
    private IBizSpecialOperationsService bizSpecialOperationsService;

    /**
     * 查询特种人员台账列表
     */
    @PreAuthorize("@ss.hasPermi('personnel:operations:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSpecialOperations bizSpecialOperations)
    {
        startPage();
        List<BizSpecialOperations> list = bizSpecialOperationsService.selectBizSpecialOperationsList(bizSpecialOperations);
        return getDataTable(list);
    }

    /**
     * 导出特种人员台账列表
     */
    @PreAuthorize("@ss.hasPermi('personnel:operations:export')")
    @Log(title = "特种人员台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSpecialOperations bizSpecialOperations)
    {
        List<BizSpecialOperations> list = bizSpecialOperationsService.selectBizSpecialOperationsList(bizSpecialOperations);
        ExcelUtil<BizSpecialOperations> util = new ExcelUtil<BizSpecialOperations>(BizSpecialOperations.class);
        util.exportExcel(response, list, "特种人员台账数据");
    }

    /**
     * 获取特种人员台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('personnel:operations:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bizSpecialOperationsService.selectBizSpecialOperationsById(id));
    }

    /**
     * 新增特种人员台账
     */
    @PreAuthorize("@ss.hasPermi('personnel:operations:add')")
    @Log(title = "特种人员台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSpecialOperations bizSpecialOperations)
    {
        return toAjax(bizSpecialOperationsService.insertBizSpecialOperations(bizSpecialOperations));
    }

    /**
     * 修改特种人员台账
     */
    @PreAuthorize("@ss.hasPermi('personnel:operations:edit')")
    @Log(title = "特种人员台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSpecialOperations bizSpecialOperations)
    {
        return toAjax(bizSpecialOperationsService.updateBizSpecialOperations(bizSpecialOperations));
    }

    /**
     * 删除特种人员台账
     */
    @PreAuthorize("@ss.hasPermi('personnel:operations:remove')")
    @Log(title = "特种人员台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizSpecialOperationsService.deleteBizSpecialOperationsByIds(ids));
    }
}
