package com.ruoyi.process.service.impl;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizProjectChangeMapper;
import com.ruoyi.process.domain.BizProjectChange;
import com.ruoyi.process.service.IBizProjectChangeService;

/**
 * 项目变更管理Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizProjectChangeServiceImpl implements IBizProjectChangeService 
{
    @Autowired
    private BizProjectChangeMapper bizProjectChangeMapper;

    /**
     * 查询项目变更管理
     * 
     * @param changeId 项目变更管理主键
     * @return 项目变更管理
     */
    @Override
    public BizProjectChange selectBizProjectChangeByChangeId(Long changeId)
    {
        return bizProjectChangeMapper.selectBizProjectChangeByChangeId(changeId);
    }

    /**
     * 查询项目变更管理列表
     * 
     * @param bizProjectChange 项目变更管理
     * @return 项目变更管理
     */
    @Override
    public List<BizProjectChange> selectBizProjectChangeList(BizProjectChange bizProjectChange)
    {
        return bizProjectChangeMapper.selectBizProjectChangeList(bizProjectChange);
    }

    /**
     * 新增项目变更管理
     * 
     * @param bizProjectChange 项目变更管理
     * @return 结果
     */
    @Override
    public int insertBizProjectChange(BizProjectChange bizProjectChange)
    {
        // 自动生成变更记录编号
        if (bizProjectChange.getChangeNo() == null || bizProjectChange.getChangeNo().isEmpty()) {
            String changeNo = generateChangeNo();
            bizProjectChange.setChangeNo(changeNo);
        }
        
        // 设置创建时间
        if (bizProjectChange.getCreatedTime() == null) {
            bizProjectChange.setCreatedTime(new Date());
        }
        
        // 处理安全管理协议和施工方案是否修改的字段
        BizProjectChange dbChange = processYesNoFields(bizProjectChange);
        
        return bizProjectChangeMapper.insertBizProjectChange(dbChange);
    }

    /**
     * 修改项目变更管理
     * 
     * @param bizProjectChange 项目变更管理
     * @return 结果
     */
    @Override
    public int updateBizProjectChange(BizProjectChange bizProjectChange)
    {
        // 处理安全管理协议和施工方案是否修改的字段
        BizProjectChange dbChange = processYesNoFields(bizProjectChange);
        
        return bizProjectChangeMapper.updateBizProjectChange(dbChange);
    }

    /**
     * 批量删除项目变更管理
     * 
     * @param changeIds 需要删除的项目变更管理主键
     * @return 结果
     */
    @Override
    public int deleteBizProjectChangeByChangeIds(Long[] changeIds)
    {
        return bizProjectChangeMapper.deleteBizProjectChangeByChangeIds(changeIds);
    }

    /**
     * 删除项目变更管理信息
     * 
     * @param changeId 项目变更管理主键
     * @return 结果
     */
    @Override
    public int deleteBizProjectChangeByChangeId(Long changeId)
    {
        return bizProjectChangeMapper.deleteBizProjectChangeByChangeId(changeId);
    }
    
    /**
     * 处理"是/否"字段，将"Y"/"N"值转换为临时对象用于数据库操作
     * 
     * @param original 原始对象
     * @return 用于数据库操作的对象
     */
    private BizProjectChange processYesNoFields(BizProjectChange original) {
        BizProjectChange dbChange = new BizProjectChange();
        // 复制所有字段
        dbChange.setChangeId(original.getChangeId());
        dbChange.setChangeNo(original.getChangeNo());
        dbChange.setChoiceId(original.getChoiceId());
        dbChange.setProjectName(original.getProjectName());
        dbChange.setContractorName(original.getContractorName());
        dbChange.setChangeTime(original.getChangeTime());
        dbChange.setChangeType(original.getChangeType());
        dbChange.setChangeReason(original.getChangeReason());
        dbChange.setChangeRecord(original.getChangeRecord());
        dbChange.setSafetyProtocolAttachment(original.getSafetyProtocolAttachment());
        dbChange.setConstructionPlanAttachment(original.getConstructionPlanAttachment());
        dbChange.setRemark(original.getRemark());
        dbChange.setCreatedBy(original.getCreatedBy());
        dbChange.setCreatedTime(original.getCreatedTime());
        dbChange.setUpdatedBy(original.getUpdatedBy());
        dbChange.setUpdatedTime(original.getUpdatedTime());
        
        // 直接设置安全管理协议是否修改字段
        dbChange.setSafetyProtocolUpdated(original.getSafetyProtocolUpdated());
        
        // 直接设置施工方案是否修改字段
        dbChange.setConstructionPlanUpdated(original.getConstructionPlanUpdated());
        
        return dbChange;
    }
    
    /**
     * 生成变更记录编号
     * 编号格式: BG + 年份 + 三位序号 (例如: BG2025102)
     * 每年最大编号为BG + 年份 + 999
     * 
     * @return 变更记录编号
     */
    private String generateChangeNo() {
        // 获取当前年份
        String year = new SimpleDateFormat("yyyy").format(new Date());
        String yearPrefix = "BG" + year;
        
        // 查询当年最大的编号
        String maxNo = bizProjectChangeMapper.selectMaxChangeNoByYearPrefix(yearPrefix);
        
        int seq = 1;
        if (maxNo != null && !maxNo.isEmpty()) {
            // 从最大编号中提取序号部分并加1
            try {
                String seqStr = maxNo.substring(6); // "BG" + "2025" = 6位
                seq = Integer.parseInt(seqStr) + 1;
            } catch (Exception e) {
                // 如果解析失败，使用默认序号1
                seq = 1;
            }
        }
        
        // 检查序号是否超过最大值999
        if (seq > 999) {
            throw new RuntimeException("当年编号已达到最大值BG" + year + "999，请联系系统管理员");
        }
        
        // 格式化为3位数字，不足的前面补0
        return yearPrefix + String.format("%03d", seq);
    }
}
