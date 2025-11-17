package com.ruoyi.contents.controller;

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
import com.ruoyi.contents.domain.BizContractorQualified;
import com.ruoyi.contents.service.IBizContractorQualifiedService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合格承包商Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/contents/qualified")
public class BizContractorQualifiedController extends BaseController
{
    @Autowired
    private IBizContractorQualifiedService bizContractorQualifiedService;

    /**
     * 查询合格承包商列表
     */
    @PreAuthorize("@ss.hasPermi('contents:qualified:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizContractorQualified bizContractorQualified)
    {
        startPage();
        List<BizContractorQualified> list = bizContractorQualifiedService.selectBizContractorQualifiedList(bizContractorQualified);
        return getDataTable(list);
    }

    /**
     * 导出合格承包商列表
     */
    @PreAuthorize("@ss.hasPermi('contents:qualified:export')")
    @Log(title = "合格承包商", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizContractorQualified bizContractorQualified)
    {
        List<BizContractorQualified> list = bizContractorQualifiedService.selectBizContractorQualifiedList(bizContractorQualified);
        ExcelUtil<BizContractorQualified> util = new ExcelUtil<BizContractorQualified>(BizContractorQualified.class);
        util.exportExcel(response, list, "合格承包商数据");
    }

    /**
     * 获取合格承包商详细信息
     */
    @PreAuthorize("@ss.hasPermi('contents:qualified:query')")
    @GetMapping(value = "/{qualId}")
    public AjaxResult getInfo(@PathVariable("qualId") Long qualId)
    {
        return success(bizContractorQualifiedService.selectBizContractorQualifiedByQualId(qualId));
    }

    /**
     * 新增合格承包商
     */
    @PreAuthorize("@ss.hasPermi('contents:qualified:add')")
    @Log(title = "合格承包商", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizContractorQualified bizContractorQualified)
    {
        return toAjax(bizContractorQualifiedService.insertBizContractorQualified(bizContractorQualified));
    }

    /**
     * 修改合格承包商
     */
    @PreAuthorize("@ss.hasPermi('contents:qualified:edit')")
    @Log(title = "合格承包商", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizContractorQualified bizContractorQualified)
    {
        return toAjax(bizContractorQualifiedService.updateBizContractorQualified(bizContractorQualified));
    }

    /**
     * 删除合格承包商
     */
    @PreAuthorize("@ss.hasPermi('contents:qualified:remove')")
    @Log(title = "合格承包商", businessType = BusinessType.DELETE)
	@DeleteMapping("/{qualIds}")
    public AjaxResult remove(@PathVariable Long[] qualIds)
    {
        return toAjax(bizContractorQualifiedService.deleteBizContractorQualifiedByQualIds(qualIds));
    }
}
