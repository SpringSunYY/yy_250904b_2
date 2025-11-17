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
import com.ruoyi.contractor.domain.BizContractorManagement;
import com.ruoyi.contractor.service.IBizContractorManagementService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 承包商制度Controller
 * 
 * @author Laogao
 * @date 2025-10-23
 */
@RestController
@RequestMapping("/contractor/management")
public class BizContractorManagementController extends BaseController
{
    @Autowired
    private IBizContractorManagementService bizContractorManagementService;

    /**
     * 查询承包商制度列表
     */
    @PreAuthorize("@ss.hasPermi('contractor:management:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizContractorManagement bizContractorManagement)
    {
        startPage();
        List<BizContractorManagement> list = bizContractorManagementService.selectBizContractorManagementList(bizContractorManagement);
        return getDataTable(list);
    }

    /**
     * 导出承包商制度列表
     */
    @PreAuthorize("@ss.hasPermi('contractor:management:export')")
    @Log(title = "承包商制度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizContractorManagement bizContractorManagement)
    {
        List<BizContractorManagement> list = bizContractorManagementService.selectBizContractorManagementList(bizContractorManagement);
        ExcelUtil<BizContractorManagement> util = new ExcelUtil<BizContractorManagement>(BizContractorManagement.class);
        util.exportExcel(response, list, "承包商制度数据");
    }

    /**
     * 获取承包商制度详细信息
     */
    @PreAuthorize("@ss.hasPermi('contractor:management:query')")
    @GetMapping(value = "/{nanageId}")
    public AjaxResult getInfo(@PathVariable("nanageId") Long nanageId)
    {
        return success(bizContractorManagementService.selectBizContractorManagementByNanageId(nanageId));
    }

    /**
     * 新增承包商制度
     */
    @PreAuthorize("@ss.hasPermi('contractor:management:add')")
    @Log(title = "承包商制度", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizContractorManagement bizContractorManagement)
    {
        return toAjax(bizContractorManagementService.insertBizContractorManagement(bizContractorManagement));
    }

    /**
     * 修改承包商制度
     */
    @PreAuthorize("@ss.hasPermi('contractor:management:edit')")
    @Log(title = "承包商制度", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizContractorManagement bizContractorManagement)
    {
        return toAjax(bizContractorManagementService.updateBizContractorManagement(bizContractorManagement));
    }

    /**
     * 删除承包商制度
     */
    @PreAuthorize("@ss.hasPermi('contractor:management:remove')")
    @Log(title = "承包商制度", businessType = BusinessType.DELETE)
	@DeleteMapping("/{nanageIds}")
    public AjaxResult remove(@PathVariable Long[] nanageIds)
    {
        return toAjax(bizContractorManagementService.deleteBizContractorManagementByNanageIds(nanageIds));
    }
}
