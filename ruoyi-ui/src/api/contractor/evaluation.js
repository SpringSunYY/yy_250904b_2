import request from '@/utils/request'

// 查询安全业绩评价列表
export function listEvaluation(query) {
  return request({
    url: '/contractor/evaluation/list',
    method: 'get',
    params: query
  })
}

// 查询安全业绩评价详细
export function getEvaluation(id) {
  return request({
    url: '/contractor/evaluation/' + id,
    method: 'get'
  })
}

// 新增安全业绩评价
export function addEvaluation(data) {
  return request({
    url: '/contractor/evaluation',
    method: 'post',
    data: data
  })
}

// 修改安全业绩评价
export function updateEvaluation(data) {
  return request({
    url: '/contractor/evaluation',
    method: 'put',
    data: data
  })
}

// 删除安全业绩评价
export function delEvaluation(id) {
  return request({
    url: '/contractor/evaluation/' + id,
    method: 'delete'
  })
}
