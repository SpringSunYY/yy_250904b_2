package com.ruoyi.choice.service.impl;

import com.ruoyi.choice.domain.BizContractorChoice;
import com.ruoyi.choice.domain.BizContractorContract;
import com.ruoyi.choice.mapper.BizContractorChoiceMapper;
import com.ruoyi.choice.mapper.BizContractorContractMapper;
import com.ruoyi.choice.service.IBizContractorContractService;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合同与协议Service业务层处理
 *
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizContractorContractServiceImpl implements IBizContractorContractService {
    @Autowired
    private BizContractorContractMapper bizContractorContractMapper;

    @Resource
    private BizContractorChoiceMapper bizContractorChoiceMapper;

    /**
     * 查询合同与协议
     *
     * @param id 合同与协议主键
     * @return 合同与协议
     */
    @Override
    public BizContractorContract selectBizContractorContractById(Long id) {
        return bizContractorContractMapper.selectBizContractorContractById(id);
    }

    /**
     * 查询合同与协议列表
     *
     * @param bizContractorContract 合同与协议
     * @return 合同与协议
     */
    @Override
    public List<BizContractorContract> selectBizContractorContractList(BizContractorContract bizContractorContract) {
        return bizContractorContractMapper.selectBizContractorContractList(bizContractorContract);
    }

    /**
     * 新增合同与协议
     *
     * @param bizContractorContract 合同与协议
     * @return 结果
     */
    @Override
    public int insertBizContractorContract(BizContractorContract bizContractorContract) {
        initData(bizContractorContract);
        return bizContractorContractMapper.insertBizContractorContract(bizContractorContract);
    }

    private void initData(BizContractorContract bizContractorContract) {
        if (StringUtils.isNull(bizContractorContract.getChoiceId())) {
            throw new RuntimeException("请选择项目");
        }
        BizContractorChoice bizContractorChoice = bizContractorChoiceMapper.selectBizContractorChoiceByChoiceId(bizContractorContract.getChoiceId());
        if (StringUtils.isNull(bizContractorChoice)) {
            throw new RuntimeException("没有找到对应的项目");
        }
        bizContractorContract.setProjectName(bizContractorChoice.getProjectName());
        bizContractorContract.setContractorName(bizContractorChoice.getSelectedContractor());
        bizContractorContract.setChoiceId(bizContractorChoice.getChoiceId());
    }

    /**
     * 修改合同与协议
     *
     * @param bizContractorContract 合同与协议
     * @return 结果
     */
    @Override
    public int updateBizContractorContract(BizContractorContract bizContractorContract) {
        initData(bizContractorContract);
        return bizContractorContractMapper.updateBizContractorContract(bizContractorContract);
    }

    /**
     * 批量删除合同与协议
     *
     * @param ids 需要删除的合同与协议主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorContractByIds(Long[] ids) {
        return bizContractorContractMapper.deleteBizContractorContractByIds(ids);
    }

    /**
     * 删除合同与协议信息
     *
     * @param id 合同与协议主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorContractById(Long id) {
        return bizContractorContractMapper.deleteBizContractorContractById(id);
    }
}
