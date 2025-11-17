package com.ruoyi.process.controller;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import com.ruoyi.process.domain.BizSafetyLogs;
import com.ruoyi.process.service.IBizSafetyLogsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 安全日志Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/process/safety")
public class BizSafetyLogsController extends BaseController
{
    // 用于存储每年的计数器，key为年份，value为该年份的计数器
    private static final Map<String, AtomicInteger> YEAR_COUNTER_MAP = new ConcurrentHashMap<>();
    
    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }
    
    @Autowired
    private IBizSafetyLogsService bizSafetyLogsService;

    /**
     * 查询安全日志列表
     */
    @PreAuthorize("@ss.hasPermi('process:safety:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSafetyLogs bizSafetyLogs)
    {
        startPage();
        List<BizSafetyLogs> list = bizSafetyLogsService.selectBizSafetyLogsList(bizSafetyLogs);
        return getDataTable(list);
    }

    /**
     * 导出安全日志列表
     */
    @PreAuthorize("@ss.hasPermi('process:safety:export')")
    @Log(title = "安全日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSafetyLogs bizSafetyLogs)
    {
        List<BizSafetyLogs> list = bizSafetyLogsService.selectBizSafetyLogsList(bizSafetyLogs);
        ExcelUtil<BizSafetyLogs> util = new ExcelUtil<BizSafetyLogs>(BizSafetyLogs.class);
        util.exportExcel(response, list, "安全日志数据");
    }

    /**
     * 获取安全日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:safety:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") Long logId)
    {
        return success(bizSafetyLogsService.selectBizSafetyLogsByLogId(logId));
    }

    /**
     * 新增安全日志
     */
    @PreAuthorize("@ss.hasPermi('process:safety:add')")
    @Log(title = "安全日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSafetyLogs bizSafetyLogs)
    {
        try {
            // 自动生成安全日志编号
            if (bizSafetyLogs.getLogCode() == null || bizSafetyLogs.getLogCode().isEmpty()) {
                String year = getCurrentYear();
                AtomicInteger counter = YEAR_COUNTER_MAP.computeIfAbsent(year, k -> new AtomicInteger(0));
                int count = counter.incrementAndGet();
                
                // 检查数据库中是否已存在该编号，如果存在则继续增加计数器
                String logCode = "AR" + year + String.format("%03d", count);
                while (bizSafetyLogsService.selectBizSafetyLogsByLogCode(logCode) != null) {
                    count = counter.incrementAndGet();
                    logCode = "AR" + year + String.format("%03d", count);
                    // 防止无限循环
                    if (count > 999) {
                        return AjaxResult.error("当年编号已用完，请联系管理员");
                    }
                }
                
                bizSafetyLogs.setLogCode(logCode);
            }
            return toAjax(bizSafetyLogsService.insertBizSafetyLogs(bizSafetyLogs));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改安全日志
     */
    @PreAuthorize("@ss.hasPermi('process:safety:edit')")
    @Log(title = "安全日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSafetyLogs bizSafetyLogs)
    {
        return toAjax(bizSafetyLogsService.updateBizSafetyLogs(bizSafetyLogs));
    }

    /**
     * 删除安全日志
     */
    @PreAuthorize("@ss.hasPermi('process:safety:remove')")
    @Log(title = "安全日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable Long[] logIds)
    {
        return toAjax(bizSafetyLogsService.deleteBizSafetyLogsByLogIds(logIds));
    }
}
