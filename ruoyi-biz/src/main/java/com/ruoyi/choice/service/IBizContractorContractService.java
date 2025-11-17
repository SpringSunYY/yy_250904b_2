package com.ruoyi.choice.service;

import java.util.List;
import com.ruoyi.choice.domain.BizContractorContract;

/**
 * 合同与协议Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizContractorContractService 
{
    /**
     * 查询合同与协议
     * 
     * @param id 合同与协议主键
     * @return 合同与协议
     */
    public BizContractorContract selectBizContractorContractById(Long id);

    /**
     * 查询合同与协议列表
     * 
     * @param bizContractorContract 合同与协议
     * @return 合同与协议集合
     */
    public List<BizContractorContract> selectBizContractorContractList(BizContractorContract bizContractorContract);

    /**
     * 新增合同与协议
     * 
     * @param bizContractorContract 合同与协议
     * @return 结果
     */
    public int insertBizContractorContract(BizContractorContract bizContractorContract);

    /**
     * 修改合同与协议
     * 
     * @param bizContractorContract 合同与协议
     * @return 结果
     */
    public int updateBizContractorContract(BizContractorContract bizContractorContract);

    /**
     * 批量删除合同与协议
     * 
     * @param ids 需要删除的合同与协议主键集合
     * @return 结果
     */
    public int deleteBizContractorContractByIds(Long[] ids);

    /**
     * 删除合同与协议信息
     * 
     * @param id 合同与协议主键
     * @return 结果
     */
    public int deleteBizContractorContractById(Long id);
}
