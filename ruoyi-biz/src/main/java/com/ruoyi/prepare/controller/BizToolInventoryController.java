package com.ruoyi.prepare.controller;

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
import com.ruoyi.prepare.domain.BizToolInventory;
import com.ruoyi.prepare.service.IBizToolInventoryService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 工器具检查Controller
 *
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/prepare/inventory")
public class BizToolInventoryController extends BaseController
{
    @Autowired
    private IBizToolInventoryService bizToolInventoryService;

    /**
     * 查询工器具检查列表
     */
    @PreAuthorize("@ss.hasPermi('prepare:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizToolInventory bizToolInventory)
    {
        startPage();
        List<BizToolInventory> list = bizToolInventoryService.selectBizToolInventoryList(bizToolInventory);
        return getDataTable(list);
    }

    /**
     * 导出工器具检查列表
     */
    @PreAuthorize("@ss.hasPermi('prepare:inventory:export')")
    @Log(title = "工器具检查", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizToolInventory bizToolInventory)
    {
        List<BizToolInventory> list = bizToolInventoryService.selectBizToolInventoryList(bizToolInventory);
        ExcelUtil<BizToolInventory> util = new ExcelUtil<BizToolInventory>(BizToolInventory.class);
        util.exportExcel(response, list, "工器具检查数据");
    }

    /**
     * 获取工器具检查详细信息
     */
    @PreAuthorize("@ss.hasPermi('prepare:inventory:query')")
    @GetMapping(value = "/{toolId}")
    public AjaxResult getInfo(@PathVariable("toolId") Long toolId)
    {
        return success(bizToolInventoryService.selectBizToolInventoryByToolId(toolId));
    }

    // 用于存储每年的计数器，key为年份，value为该年份的计数器
    private static final Map<String, AtomicInteger> YEAR_COUNTER_MAP = new ConcurrentHashMap<>();

    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    // 生成工器具编号
    private synchronized String generateToolCode() {
        String year = getCurrentYear();
        AtomicInteger counter = YEAR_COUNTER_MAP.computeIfAbsent(year, k -> new AtomicInteger(0));
        int count = counter.incrementAndGet();

        // 如果超过9999，则重置为1
        if (count > 9999) {
            counter.set(1);
            count = 1;
        }

        return String.format("GJ%s%04d", year, count);
    }

    /**
     * 新增工器具检查
     */
    @PreAuthorize("@ss.hasPermi('prepare:inventory:add')")
    @Log(title = "工器具检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizToolInventory bizToolInventory)
    {
        // 自动生成工器具编号
        if (bizToolInventory.getToolCode() == null || bizToolInventory.getToolCode().isEmpty()) {
            String toolCode = generateToolCode();
            bizToolInventory.setToolCode(toolCode);
        }
        return toAjax(bizToolInventoryService.insertBizToolInventory(bizToolInventory));
    }

    /**
     * 修改工器具检查
     */
    @PreAuthorize("@ss.hasPermi('prepare:inventory:edit')")
    @Log(title = "工器具检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizToolInventory bizToolInventory)
    {
        return toAjax(bizToolInventoryService.updateBizToolInventory(bizToolInventory));
    }

    /**
     * 删除工器具检查
     */
    @PreAuthorize("@ss.hasPermi('prepare:inventory:remove')")
    @Log(title = "工器具检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{toolIds}")
    public AjaxResult remove(@PathVariable Long[] toolIds)
    {
        return toAjax(bizToolInventoryService.deleteBizToolInventoryByToolIds(toolIds));
    }
}
