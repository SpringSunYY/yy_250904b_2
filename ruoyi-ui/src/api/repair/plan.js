import request from '@/utils/request'

// 查询设备检修计划列表
export function listRepairPlan(query) {
  return request({
    url: '/repair/plan/list',
    method: 'get',
    params: query
  })
}

// 查询设备检修计划详细
export function getPlan(planId) {
  return request({
    url: '/repair/plan/' + planId,
    method: 'get'
  })
}

// 新增设备检修计划
export function addPlan(data) {
  return request({
    url: '/repair/plan',
    method: 'post',
    data: data
  })
}

// 修改设备检修计划
export function updatePlan(data) {
  return request({
    url: '/repair/plan',
    method: 'put',
    data: data
  })
}

// 删除设备检修计划
export function delPlan(planId) {
  return request({
    url: '/repair/plan/' + planId,
    method: 'delete'
  })
}
