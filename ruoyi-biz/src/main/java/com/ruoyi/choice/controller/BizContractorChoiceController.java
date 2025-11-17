package com.ruoyi.choice.controller;

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
import com.ruoyi.choice.domain.BizContractorChoice;
import com.ruoyi.choice.service.IBizContractorChoiceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 承包商选择Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/choice/choice")
public class BizContractorChoiceController extends BaseController
{
    @Autowired
    private IBizContractorChoiceService bizContractorChoiceService;

    /**
     * 查询承包商选择列表
     */
    @PreAuthorize("@ss.hasPermi('choice:choice:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizContractorChoice bizContractorChoice)
    {
        startPage();
        List<BizContractorChoice> list = bizContractorChoiceService.selectBizContractorChoiceList(bizContractorChoice);
        return getDataTable(list);
    }

    /**
     * 导出承包商选择列表
     */
    @PreAuthorize("@ss.hasPermi('choice:choice:export')")
    @Log(title = "承包商选择", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizContractorChoice bizContractorChoice)
    {
        List<BizContractorChoice> list = bizContractorChoiceService.selectBizContractorChoiceList(bizContractorChoice);
        ExcelUtil<BizContractorChoice> util = new ExcelUtil<BizContractorChoice>(BizContractorChoice.class);
        util.exportExcel(response, list, "承包商选择数据");
    }

    /**
     * 获取承包商选择详细信息
     */
    @PreAuthorize("@ss.hasPermi('choice:choice:query')")
    @GetMapping(value = "/{choiceId}")
    public AjaxResult getInfo(@PathVariable("choiceId") Long choiceId)
    {
        return success(bizContractorChoiceService.selectBizContractorChoiceByChoiceId(choiceId));
    }

    /**
     * 新增承包商选择
     */
    @PreAuthorize("@ss.hasPermi('choice:choice:add')")
    @Log(title = "承包商选择", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizContractorChoice bizContractorChoice)
    {
        return toAjax(bizContractorChoiceService.insertBizContractorChoice(bizContractorChoice));
    }

    /**
     * 修改承包商选择
     */
    @PreAuthorize("@ss.hasPermi('choice:choice:edit')")
    @Log(title = "承包商选择", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizContractorChoice bizContractorChoice)
    {
        return toAjax(bizContractorChoiceService.updateBizContractorChoice(bizContractorChoice));
    }

    /**
     * 删除承包商选择
     */
    @PreAuthorize("@ss.hasPermi('choice:choice:remove')")
    @Log(title = "承包商选择", businessType = BusinessType.DELETE)
	@DeleteMapping("/{choiceIds}")
    public AjaxResult remove(@PathVariable Long[] choiceIds)
    {
        return toAjax(bizContractorChoiceService.deleteBizContractorChoiceByChoiceIds(choiceIds));
    }
}
