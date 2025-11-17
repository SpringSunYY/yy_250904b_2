package com.ruoyi.choice.controller;

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
import com.ruoyi.choice.domain.BizContractorContract;
import com.ruoyi.choice.service.IBizContractorContractService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 合同与协议Controller
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@RestController
@RequestMapping("/choice/contract")
public class BizContractorContractController extends BaseController
{
    @Autowired
    private IBizContractorContractService bizContractorContractService;

    /**
     * 查询合同与协议列表
     */
    @PreAuthorize("@ss.hasPermi('choice:contract:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizContractorContract bizContractorContract)
    {
        startPage();
        List<BizContractorContract> list = bizContractorContractService.selectBizContractorContractList(bizContractorContract);
        return getDataTable(list);
    }

    /**
     * 导出合同与协议列表
     */
    @PreAuthorize("@ss.hasPermi('choice:contract:export')")
    @Log(title = "合同与协议", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizContractorContract bizContractorContract)
    {
        List<BizContractorContract> list = bizContractorContractService.selectBizContractorContractList(bizContractorContract);
        ExcelUtil<BizContractorContract> util = new ExcelUtil<BizContractorContract>(BizContractorContract.class);
        util.exportExcel(response, list, "合同与协议数据");
    }

    /**
     * 获取合同与协议详细信息
     */
    @PreAuthorize("@ss.hasPermi('choice:contract:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bizContractorContractService.selectBizContractorContractById(id));
    }

    /**
     * 新增合同与协议
     */
    @PreAuthorize("@ss.hasPermi('choice:contract:add')")
    @Log(title = "合同与协议", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizContractorContract bizContractorContract)
    {
        return toAjax(bizContractorContractService.insertBizContractorContract(bizContractorContract));
    }

    /**
     * 修改合同与协议
     */
    @PreAuthorize("@ss.hasPermi('choice:contract:edit')")
    @Log(title = "合同与协议", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizContractorContract bizContractorContract)
    {
        return toAjax(bizContractorContractService.updateBizContractorContract(bizContractorContract));
    }

    /**
     * 删除合同与协议
     */
    @PreAuthorize("@ss.hasPermi('choice:contract:remove')")
    @Log(title = "合同与协议", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizContractorContractService.deleteBizContractorContractByIds(ids));
    }
}
