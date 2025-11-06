import request from '@/utils/request'

// 查询巡检记录列表
export function listPatrol(query) {
  return request({
    url: '/patrol/patrol/list',
    method: 'get',
    params: query
  })
}

// 查询巡检记录详细
export function getPatrol(patrolId) {
  return request({
    url: '/patrol/patrol/' + patrolId,
    method: 'get'
  })
}

// 新增巡检记录
export function addPatrol(data) {
  return request({
    url: '/patrol/patrol',
    method: 'post',
    data: data
  })
}

// 修改巡检记录
export function updatePatrol(data) {
  return request({
    url: '/patrol/patrol',
    method: 'put',
    data: data
  })
}

// 删除巡检记录
export function delPatrol(patrolId) {
  return request({
    url: '/patrol/patrol/' + patrolId,
    method: 'delete'
  })
}
