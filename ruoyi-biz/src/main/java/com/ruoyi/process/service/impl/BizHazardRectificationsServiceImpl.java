package com.ruoyi.process.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.process.mapper.BizHazardRectificationsMapper;
import com.ruoyi.process.domain.BizHazardRectifications;
import com.ruoyi.process.service.IBizHazardRectificationsService;
import com.ruoyi.common.exception.ServiceException;

/**
 * 隐患整改记录Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizHazardRectificationsServiceImpl implements IBizHazardRectificationsService 
{
    @Autowired
    private BizHazardRectificationsMapper bizHazardRectificationsMapper;
    
    // 获取当前年份
    private String getCurrentYear() {
        return new SimpleDateFormat("yyyy").format(new Date());
    }
    
    // 获取当年最大编号
    private synchronized int getMaxSequence(String year) {
        // 查询数据库中当年已有的最大编号
        BizHazardRectifications query = new BizHazardRectifications();
        // 设置查询条件为当年的编号前缀
        query.setRectificationCode("YH" + year + "%");
        List<BizHazardRectifications> list = bizHazardRectificationsMapper.selectBizHazardRectificationsList(query);
        
        int maxSeq = 0;
        for (BizHazardRectifications item : list) {
            String code = item.getRectificationCode();
            if (code != null && code.startsWith("YH" + year)) {
                try {
                    // 提取年份后的数字部分
                    String seqStr = code.substring(6); // "YH" + 4位年份 = 6个字符
                    int seq = Integer.parseInt(seqStr);
                    if (seq > maxSeq) {
                        maxSeq = seq;
                    }
                } catch (Exception e) {
                    // 忽略解析错误
                }
            }
        }
        return maxSeq;
    }
    
    // 生成唯一的隐患编号
    private synchronized String generateRectificationCode() {
        String year = getCurrentYear();
        
        // 获取当年最大编号并加1
        int maxSeq = getMaxSequence(year);
        int nextSeq = maxSeq + 1;
        
        // 防止编号超出最大值9999
        if (nextSeq > 9999) {
            throw new ServiceException("当年编号已用完，请联系管理员");
        }
        
        // 生成编号格式：YH+年份+4位数字，如YH20251002
        return "YH" + year + String.format("%04d", nextSeq);
    }

    /**
     * 查询隐患整改记录
     * 
     * @param id 隐患整改记录主键
     * @return 隐患整改记录
     */
    @Override
    public BizHazardRectifications selectBizHazardRectificationsById(Long id)
    {
        return bizHazardRectificationsMapper.selectBizHazardRectificationsById(id);
    }

    /**
     * 查询隐患整改记录列表
     * 
     * @param bizHazardRectifications 隐患整改记录
     * @return 隐患整改记录
     */
    @Override
    public List<BizHazardRectifications> selectBizHazardRectificationsList(BizHazardRectifications bizHazardRectifications)
    {
        return bizHazardRectificationsMapper.selectBizHazardRectificationsList(bizHazardRectifications);
    }

    /**
     * 新增隐患整改记录
     * 
     * @param bizHazardRectifications 隐患整改记录
     * @return 结果
     */
    @Override
    public int insertBizHazardRectifications(BizHazardRectifications bizHazardRectifications) {
        // 自动生成隐患编号
        if (bizHazardRectifications.getRectificationCode() == null || bizHazardRectifications.getRectificationCode().isEmpty()) {
            String rectificationCode = generateRectificationCode();
            bizHazardRectifications.setRectificationCode(rectificationCode);
        } else {
            // 检查手动输入的编号是否已存在
            BizHazardRectifications query = new BizHazardRectifications();
            query.setRectificationCode(bizHazardRectifications.getRectificationCode());
            List<BizHazardRectifications> existingList = bizHazardRectificationsMapper.selectBizHazardRectificationsList(query);
            if (existingList != null && !existingList.isEmpty()) {
                throw new ServiceException("隐患整改编号已存在，请重新输入");
            }
        }
        
        return bizHazardRectificationsMapper.insertBizHazardRectifications(bizHazardRectifications);
    }

    /**
     * 修改隐患整改记录
     * 
     * @param bizHazardRectifications 隐患整改记录
     * @return 结果
     */
    @Override
    public int updateBizHazardRectifications(BizHazardRectifications bizHazardRectifications)
    {
        // 如果编号有修改，则检查新编号是否已存在
        if (bizHazardRectifications.getRectificationCode() != null && !bizHazardRectifications.getRectificationCode().isEmpty()) {
            BizHazardRectifications query = new BizHazardRectifications();
            query.setRectificationCode(bizHazardRectifications.getRectificationCode());
            List<BizHazardRectifications> existingList = bizHazardRectificationsMapper.selectBizHazardRectificationsList(query);
            if (existingList != null && !existingList.isEmpty()) {
                // 如果查到的记录ID不等于当前修改的记录ID，则说明编号重复
                if (!existingList.get(0).getId().equals(bizHazardRectifications.getId())) {
                    throw new ServiceException("隐患整改编号已存在，请重新输入");
                }
            }
        }
        return bizHazardRectificationsMapper.updateBizHazardRectifications(bizHazardRectifications);
    }

    /**
     * 批量删除隐患整改记录
     * 
     * @param ids 需要删除的隐患整改记录主键
     * @return 结果
     */
    @Override
    public int deleteBizHazardRectificationsByIds(Long[] ids)
    {
        return bizHazardRectificationsMapper.deleteBizHazardRectificationsByIds(ids);
    }

    /**
     * 删除隐患整改记录信息
     * 
     * @param id 隐患整改记录主键
     * @return 结果
     */
    @Override
    public int deleteBizHazardRectificationsById(Long id)
    {
        return bizHazardRectificationsMapper.deleteBizHazardRectificationsById(id);
    }
}
