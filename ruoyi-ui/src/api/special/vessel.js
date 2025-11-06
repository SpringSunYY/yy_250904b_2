import request from '@/utils/request'

// 查询压力容器列表
export function listVessel(query) {
  return request({
    url: '/special/vessel/list',
    method: 'get',
    params: query
  })
}

// 查询压力容器详细
export function getVessel(id) {
  return request({
    url: '/special/vessel/' + id,
    method: 'get'
  })
}

// 新增压力容器
export function addVessel(data) {
  return request({
    url: '/special/vessel',
    method: 'post',
    data: data
  })
}

// 修改压力容器
export function updateVessel(data) {
  return request({
    url: '/special/vessel',
    method: 'put',
    data: data
  })
}

// 删除压力容器
export function delVessel(id) {
  return request({
    url: '/special/vessel/' + id,
    method: 'delete'
  })
}
