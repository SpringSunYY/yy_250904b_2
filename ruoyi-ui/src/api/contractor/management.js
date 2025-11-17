import request from '@/utils/request'

// 查询承包商制度列表
export function listManagement(query) {
  return request({
    url: '/contractor/management/list',
    method: 'get',
    params: query
  })
}

// 查询承包商制度详细
export function getManagement(nanageId) {
  return request({
    url: '/contractor/management/' + nanageId,
    method: 'get'
  })
}

// 新增承包商制度
export function addManagement(data) {
  return request({
    url: '/contractor/management',
    method: 'post',
    data: data
  })
}

// 修改承包商制度
export function updateManagement(data) {
  return request({
    url: '/contractor/management',
    method: 'put',
    data: data
  })
}

// 删除承包商制度
export function delManagement(nanageId) {
  return request({
    url: '/contractor/management/' + nanageId,
    method: 'delete'
  })
}
