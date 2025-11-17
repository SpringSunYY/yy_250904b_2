package com.ruoyi.personnel.mapper;

import java.util.List;
import com.ruoyi.personnel.domain.BizPersonnelRegister;

/**
 * 人员信息台账Mapper接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface BizPersonnelRegisterMapper 
{
    /**
     * 查询人员信息台账
     * 
     * @param personnelId 人员信息台账主键
     * @return 人员信息台账
     */
    public BizPersonnelRegister selectBizPersonnelRegisterByPersonnelId(Long personnelId);

    /**
     * 查询人员信息台账列表
     * 
     * @param bizPersonnelRegister 人员信息台账
     * @return 人员信息台账集合
     */
    public List<BizPersonnelRegister> selectBizPersonnelRegisterList(BizPersonnelRegister bizPersonnelRegister);

    /**
     * 新增人员信息台账
     * 
     * @param bizPersonnelRegister 人员信息台账
     * @return 结果
     */
    public int insertBizPersonnelRegister(BizPersonnelRegister bizPersonnelRegister);

    /**
     * 修改人员信息台账
     * 
     * @param bizPersonnelRegister 人员信息台账
     * @return 结果
     */
    public int updateBizPersonnelRegister(BizPersonnelRegister bizPersonnelRegister);

    /**
     * 删除人员信息台账
     * 
     * @param personnelId 人员信息台账主键
     * @return 结果
     */
    public int deleteBizPersonnelRegisterByPersonnelId(Long personnelId);

    /**
     * 批量删除人员信息台账
     * 
     * @param personnelIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizPersonnelRegisterByPersonnelIds(Long[] personnelIds);
}
