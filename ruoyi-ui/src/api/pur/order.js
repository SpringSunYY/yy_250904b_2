import request from '@/utils/request'

// 查询采购订单列表
export function listOrder(query) {
  return request({
    url: '/pur/order/list',
    method: 'get',
    params: query
  })
}

// 查询采购订单详细
export function getOrder(orderId) {
  return request({
    url: '/pur/order/' + orderId,
    method: 'get'
  })
}

// 新增采购订单
export function addOrder(data) {
  return request({
    url: '/pur/order',
    method: 'post',
    data: data
  })
}

// 修改采购订单
export function updateOrder(data) {
  return request({
    url: '/pur/order',
    method: 'put',
    data: data
  })
}

// 删除采购订单
export function delOrder(orderId) {
  return request({
    url: '/pur/order/' + orderId,
    method: 'delete'
  })
}
