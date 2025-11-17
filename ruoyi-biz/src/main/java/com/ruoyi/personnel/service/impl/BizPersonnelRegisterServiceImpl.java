package com.ruoyi.personnel.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.personnel.mapper.BizPersonnelRegisterMapper;
import com.ruoyi.personnel.domain.BizPersonnelRegister;
import com.ruoyi.personnel.service.IBizPersonnelRegisterService;

/**
 * 人员信息台账Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizPersonnelRegisterServiceImpl implements IBizPersonnelRegisterService 
{
    @Autowired
    private BizPersonnelRegisterMapper bizPersonnelRegisterMapper;

    /**
     * 查询人员信息台账
     * 
     * @param personnelId 人员信息台账主键
     * @return 人员信息台账
     */
    @Override
    public BizPersonnelRegister selectBizPersonnelRegisterByPersonnelId(Long personnelId)
    {
        return bizPersonnelRegisterMapper.selectBizPersonnelRegisterByPersonnelId(personnelId);
    }

    /**
     * 查询人员信息台账列表
     * 
     * @param bizPersonnelRegister 人员信息台账
     * @return 人员信息台账
     */
    @Override
    public List<BizPersonnelRegister> selectBizPersonnelRegisterList(BizPersonnelRegister bizPersonnelRegister)
    {
        return bizPersonnelRegisterMapper.selectBizPersonnelRegisterList(bizPersonnelRegister);
    }

    /**
     * 新增人员信息台账
     * 
     * @param bizPersonnelRegister 人员信息台账
     * @return 结果
     */
    @Override
    public int insertBizPersonnelRegister(BizPersonnelRegister bizPersonnelRegister)
    {
        return bizPersonnelRegisterMapper.insertBizPersonnelRegister(bizPersonnelRegister);
    }

    /**
     * 修改人员信息台账
     * 
     * @param bizPersonnelRegister 人员信息台账
     * @return 结果
     */
    @Override
    public int updateBizPersonnelRegister(BizPersonnelRegister bizPersonnelRegister)
    {
        return bizPersonnelRegisterMapper.updateBizPersonnelRegister(bizPersonnelRegister);
    }

    /**
     * 批量删除人员信息台账
     * 
     * @param personnelIds 需要删除的人员信息台账主键
     * @return 结果
     */
    @Override
    public int deleteBizPersonnelRegisterByPersonnelIds(Long[] personnelIds)
    {
        return bizPersonnelRegisterMapper.deleteBizPersonnelRegisterByPersonnelIds(personnelIds);
    }

    /**
     * 删除人员信息台账信息
     * 
     * @param personnelId 人员信息台账主键
     * @return 结果
     */
    @Override
    public int deleteBizPersonnelRegisterByPersonnelId(Long personnelId)
    {
        return bizPersonnelRegisterMapper.deleteBizPersonnelRegisterByPersonnelId(personnelId);
    }
}
