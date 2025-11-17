import request from '@/utils/request'

// 查询安全技术交底列表
export function listBriefing(query) {
  return request({
    url: '/prepare/briefing/list',
    method: 'get',
    params: query
  })
}

// 查询安全技术交底详细
export function getBriefing(id) {
  return request({
    url: '/prepare/briefing/' + id,
    method: 'get'
  })
}

// 新增安全技术交底
export function addBriefing(data) {
  return request({
    url: '/prepare/briefing',
    method: 'post',
    data: data
  })
}

// 修改安全技术交底
export function updateBriefing(data) {
  return request({
    url: '/prepare/briefing',
    method: 'put',
    data: data
  })
}

// 删除安全技术交底
export function delBriefing(id) {
  return request({
    url: '/prepare/briefing/' + id,
    method: 'delete'
  })
}
