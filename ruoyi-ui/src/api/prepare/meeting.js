import request from '@/utils/request'

// 查询协调会议列表
export function listMeeting(query) {
  return request({
    url: '/prepare/meeting/list',
    method: 'get',
    params: query
  })
}

// 查询协调会议详细
export function getMeeting(meetingId) {
  return request({
    url: '/prepare/meeting/' + meetingId,
    method: 'get'
  })
}

// 新增协调会议
export function addMeeting(data) {
  return request({
    url: '/prepare/meeting',
    method: 'post',
    data: data
  })
}

// 修改协调会议
export function updateMeeting(data) {
  return request({
    url: '/prepare/meeting',
    method: 'put',
    data: data
  })
}

// 删除协调会议
export function delMeeting(meetingId) {
  return request({
    url: '/prepare/meeting/' + meetingId,
    method: 'delete'
  })
}
