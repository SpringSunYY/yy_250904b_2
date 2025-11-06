import request from '@/utils/request'

// 查询易损设备信息列表
export function listInfo(query) {
  return request({
    url: '/vulnerable/info/list',
    method: 'get',
    params: query
  })
}

// 查询易损设备信息详细
export function getInfo(id) {
  return request({
    url: '/vulnerable/info/' + id,
    method: 'get'
  })
}

// 新增易损设备信息
export function addInfo(data) {
  return request({
    url: '/vulnerable/info',
    method: 'post',
    data: data
  })
}

// 修改易损设备信息
export function updateInfo(data) {
  return request({
    url: '/vulnerable/info',
    method: 'put',
    data: data
  })
}

// 删除易损设备信息
export function delInfo(id) {
  return request({
    url: '/vulnerable/info/' + id,
    method: 'delete'
  })
}
