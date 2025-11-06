package com.ruoyi.special.controller;

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
import com.ruoyi.special.domain.EquipPipelineData;
import com.ruoyi.special.service.IEquipPipelineDataService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 管道数据Controller
 * 
 * @author Laogao
 * @date 2025-11-04
 */
@RestController
@RequestMapping("/special/data")
public class EquipPipelineDataController extends BaseController
{
    @Autowired
    private IEquipPipelineDataService equipPipelineDataService;

    /**
     * 查询管道数据列表
     */
    @PreAuthorize("@ss.hasPermi('special:data:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipPipelineData equipPipelineData)
    {
        startPage();
        List<EquipPipelineData> list = equipPipelineDataService.selectEquipPipelineDataList(equipPipelineData);
        return getDataTable(list);
    }

    /**
     * 导出管道数据列表
     */
    @PreAuthorize("@ss.hasPermi('special:data:export')")
    @Log(title = "管道数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipPipelineData equipPipelineData)
    {
        List<EquipPipelineData> list = equipPipelineDataService.selectEquipPipelineDataList(equipPipelineData);
        ExcelUtil<EquipPipelineData> util = new ExcelUtil<EquipPipelineData>(EquipPipelineData.class);
        util.exportExcel(response, list, "管道数据数据");
    }

    /**
     * 获取管道数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('special:data:query')")
    @GetMapping(value = "/{pipelineId}")
    public AjaxResult getInfo(@PathVariable("pipelineId") String pipelineId)
    {
        return success(equipPipelineDataService.selectEquipPipelineDataByPipelineId(pipelineId));
    }

    /**
     * 新增管道数据
     */
    @PreAuthorize("@ss.hasPermi('special:data:add')")
    @Log(title = "管道数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipPipelineData equipPipelineData)
    {
        return toAjax(equipPipelineDataService.insertEquipPipelineData(equipPipelineData));
    }

    /**
     * 修改管道数据
     */
    @PreAuthorize("@ss.hasPermi('special:data:edit')")
    @Log(title = "管道数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipPipelineData equipPipelineData)
    {
        return toAjax(equipPipelineDataService.updateEquipPipelineData(equipPipelineData));
    }

    /**
     * 删除管道数据
     */
    @PreAuthorize("@ss.hasPermi('special:data:remove')")
    @Log(title = "管道数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pipelineIds}")
    public AjaxResult remove(@PathVariable String[] pipelineIds)
    {
        return toAjax(equipPipelineDataService.deleteEquipPipelineDataByPipelineIds(pipelineIds));
    }
}
