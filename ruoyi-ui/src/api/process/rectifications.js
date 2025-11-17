import request from '@/utils/request'

// 查询隐患整改记录列表
export function listRectifications(query) {
  return request({
    url: '/process/rectifications/list',
    method: 'get',
    params: query
  })
}

// 查询隐患整改记录详细
export function getRectifications(id) {
  return request({
    url: '/process/rectifications/' + id,
    method: 'get'
  })
}

// 新增隐患整改记录
export function addRectifications(data) {
  return request({
    url: '/process/rectifications',
    method: 'post',
    data: data
  })
}

// 修改隐患整改记录
export function updateRectifications(data) {
  return request({
    url: '/process/rectifications',
    method: 'put',
    data: data
  })
}

// 删除隐患整改记录
export function delRectifications(id) {
  return request({
    url: '/process/rectifications/' + id,
    method: 'delete'
  })
}
