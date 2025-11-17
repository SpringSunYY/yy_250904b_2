package com.ruoyi.personnel.controller;

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
import com.ruoyi.personnel.domain.BizPersonnelRegister;
import com.ruoyi.personnel.service.IBizPersonnelRegisterService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 人员信息台账Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/personnel/register")
public class BizPersonnelRegisterController extends BaseController
{
    @Autowired
    private IBizPersonnelRegisterService bizPersonnelRegisterService;

    /**
     * 查询人员信息台账列表
     */
    @PreAuthorize("@ss.hasPermi('personnel:register:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPersonnelRegister bizPersonnelRegister)
    {
        startPage();
        List<BizPersonnelRegister> list = bizPersonnelRegisterService.selectBizPersonnelRegisterList(bizPersonnelRegister);
        return getDataTable(list);
    }

    /**
     * 导出人员信息台账列表
     */
    @PreAuthorize("@ss.hasPermi('personnel:register:export')")
    @Log(title = "人员信息台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPersonnelRegister bizPersonnelRegister)
    {
        List<BizPersonnelRegister> list = bizPersonnelRegisterService.selectBizPersonnelRegisterList(bizPersonnelRegister);
        ExcelUtil<BizPersonnelRegister> util = new ExcelUtil<BizPersonnelRegister>(BizPersonnelRegister.class);
        util.exportExcel(response, list, "人员信息台账数据");
    }

    /**
     * 获取人员信息台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('personnel:register:query')")
    @GetMapping(value = "/{personnelId}")
    public AjaxResult getInfo(@PathVariable("personnelId") Long personnelId)
    {
        return success(bizPersonnelRegisterService.selectBizPersonnelRegisterByPersonnelId(personnelId));
    }

    /**
     * 新增人员信息台账
     */
    @PreAuthorize("@ss.hasPermi('personnel:register:add')")
    @Log(title = "人员信息台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPersonnelRegister bizPersonnelRegister)
    {
        return toAjax(bizPersonnelRegisterService.insertBizPersonnelRegister(bizPersonnelRegister));
    }

    /**
     * 修改人员信息台账
     */
    @PreAuthorize("@ss.hasPermi('personnel:register:edit')")
    @Log(title = "人员信息台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPersonnelRegister bizPersonnelRegister)
    {
        return toAjax(bizPersonnelRegisterService.updateBizPersonnelRegister(bizPersonnelRegister));
    }

    /**
     * 删除人员信息台账
     */
    @PreAuthorize("@ss.hasPermi('personnel:register:remove')")
    @Log(title = "人员信息台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{personnelIds}")
    public AjaxResult remove(@PathVariable Long[] personnelIds)
    {
        return toAjax(bizPersonnelRegisterService.deleteBizPersonnelRegisterByPersonnelIds(personnelIds));
    }
}
