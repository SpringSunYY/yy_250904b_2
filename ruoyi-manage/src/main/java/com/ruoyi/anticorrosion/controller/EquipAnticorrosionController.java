package com.ruoyi.anticorrosion.controller;

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
import com.ruoyi.anticorrosion.domain.EquipAnticorrosion;
import com.ruoyi.anticorrosion.service.IEquipAnticorrosionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 防腐蚀检测Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/anticorrosion/anticorrosion")
public class EquipAnticorrosionController extends BaseController
{
    @Autowired
    private IEquipAnticorrosionService equipAnticorrosionService;

    /**
     * 查询防腐蚀检测列表
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:anticorrosion:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipAnticorrosion equipAnticorrosion)
    {
        startPage();
        List<EquipAnticorrosion> list = equipAnticorrosionService.selectEquipAnticorrosionList(equipAnticorrosion);
        return getDataTable(list);
    }

    /**
     * 导出防腐蚀检测列表
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:anticorrosion:export')")
    @Log(title = "防腐蚀检测", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipAnticorrosion equipAnticorrosion)
    {
        List<EquipAnticorrosion> list = equipAnticorrosionService.selectEquipAnticorrosionList(equipAnticorrosion);
        ExcelUtil<EquipAnticorrosion> util = new ExcelUtil<EquipAnticorrosion>(EquipAnticorrosion.class);
        util.exportExcel(response, list, "防腐蚀检测数据");
    }

    /**
     * 获取防腐蚀检测详细信息
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:anticorrosion:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(equipAnticorrosionService.selectEquipAnticorrosionById(id));
    }

    /**
     * 新增防腐蚀检测
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:anticorrosion:add')")
    @Log(title = "防腐蚀检测", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipAnticorrosion equipAnticorrosion)
    {
        return toAjax(equipAnticorrosionService.insertEquipAnticorrosion(equipAnticorrosion));
    }

    /**
     * 修改防腐蚀检测
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:anticorrosion:edit')")
    @Log(title = "防腐蚀检测", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipAnticorrosion equipAnticorrosion)
    {
        return toAjax(equipAnticorrosionService.updateEquipAnticorrosion(equipAnticorrosion));
    }

    /**
     * 删除防腐蚀检测
     */
    @PreAuthorize("@ss.hasPermi('anticorrosion:anticorrosion:remove')")
    @Log(title = "防腐蚀检测", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(equipAnticorrosionService.deleteEquipAnticorrosionByIds(ids));
    }
}
