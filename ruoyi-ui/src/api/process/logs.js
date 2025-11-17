import request from '@/utils/request'

// 查询项目日志列表
export function listLogs(query) {
  return request({
    url: '/process/logs/list',
    method: 'get',
    params: query
  })
}

// 查询项目日志详细
export function getLogs(logsId) {
  return request({
    url: '/process/logs/' + logsId,
    method: 'get'
  })
}

// 新增项目日志
export function addLogs(data) {
  return request({
    url: '/process/logs',
    method: 'post',
    data: data
  })
}

// 修改项目日志
export function updateLogs(data) {
  return request({
    url: '/process/logs',
    method: 'put',
    data: data
  })
}

// 删除项目日志
export function delLogs(logsId) {
  return request({
    url: '/process/logs/' + logsId,
    method: 'delete'
  })
}
