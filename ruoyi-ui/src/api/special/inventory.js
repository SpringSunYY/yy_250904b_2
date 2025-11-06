import request from '@/utils/request'

// 查询场(厂)内专用机动车辆列表
export function listInventory(query) {
  return request({
    url: '/special/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询场(厂)内专用机动车辆详细
export function getInventory(id) {
  return request({
    url: '/special/inventory/' + id,
    method: 'get'
  })
}

// 新增场(厂)内专用机动车辆
export function addInventory(data) {
  return request({
    url: '/special/inventory',
    method: 'post',
    data: data
  })
}

// 修改场(厂)内专用机动车辆
export function updateInventory(data) {
  return request({
    url: '/special/inventory',
    method: 'put',
    data: data
  })
}

// 删除场(厂)内专用机动车辆
export function delInventory(id) {
  return request({
    url: '/special/inventory/' + id,
    method: 'delete'
  })
}
