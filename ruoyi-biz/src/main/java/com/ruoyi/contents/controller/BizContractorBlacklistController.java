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
import com.ruoyi.contents.domain.BizContractorBlacklist;
import com.ruoyi.contents.service.IBizContractorBlacklistService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 承包商黑名单Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/contents/blacklist")
public class BizContractorBlacklistController extends BaseController
{
    @Autowired
    private IBizContractorBlacklistService bizContractorBlacklistService;

    /**
     * 查询承包商黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('contents:blacklist:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizContractorBlacklist bizContractorBlacklist)
    {
        startPage();
        List<BizContractorBlacklist> list = bizContractorBlacklistService.selectBizContractorBlacklistList(bizContractorBlacklist);
        return getDataTable(list);
    }

    /**
     * 导出承包商黑名单列表
     */
    @PreAuthorize("@ss.hasPermi('contents:blacklist:export')")
    @Log(title = "承包商黑名单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizContractorBlacklist bizContractorBlacklist)
    {
        List<BizContractorBlacklist> list = bizContractorBlacklistService.selectBizContractorBlacklistList(bizContractorBlacklist);
        ExcelUtil<BizContractorBlacklist> util = new ExcelUtil<BizContractorBlacklist>(BizContractorBlacklist.class);
        util.exportExcel(response, list, "承包商黑名单数据");
    }

    /**
     * 获取承包商黑名单详细信息
     */
    @PreAuthorize("@ss.hasPermi('contents:blacklist:query')")
    @GetMapping(value = "/{blackId}")
    public AjaxResult getInfo(@PathVariable("blackId") Long blackId)
    {
        return success(bizContractorBlacklistService.selectBizContractorBlacklistByBlackId(blackId));
    }

    /**
     * 新增承包商黑名单
     */
    @PreAuthorize("@ss.hasPermi('contents:blacklist:add')")
    @Log(title = "承包商黑名单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizContractorBlacklist bizContractorBlacklist)
    {
        return toAjax(bizContractorBlacklistService.insertBizContractorBlacklist(bizContractorBlacklist));
    }

    /**
     * 修改承包商黑名单
     */
    @PreAuthorize("@ss.hasPermi('contents:blacklist:edit')")
    @Log(title = "承包商黑名单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizContractorBlacklist bizContractorBlacklist)
    {
        return toAjax(bizContractorBlacklistService.updateBizContractorBlacklist(bizContractorBlacklist));
    }

    /**
     * 删除承包商黑名单
     */
    @PreAuthorize("@ss.hasPermi('contents:blacklist:remove')")
    @Log(title = "承包商黑名单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{blackIds}")
    public AjaxResult remove(@PathVariable Long[] blackIds)
    {
        return toAjax(bizContractorBlacklistService.deleteBizContractorBlacklistByBlackIds(blackIds));
    }
}
