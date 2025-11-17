package com.ruoyi.prepare.service.impl;

import com.ruoyi.choice.domain.BizContractorChoice;
import com.ruoyi.choice.mapper.BizContractorChoiceMapper;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.prepare.domain.BizToolInventory;
import com.ruoyi.prepare.mapper.BizToolInventoryMapper;
import com.ruoyi.prepare.service.IBizToolInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 工器具检查Service业务层处理
 *
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizToolInventoryServiceImpl implements IBizToolInventoryService {
    @Autowired
    private BizToolInventoryMapper bizToolInventoryMapper;

    @Autowired
    private BizContractorChoiceMapper bizContractorChoiceMapper;

    /**
     * 查询工器具检查
     *
     * @param toolId 工器具检查主键
     * @return 工器具检查
     */
    @Override
    public BizToolInventory selectBizToolInventoryByToolId(Long toolId) {
        return bizToolInventoryMapper.selectBizToolInventoryByToolId(toolId);
    }

    /**
     * 查询工器具检查列表
     *
     * @param bizToolInventory 工器具检查
     * @return 工器具检查
     */
    @Override
    public List<BizToolInventory> selectBizToolInventoryList(BizToolInventory bizToolInventory) {
        return bizToolInventoryMapper.selectBizToolInventoryList(bizToolInventory);
    }

    /**
     * 新增工器具检查
     *
     * @param bizToolInventory 工器具检查
     * @return 结果
     */
    @Override
    public int insertBizToolInventory(BizToolInventory bizToolInventory) {
        initData(bizToolInventory);
        return bizToolInventoryMapper.insertBizToolInventory(bizToolInventory);
    }

    private void initData(BizToolInventory bizToolInventory) {
        BizContractorChoice bizContractorChoice = bizContractorChoiceMapper.selectBizContractorChoiceByChoiceId(bizToolInventory.getChoiceId());
        if (StringUtils.isNull(bizContractorChoice)) {
            throw new RuntimeException("请选择项目");
        }
        bizToolInventory.setProjectName(bizContractorChoice.getProjectName());
        bizToolInventory.setContractorName(bizContractorChoice.getSelectedContractor());
        bizToolInventory.setChoiceId(bizContractorChoice.getChoiceId());
    }

    /**
     * 修改工器具检查
     *
     * @param bizToolInventory 工器具检查
     * @return 结果
     */
    @Override
    public int updateBizToolInventory(BizToolInventory bizToolInventory) {
        initData(bizToolInventory);
        return bizToolInventoryMapper.updateBizToolInventory(bizToolInventory);
    }

    /**
     * 批量删除工器具检查
     *
     * @param toolIds 需要删除的工器具检查主键
     * @return 结果
     */
    @Override
    public int deleteBizToolInventoryByToolIds(Long[] toolIds) {
        return bizToolInventoryMapper.deleteBizToolInventoryByToolIds(toolIds);
    }

    /**
     * 删除工器具检查信息
     *
     * @param toolId 工器具检查主键
     * @return 结果
     */
    @Override
    public int deleteBizToolInventoryByToolId(Long toolId) {
        return bizToolInventoryMapper.deleteBizToolInventoryByToolId(toolId);
    }
}
