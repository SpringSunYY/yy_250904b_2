package com.ruoyi.equip.controller;

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
import com.ruoyi.equip.domain.EquipDevice;
import com.ruoyi.equip.service.IEquipDeviceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备安装验收Controller
 * 
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/equip/device")
public class EquipDeviceController extends BaseController
{
    @Autowired
    private IEquipDeviceService equipDeviceService;

    /**
     * 查询设备安装验收列表
     */
    @PreAuthorize("@ss.hasPermi('equip:device:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipDevice equipDevice)
    {
        startPage();
        List<EquipDevice> list = equipDeviceService.selectEquipDeviceList(equipDevice);
        return getDataTable(list);
    }

    /**
     * 导出设备安装验收列表
     */
    @PreAuthorize("@ss.hasPermi('equip:device:export')")
    @Log(title = "设备安装验收", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipDevice equipDevice)
    {
        List<EquipDevice> list = equipDeviceService.selectEquipDeviceList(equipDevice);
        ExcelUtil<EquipDevice> util = new ExcelUtil<EquipDevice>(EquipDevice.class);
        util.exportExcel(response, list, "设备安装验收数据");
    }

    /**
     * 获取设备安装验收详细信息
     */
    @PreAuthorize("@ss.hasPermi('equip:device:query')")
    @GetMapping(value = "/{deviceId}")
    public AjaxResult getInfo(@PathVariable("deviceId") Long deviceId)
    {
        return success(equipDeviceService.selectEquipDeviceByDeviceId(deviceId));
    }

    /**
     * 新增设备安装验收
     */
    @PreAuthorize("@ss.hasPermi('equip:device:add')")
    @Log(title = "设备安装验收", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipDevice equipDevice)
    {
        return toAjax(equipDeviceService.insertEquipDevice(equipDevice));
    }

    /**
     * 修改设备安装验收
     */
    @PreAuthorize("@ss.hasPermi('equip:device:edit')")
    @Log(title = "设备安装验收", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipDevice equipDevice)
    {
        return toAjax(equipDeviceService.updateEquipDevice(equipDevice));
    }

    /**
     * 删除设备安装验收
     */
    @PreAuthorize("@ss.hasPermi('equip:device:remove')")
    @Log(title = "设备安装验收", businessType = BusinessType.DELETE)
	@DeleteMapping("/{deviceIds}")
    public AjaxResult remove(@PathVariable Long[] deviceIds)
    {
        return toAjax(equipDeviceService.deleteEquipDeviceByDeviceIds(deviceIds));
    }
}
