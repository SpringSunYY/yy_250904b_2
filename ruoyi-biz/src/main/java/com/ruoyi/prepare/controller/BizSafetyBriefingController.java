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
import com.ruoyi.prepare.domain.BizSafetyBriefing;
import com.ruoyi.prepare.service.IBizSafetyBriefingService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 安全技术交底Controller
 *
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/prepare/briefing")
public class BizSafetyBriefingController extends BaseController
{
    @Autowired
    private IBizSafetyBriefingService bizSafetyBriefingService;

    // 用于存储每年的计数器，key为年份，value为该年份的计数器
    private static final Map<String, AtomicInteger> YEAR_COUNTER_MAP = new ConcurrentHashMap<>();

    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }

    /**
     * 查询安全技术交底列表
     */
    @PreAuthorize("@ss.hasPermi('prepare:briefing:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSafetyBriefing bizSafetyBriefing)
    {
        startPage();
        List<BizSafetyBriefing> list = bizSafetyBriefingService.selectBizSafetyBriefingList(bizSafetyBriefing);
        return getDataTable(list);
    }

    /**
     * 导出安全技术交底列表
     */
    @PreAuthorize("@ss.hasPermi('prepare:briefing:export')")
    @Log(title = "安全技术交底", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSafetyBriefing bizSafetyBriefing)
    {
        List<BizSafetyBriefing> list = bizSafetyBriefingService.selectBizSafetyBriefingList(bizSafetyBriefing);
        ExcelUtil<BizSafetyBriefing> util = new ExcelUtil<BizSafetyBriefing>(BizSafetyBriefing.class);
        util.exportExcel(response, list, "安全技术交底数据");
    }

    /**
     * 获取安全技术交底详细信息
     */
    @PreAuthorize("@ss.hasPermi('prepare:briefing:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bizSafetyBriefingService.selectBizSafetyBriefingById(id));
    }

    /**
     * 新增安全技术交底
     */
    @PreAuthorize("@ss.hasPermi('prepare:briefing:add')")
    @Log(title = "安全技术交底", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSafetyBriefing bizSafetyBriefing)
    {
        // 自动生成交底编号
        if (bizSafetyBriefing.getBriefingCode() == null || bizSafetyBriefing.getBriefingCode().isEmpty()) {
            String year = getCurrentYear();
            AtomicInteger counter = YEAR_COUNTER_MAP.computeIfAbsent(year, k -> new AtomicInteger(0));
            int count = counter.incrementAndGet();

            // 如果超过999，则重置为1
            if (count > 999) {
                counter.compareAndSet(count, 1);
                count = counter.get();
            }

            String briefingCode = "JD" + year + String.format("%03d", count);
            bizSafetyBriefing.setBriefingCode(briefingCode);
        }
        return toAjax(bizSafetyBriefingService.insertBizSafetyBriefing(bizSafetyBriefing));
    }

    /**
     * 修改安全技术交底
     */
    @PreAuthorize("@ss.hasPermi('prepare:briefing:edit')")
    @Log(title = "安全技术交底", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSafetyBriefing bizSafetyBriefing)
    {
        return toAjax(bizSafetyBriefingService.updateBizSafetyBriefing(bizSafetyBriefing));
    }

    /**
     * 删除安全技术交底
     */
    @PreAuthorize("@ss.hasPermi('prepare:briefing:remove')")
    @Log(title = "安全技术交底", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizSafetyBriefingService.deleteBizSafetyBriefingByIds(ids));
    }
}
