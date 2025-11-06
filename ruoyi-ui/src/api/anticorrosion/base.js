import request from '@/utils/request'

// 查询防腐基础信息列表
export function listBase(query) {
  return request({
    url: '/anticorrosion/base/list',
    method: 'get',
    params: query
  })
}

// 查询防腐基础信息详细
export function getBase(id) {
  return request({
    url: '/anticorrosion/base/' + id,
    method: 'get'
  })
}

// 新增防腐基础信息
export function addBase(data) {
  return request({
    url: '/anticorrosion/base',
    method: 'post',
    data: data
  })
}

// 修改防腐基础信息
export function updateBase(data) {
  return request({
    url: '/anticorrosion/base',
    method: 'put',
    data: data
  })
}

// 删除防腐基础信息
export function delBase(id) {
  return request({
    url: '/anticorrosion/base/' + id,
    method: 'delete'
  })
}
