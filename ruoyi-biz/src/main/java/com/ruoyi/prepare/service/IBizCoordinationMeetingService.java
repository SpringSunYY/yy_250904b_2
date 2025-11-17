package com.ruoyi.prepare.service;

import java.util.List;
import com.ruoyi.prepare.domain.BizCoordinationMeeting;

/**
 * 协调会议Service接口
 * 
 * @author Laogao
 * @date 2025-10-24
 */
public interface IBizCoordinationMeetingService 
{
    /**
     * 查询协调会议
     * 
     * @param meetingId 协调会议主键
     * @return 协调会议
     */
    public BizCoordinationMeeting selectBizCoordinationMeetingByMeetingId(Long meetingId);

    /**
     * 查询协调会议列表
     * 
     * @param bizCoordinationMeeting 协调会议
     * @return 协调会议集合
     */
    public List<BizCoordinationMeeting> selectBizCoordinationMeetingList(BizCoordinationMeeting bizCoordinationMeeting);

    /**
     * 新增协调会议
     * 
     * @param bizCoordinationMeeting 协调会议
     * @return 结果
     */
    public int insertBizCoordinationMeeting(BizCoordinationMeeting bizCoordinationMeeting);

    /**
     * 修改协调会议
     * 
     * @param bizCoordinationMeeting 协调会议
     * @return 结果
     */
    public int updateBizCoordinationMeeting(BizCoordinationMeeting bizCoordinationMeeting);

    /**
     * 批量删除协调会议
     * 
     * @param meetingIds 需要删除的协调会议主键集合
     * @return 结果
     */
    public int deleteBizCoordinationMeetingByMeetingIds(Long[] meetingIds);

    /**
     * 删除协调会议信息
     * 
     * @param meetingId 协调会议主键
     * @return 结果
     */
    public int deleteBizCoordinationMeetingByMeetingId(Long meetingId);
}
