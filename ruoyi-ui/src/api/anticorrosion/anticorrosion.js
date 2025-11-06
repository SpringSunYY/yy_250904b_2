import request from '@/utils/request'

// 查询防腐蚀检测列表
export function listAnticorrosion(query) {
  return request({
    url: '/anticorrosion/anticorrosion/list',
    method: 'get',
    params: query
  })
}

// 查询防腐蚀检测详细
export function getAnticorrosion(id) {
  return request({
    url: '/anticorrosion/anticorrosion/' + id,
    method: 'get'
  })
}

// 新增防腐蚀检测
export function addAnticorrosion(data) {
  return request({
    url: '/anticorrosion/anticorrosion',
    method: 'post',
    data: data
  })
}

// 修改防腐蚀检测
export function updateAnticorrosion(data) {
  return request({
    url: '/anticorrosion/anticorrosion',
    method: 'put',
    data: data
  })
}

// 删除防腐蚀检测
export function delAnticorrosion(id) {
  return request({
    url: '/anticorrosion/anticorrosion/' + id,
    method: 'delete'
  })
}
