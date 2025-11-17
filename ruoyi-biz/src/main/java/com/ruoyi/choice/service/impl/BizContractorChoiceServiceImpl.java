package com.ruoyi.choice.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.choice.mapper.BizContractorChoiceMapper;
import com.ruoyi.choice.domain.BizContractorChoice;
import com.ruoyi.choice.service.IBizContractorChoiceService;

/**
 * 承包商选择Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizContractorChoiceServiceImpl implements IBizContractorChoiceService 
{
    @Autowired
    private BizContractorChoiceMapper bizContractorChoiceMapper;

    /**
     * 查询承包商选择
     * 
     * @param choiceId 承包商选择主键
     * @return 承包商选择
     */
    @Override
    public BizContractorChoice selectBizContractorChoiceByChoiceId(Long choiceId)
    {
        return bizContractorChoiceMapper.selectBizContractorChoiceByChoiceId(choiceId);
    }

    /**
     * 查询承包商选择列表
     * 
     * @param bizContractorChoice 承包商选择
     * @return 承包商选择
     */
    @Override
    public List<BizContractorChoice> selectBizContractorChoiceList(BizContractorChoice bizContractorChoice)
    {
        return bizContractorChoiceMapper.selectBizContractorChoiceList(bizContractorChoice);
    }

    /**
     * 新增承包商选择
     * 
     * @param bizContractorChoice 承包商选择
     * @return 结果
     */
    @Override
    public int insertBizContractorChoice(BizContractorChoice bizContractorChoice)
    {
        return bizContractorChoiceMapper.insertBizContractorChoice(bizContractorChoice);
    }

    /**
     * 修改承包商选择
     * 
     * @param bizContractorChoice 承包商选择
     * @return 结果
     */
    @Override
    public int updateBizContractorChoice(BizContractorChoice bizContractorChoice)
    {
        return bizContractorChoiceMapper.updateBizContractorChoice(bizContractorChoice);
    }

    /**
     * 批量删除承包商选择
     * 
     * @param choiceIds 需要删除的承包商选择主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorChoiceByChoiceIds(Long[] choiceIds)
    {
        return bizContractorChoiceMapper.deleteBizContractorChoiceByChoiceIds(choiceIds);
    }

    /**
     * 删除承包商选择信息
     * 
     * @param choiceId 承包商选择主键
     * @return 结果
     */
    @Override
    public int deleteBizContractorChoiceByChoiceId(Long choiceId)
    {
        return bizContractorChoiceMapper.deleteBizContractorChoiceByChoiceId(choiceId);
    }
}
