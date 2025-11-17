package com.ruoyi.choice.service;

import java.util.List;
import com.ruoyi.choice.domain.BizContractorChoice;

/**
 * 承包商选择Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizContractorChoiceService 
{
    /**
     * 查询承包商选择
     * 
     * @param choiceId 承包商选择主键
     * @return 承包商选择
     */
    public BizContractorChoice selectBizContractorChoiceByChoiceId(Long choiceId);

    /**
     * 查询承包商选择列表
     * 
     * @param bizContractorChoice 承包商选择
     * @return 承包商选择集合
     */
    public List<BizContractorChoice> selectBizContractorChoiceList(BizContractorChoice bizContractorChoice);

    /**
     * 新增承包商选择
     * 
     * @param bizContractorChoice 承包商选择
     * @return 结果
     */
    public int insertBizContractorChoice(BizContractorChoice bizContractorChoice);

    /**
     * 修改承包商选择
     * 
     * @param bizContractorChoice 承包商选择
     * @return 结果
     */
    public int updateBizContractorChoice(BizContractorChoice bizContractorChoice);

    /**
     * 批量删除承包商选择
     * 
     * @param choiceIds 需要删除的承包商选择主键集合
     * @return 结果
     */
    public int deleteBizContractorChoiceByChoiceIds(Long[] choiceIds);

    /**
     * 删除承包商选择信息
     * 
     * @param choiceId 承包商选择主键
     * @return 结果
     */
    public int deleteBizContractorChoiceByChoiceId(Long choiceId);
}
