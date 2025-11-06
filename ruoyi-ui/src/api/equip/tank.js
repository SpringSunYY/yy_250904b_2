import request from '@/utils/request'

// 查询常压储罐检查列表
export function listTank(query) {
  return request({
    url: '/equip/tank/list',
    method: 'get',
    params: query
  })
}

// 查询常压储罐检查详细
export function getTank(id) {
  return request({
    url: '/equip/tank/' + id,
    method: 'get'
  })
}

// 新增常压储罐检查
export function addTank(data) {
  return request({
    url: '/equip/tank',
    method: 'post',
    data: data
  })
}

// 修改常压储罐检查
export function updateTank(data) {
  return request({
    url: '/equip/tank',
    method: 'put',
    data: data
  })
}

// 删除常压储罐检查
export function delTank(id) {
  return request({
    url: '/equip/tank/' + id,
    method: 'delete'
  })
}
