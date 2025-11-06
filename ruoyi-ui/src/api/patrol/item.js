import request from '@/utils/request'

// 查询设备巡检项列表
export function listItem(query) {
  return request({
    url: '/patrol/item/list',
    method: 'get',
    params: query
  })
}

// 查询设备巡检项详细
export function getItem(itemId) {
  return request({
    url: '/patrol/item/' + itemId,
    method: 'get'
  })
}

// 新增设备巡检项
export function addItem(data) {
  return request({
    url: '/patrol/item',
    method: 'post',
    data: data
  })
}

// 修改设备巡检项
export function updateItem(data) {
  return request({
    url: '/patrol/item',
    method: 'put',
    data: data
  })
}

// 删除设备巡检项
export function delItem(itemId) {
  return request({
    url: '/patrol/item/' + itemId,
    method: 'delete'
  })
}
