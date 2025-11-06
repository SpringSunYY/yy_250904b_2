package com.ruoyi.pur.controller;

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
import com.ruoyi.pur.domain.EquipPurchaseOrder;
import com.ruoyi.pur.service.IEquipPurchaseOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 采购订单Controller
 *
 * @author laogao
 * @date 2025-09-23
 */
@RestController
@RequestMapping("/pur/order")
public class EquipPurchaseOrderController extends BaseController
{
    @Autowired
    private IEquipPurchaseOrderService equipPurchaseOrderService;

    /**
     * 查询采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('pur:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipPurchaseOrder equipPurchaseOrder)
    {
        startPage();
        List<EquipPurchaseOrder> list = equipPurchaseOrderService.selectEquipPurchaseOrderList(equipPurchaseOrder);
        return getDataTable(list);
    }

    /**
     * 导出采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('pur:order:export')")
    @Log(title = "采购订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipPurchaseOrder equipPurchaseOrder)
    {
        List<EquipPurchaseOrder> list = equipPurchaseOrderService.selectEquipPurchaseOrderList(equipPurchaseOrder);
        ExcelUtil<EquipPurchaseOrder> util = new ExcelUtil<EquipPurchaseOrder>(EquipPurchaseOrder.class);
        util.exportExcel(response, list, "采购订单数据");
    }

    /**
     * 获取采购订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('pur:order:query')")
    @GetMapping(value = "/{orderId}")
    public AjaxResult getInfo(@PathVariable("orderId") Long orderId)
    {
        return success(equipPurchaseOrderService.selectEquipPurchaseOrderByOrderId(orderId));
    }

    /**
     * 新增采购订单
     */
    @PreAuthorize("@ss.hasPermi('pur:order:add')")
    @Log(title = "采购订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipPurchaseOrder equipPurchaseOrder)
    {
        equipPurchaseOrder.setDeptId(getDeptId());
        equipPurchaseOrder.setApplyUserId(getUserId());
        return toAjax(equipPurchaseOrderService.insertEquipPurchaseOrder(equipPurchaseOrder));
    }

    /**
     * 修改采购订单
     */
    @PreAuthorize("@ss.hasPermi('pur:order:edit')")
    @Log(title = "采购订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipPurchaseOrder equipPurchaseOrder)
    {
        return toAjax(equipPurchaseOrderService.updateEquipPurchaseOrder(equipPurchaseOrder));
    }

    /**
     * 删除采购订单
     */
    @PreAuthorize("@ss.hasPermi('pur:order:remove')")
    @Log(title = "采购订单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{orderIds}")
    public AjaxResult remove(@PathVariable Long[] orderIds)
    {
        return toAjax(equipPurchaseOrderService.deleteEquipPurchaseOrderByOrderIds(orderIds));
    }
}
