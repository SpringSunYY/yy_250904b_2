import request from '@/utils/request'

// 查询安全条件检查列表
export function listCondition(query) {
  return request({
    url: '/prepare/condition/list',
    method: 'get',
    params: query
  })
}

// 查询安全条件检查详细
export function getCondition(conditionId) {
  return request({
    url: '/prepare/condition/' + conditionId,
    method: 'get'
  })
}

// 新增安全条件检查
export function addCondition(data) {
  return request({
    url: '/prepare/condition',
    method: 'post',
    data: data
  })
}

// 修改安全条件检查
export function updateCondition(data) {
  return request({
    url: '/prepare/condition',
    method: 'put',
    data: data
  })
}

// 删除安全条件检查
export function delCondition(conditionId) {
  return request({
    url: '/prepare/condition/' + conditionId,
    method: 'delete'
  })
}
