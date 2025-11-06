import request from '@/utils/request'

// 查询润滑计划列表
export function listPlan(query) {
  return request({
    url: '/lubr/plan/list',
    method: 'get',
    params: query
  })
}

// 查询润滑计划详细
export function getPlan(planId) {
  return request({
    url: '/lubr/plan/' + planId,
    method: 'get'
  })
}

// 新增润滑计划
export function addPlan(data) {
  return request({
    url: '/lubr/plan',
    method: 'post',
    data: data
  })
}

// 修改润滑计划
export function updatePlan(data) {
  return request({
    url: '/lubr/plan',
    method: 'put',
    data: data
  })
}

// 删除润滑计划
export function delPlan(planId) {
  return request({
    url: '/lubr/plan/' + planId,
    method: 'delete'
  })
}
