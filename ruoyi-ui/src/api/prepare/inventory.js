import request from '@/utils/request'

// 查询工器具检查列表
export function listInventory(query) {
  return request({
    url: '/prepare/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询工器具检查详细
export function getInventory(toolId) {
  return request({
    url: '/prepare/inventory/' + toolId,
    method: 'get'
  })
}

// 新增工器具检查
export function addInventory(data) {
  return request({
    url: '/prepare/inventory',
    method: 'post',
    data: data
  })
}

// 修改工器具检查
export function updateInventory(data) {
  return request({
    url: '/prepare/inventory',
    method: 'put',
    data: data
  })
}

// 删除工器具检查
export function delInventory(toolId) {
  return request({
    url: '/prepare/inventory/' + toolId,
    method: 'delete'
  })
}
