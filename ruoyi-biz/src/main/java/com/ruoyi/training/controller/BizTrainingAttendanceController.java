package com.ruoyi.training.controller;

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
import com.ruoyi.training.domain.BizTrainingAttendance;
import com.ruoyi.training.service.IBizTrainingAttendanceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 人员参训记录Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/training/attendance")
public class BizTrainingAttendanceController extends BaseController
{
    @Autowired
    private IBizTrainingAttendanceService bizTrainingAttendanceService;

    /**
     * 查询人员参训记录列表
     */
    @PreAuthorize("@ss.hasPermi('training:attendance:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizTrainingAttendance bizTrainingAttendance)
    {
        startPage();
        List<BizTrainingAttendance> list = bizTrainingAttendanceService.selectBizTrainingAttendanceList(bizTrainingAttendance);
        return getDataTable(list);
    }

    /**
     * 导出人员参训记录列表
     */
    @PreAuthorize("@ss.hasPermi('training:attendance:export')")
    @Log(title = "人员参训记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizTrainingAttendance bizTrainingAttendance)
    {
        List<BizTrainingAttendance> list = bizTrainingAttendanceService.selectBizTrainingAttendanceList(bizTrainingAttendance);
        ExcelUtil<BizTrainingAttendance> util = new ExcelUtil<BizTrainingAttendance>(BizTrainingAttendance.class);
        util.exportExcel(response, list, "人员参训记录数据");
    }

    /**
     * 获取人员参训记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('training:attendance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bizTrainingAttendanceService.selectBizTrainingAttendanceById(id));
    }

    /**
     * 新增人员参训记录
     */
    @PreAuthorize("@ss.hasPermi('training:attendance:add')")
    @Log(title = "人员参训记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizTrainingAttendance bizTrainingAttendance)
    {
        return toAjax(bizTrainingAttendanceService.insertBizTrainingAttendance(bizTrainingAttendance));
    }

    /**
     * 修改人员参训记录
     */
    @PreAuthorize("@ss.hasPermi('training:attendance:edit')")
    @Log(title = "人员参训记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizTrainingAttendance bizTrainingAttendance)
    {
        return toAjax(bizTrainingAttendanceService.updateBizTrainingAttendance(bizTrainingAttendance));
    }

    /**
     * 删除人员参训记录
     */
    @PreAuthorize("@ss.hasPermi('training:attendance:remove')")
    @Log(title = "人员参训记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizTrainingAttendanceService.deleteBizTrainingAttendanceByIds(ids));
    }
}
