import request from '@/utils/request'

// 查询合格承包商列表
export function listQualified(query) {
  return request({
    url: '/contents/qualified/list',
    method: 'get',
    params: query
  })
}

// 查询合格承包商详细
export function getQualified(qualId) {
  return request({
    url: '/contents/qualified/' + qualId,
    method: 'get'
  })
}

// 新增合格承包商
export function addQualified(data) {
  return request({
    url: '/contents/qualified',
    method: 'post',
    data: data
  })
}

// 修改合格承包商
export function updateQualified(data) {
  return request({
    url: '/contents/qualified',
    method: 'put',
    data: data
  })
}

// 删除合格承包商
export function delQualified(qualId) {
  return request({
    url: '/contents/qualified/' + qualId,
    method: 'delete'
  })
}
