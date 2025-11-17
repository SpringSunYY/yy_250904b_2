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
import com.ruoyi.process.domain.BizSiteInspections;
import com.ruoyi.process.service.IBizSiteInspectionsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 监督检查记录Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/process/inspections")
public class BizSiteInspectionsController extends BaseController
{
    // 用于存储每年的计数器，key为年份，value为该年份的计数器
    private static final Map<String, AtomicInteger> YEAR_COUNTER_MAP = new ConcurrentHashMap<>();
    
    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }
    
    @Autowired
    private IBizSiteInspectionsService bizSiteInspectionsService;

    /**
     * 查询监督检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('process:inspections:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizSiteInspections bizSiteInspections)
    {
        startPage();
        List<BizSiteInspections> list = bizSiteInspectionsService.selectBizSiteInspectionsList(bizSiteInspections);
        return getDataTable(list);
    }

    /**
     * 导出监督检查记录列表
     */
    @PreAuthorize("@ss.hasPermi('process:inspections:export')")
    @Log(title = "监督检查记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSiteInspections bizSiteInspections)
    {
        List<BizSiteInspections> list = bizSiteInspectionsService.selectBizSiteInspectionsList(bizSiteInspections);
        ExcelUtil<BizSiteInspections> util = new ExcelUtil<BizSiteInspections>(BizSiteInspections.class);
        util.exportExcel(response, list, "监督检查记录数据");
    }

    /**
     * 获取监督检查记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('process:inspections:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bizSiteInspectionsService.selectBizSiteInspectionsById(id));
    }

    /**
     * 新增监督检查记录
     */
    @PreAuthorize("@ss.hasPermi('process:inspections:add')")
    @Log(title = "监督检查记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizSiteInspections bizSiteInspections)
    {
        try {
            // 自动生成检查记录编号
            if (bizSiteInspections.getInspectionCode() == null || bizSiteInspections.getInspectionCode().isEmpty()) {
                String year = getCurrentYear();
                AtomicInteger counter = YEAR_COUNTER_MAP.computeIfAbsent(year, k -> new AtomicInteger(0));
                int count = counter.incrementAndGet();
                
                // 检查数据库中是否已存在该编号，如果存在则继续增加计数器
                String inspectionCode = "JC" + year + String.format("%03d", count);
                while (bizSiteInspectionsService.selectBizSiteInspectionsByInspectionCode(inspectionCode) != null) {
                    count = counter.incrementAndGet();
                    inspectionCode = "JC" + year + String.format("%03d", count);
                    // 防止无限循环
                    if (count > 999) {
                        return AjaxResult.error("当年编号已用完，请联系管理员");
                    }
                }
                
                bizSiteInspections.setInspectionCode(inspectionCode);
            }
            return toAjax(bizSiteInspectionsService.insertBizSiteInspections(bizSiteInspections));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 修改监督检查记录
     */
    @PreAuthorize("@ss.hasPermi('process:inspections:edit')")
    @Log(title = "监督检查记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizSiteInspections bizSiteInspections)
    {
        try {
            return toAjax(bizSiteInspectionsService.updateBizSiteInspections(bizSiteInspections));
        } catch (Exception e) {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 删除监督检查记录
     */
    @PreAuthorize("@ss.hasPermi('process:inspections:remove')")
    @Log(title = "监督检查记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizSiteInspectionsService.deleteBizSiteInspectionsByIds(ids));
    }
}
