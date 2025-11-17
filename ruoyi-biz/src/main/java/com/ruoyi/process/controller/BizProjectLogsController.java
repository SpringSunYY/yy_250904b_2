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
import com.ruoyi.process.domain.BizProjectLogs;
import com.ruoyi.process.service.IBizProjectLogsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 项目日志Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/process/logs")
public class BizProjectLogsController extends BaseController
{
    @Autowired
    private IBizProjectLogsService bizProjectLogsService;
    
    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }
    
    // 生成项目日志编号
    private synchronized String generateProjectLogsCode() {
        String year = getCurrentYear();
        String maxCode = bizProjectLogsService.selectMaxLogsCodeByYear(year);
        
        int count = 1;
        if (maxCode != null && !maxCode.isEmpty()) {
            // 从类似 XR2025001 的格式中提取数字部分
            Pattern pattern = Pattern.compile("XR(\\d{4})(\\d{3})");
            Matcher matcher = pattern.matcher(maxCode);
            if (matcher.matches()) {
                // 获取数字部分并加1
                String numberPart = matcher.group(2);
                count = Integer.parseInt(numberPart) + 1;
            }
        }
        
        // 确保不超过999
        if (count > 999) {
            count = 1;
        }
        
        return String.format("XR%s%03d", year, count);
    }

    /**
     * 查询项目日志列表
     */
    @PreAuthorize("@ss.hasPermi('process:logs:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProjectLogs bizProjectLogs)
    {
        startPage();
        List<BizProjectLogs> list = bizProjectLogsService.selectBizProjectLogsList(bizProjectLogs);
        return getDataTable(list);
    }

    /**
     * 导出项目日志列表
     */
    @PreAuthorize("@ss.hasPermi('process:logs:export')")
    @Log(title = "项目日志", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizProjectLogs bizProjectLogs)
    {
        List<BizProjectLogs> list = bizProjectLogsService.selectBizProjectLogsList(bizProjectLogs);
        ExcelUtil<BizProjectLogs> util = new ExcelUtil<BizProjectLogs>(BizProjectLogs.class);
        util.exportExcel(response, list, "项目日志数据");
    }

    /**
     * 获取项目日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:logs:query')")
    @GetMapping(value = "/{logsId}")
    public AjaxResult getInfo(@PathVariable("logsId") Long logsId)
    {
        return success(bizProjectLogsService.selectBizProjectLogsByLogsId(logsId));
    }

    /**
     * 新增项目日志
     */
    @PreAuthorize("@ss.hasPermi('process:logs:add')")
    @Log(title = "项目日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizProjectLogs bizProjectLogs)
    {
        // 自动生成项目日志编号
        if (bizProjectLogs.getLogsCode() == null || bizProjectLogs.getLogsCode().isEmpty()) {
            String logsCode = generateProjectLogsCode();
            bizProjectLogs.setLogsCode(logsCode);
        }
        return toAjax(bizProjectLogsService.insertBizProjectLogs(bizProjectLogs));
    }

    /**
     * 修改项目日志
     */
    @PreAuthorize("@ss.hasPermi('process:logs:edit')")
    @Log(title = "项目日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizProjectLogs bizProjectLogs)
    {
        return toAjax(bizProjectLogsService.updateBizProjectLogs(bizProjectLogs));
    }

    /**
     * 删除项目日志
     */
    @PreAuthorize("@ss.hasPermi('process:logs:remove')")
    @Log(title = "项目日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logsIds}")
    public AjaxResult remove(@PathVariable Long[] logsIds)
    {
        return toAjax(bizProjectLogsService.deleteBizProjectLogsByLogsIds(logsIds));
    }
}
