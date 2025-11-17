package com.ruoyi.contractor.controller;

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
import com.ruoyi.contractor.domain.BizContractorProject;
import com.ruoyi.contractor.service.IBizContractorProjectService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 发包项目信息Controller
 * 
 * @author Laogao
 * @date 2025-10-23
 */
@RestController
@RequestMapping("/contractor/project")
public class BizContractorProjectController extends BaseController
{
    @Autowired
    private IBizContractorProjectService bizContractorProjectService;

    /**
     * 查询发包项目信息列表
     */
    @PreAuthorize("@ss.hasPermi('contractor:project:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizContractorProject bizContractorProject)
    {
        startPage();
        List<BizContractorProject> list = bizContractorProjectService.selectBizContractorProjectList(bizContractorProject);
        return getDataTable(list);
    }

    /**
     * 导出发包项目信息列表
     */
    @PreAuthorize("@ss.hasPermi('contractor:project:export')")
    @Log(title = "发包项目信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizContractorProject bizContractorProject)
    {
        List<BizContractorProject> list = bizContractorProjectService.selectBizContractorProjectList(bizContractorProject);
        ExcelUtil<BizContractorProject> util = new ExcelUtil<BizContractorProject>(BizContractorProject.class);
        util.exportExcel(response, list, "发包项目信息数据");
    }

    /**
     * 获取发包项目信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('contractor:project:query')")
    @GetMapping(value = "/{projectId}")
    public AjaxResult getInfo(@PathVariable("projectId") Long projectId)
    {
        return success(bizContractorProjectService.selectBizContractorProjectByProjectId(projectId));
    }

    /**
     * 新增发包项目信息
     */
    @PreAuthorize("@ss.hasPermi('contractor:project:add')")
    @Log(title = "发包项目信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizContractorProject bizContractorProject)
    {
        return toAjax(bizContractorProjectService.insertBizContractorProject(bizContractorProject));
    }

    /**
     * 修改发包项目信息
     */
    @PreAuthorize("@ss.hasPermi('contractor:project:edit')")
    @Log(title = "发包项目信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizContractorProject bizContractorProject)
    {
        return toAjax(bizContractorProjectService.updateBizContractorProject(bizContractorProject));
    }

    /**
     * 删除发包项目信息
     */
    @PreAuthorize("@ss.hasPermi('contractor:project:remove')")
    @Log(title = "发包项目信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{projectIds}")
    public AjaxResult remove(@PathVariable Long[] projectIds)
    {
        return toAjax(bizContractorProjectService.deleteBizContractorProjectByProjectIds(projectIds));
    }
}
