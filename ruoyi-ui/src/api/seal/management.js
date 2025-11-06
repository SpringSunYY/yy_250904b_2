import request from '@/utils/request'

// 查询泄露处置列表
export function listManagement(query) {
  return request({
    url: '/seal/management/list',
    method: 'get',
    params: query
  })
}

// 查询泄露处置详细
export function getManagement(manageId) {
  return request({
    url: '/seal/management/' + manageId,
    method: 'get'
  })
}

// 新增泄露处置
export function addManagement(data) {
  return request({
    url: '/seal/management',
    method: 'post',
    data: data
  })
}

// 修改泄露处置
export function updateManagement(data) {
  return request({
    url: '/seal/management',
    method: 'put',
    data: data
  })
}

// 删除泄露处置
export function delManagement(manageId) {
  return request({
    url: '/seal/management/' + manageId,
    method: 'delete'
  })
}
