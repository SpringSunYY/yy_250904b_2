import request from '@/utils/request'

// 查询安全日志列表
export function listSafety(query) {
  return request({
    url: '/process/safety/list',
    method: 'get',
    params: query
  })
}

// 查询安全日志详细
export function getSafety(logId) {
  return request({
    url: '/process/safety/' + logId,
    method: 'get'
  })
}

// 新增安全日志
export function addSafety(data) {
  return request({
    url: '/process/safety',
    method: 'post',
    data: data
  })
}

// 修改安全日志
export function updateSafety(data) {
  return request({
    url: '/process/safety',
    method: 'put',
    data: data
  })
}

// 删除安全日志
export function delSafety(logId) {
  return request({
    url: '/process/safety/' + logId,
    method: 'delete'
  })
}
