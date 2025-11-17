package com.ruoyi.prepare.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.prepare.mapper.BizCoordinationMeetingMapper;
import com.ruoyi.prepare.domain.BizCoordinationMeeting;
import com.ruoyi.prepare.service.IBizCoordinationMeetingService;

/**
 * 协调会议Service业务层处理
 * 
 * @author Laogao
 * @date 2025-10-24
 */
@Service
public class BizCoordinationMeetingServiceImpl implements IBizCoordinationMeetingService 
{
    @Autowired
    private BizCoordinationMeetingMapper bizCoordinationMeetingMapper;

    /**
     * 查询协调会议
     * 
     * @param meetingId 协调会议主键
     * @return 协调会议
     */
    @Override
    public BizCoordinationMeeting selectBizCoordinationMeetingByMeetingId(Long meetingId)
    {
        return bizCoordinationMeetingMapper.selectBizCoordinationMeetingByMeetingId(meetingId);
    }

    /**
     * 查询协调会议列表
     * 
     * @param bizCoordinationMeeting 协调会议
     * @return 协调会议
     */
    @Override
    public List<BizCoordinationMeeting> selectBizCoordinationMeetingList(BizCoordinationMeeting bizCoordinationMeeting)
    {
        return bizCoordinationMeetingMapper.selectBizCoordinationMeetingList(bizCoordinationMeeting);
    }

    /**
     * 新增协调会议
     * 
     * @param bizCoordinationMeeting 协调会议
     * @return 结果
     */
    @Override
    public int insertBizCoordinationMeeting(BizCoordinationMeeting bizCoordinationMeeting)
    {
        return bizCoordinationMeetingMapper.insertBizCoordinationMeeting(bizCoordinationMeeting);
    }

    /**
     * 修改协调会议
     * 
     * @param bizCoordinationMeeting 协调会议
     * @return 结果
     */
    @Override
    public int updateBizCoordinationMeeting(BizCoordinationMeeting bizCoordinationMeeting)
    {
        return bizCoordinationMeetingMapper.updateBizCoordinationMeeting(bizCoordinationMeeting);
    }

    /**
     * 批量删除协调会议
     * 
     * @param meetingIds 需要删除的协调会议主键
     * @return 结果
     */
    @Override
    public int deleteBizCoordinationMeetingByMeetingIds(Long[] meetingIds)
    {
        return bizCoordinationMeetingMapper.deleteBizCoordinationMeetingByMeetingIds(meetingIds);
    }

    /**
     * 删除协调会议信息
     * 
     * @param meetingId 协调会议主键
     * @return 结果
     */
    @Override
    public int deleteBizCoordinationMeetingByMeetingId(Long meetingId)
    {
        return bizCoordinationMeetingMapper.deleteBizCoordinationMeetingByMeetingId(meetingId);
    }
}
