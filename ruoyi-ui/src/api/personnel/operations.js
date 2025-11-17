import request from '@/utils/request'

// 查询特种人员台账列表
export function listOperations(query) {
  return request({
    url: '/personnel/operations/list',
    method: 'get',
    params: query
  })
}

// 查询特种人员台账详细
export function getOperations(id) {
  return request({
    url: '/personnel/operations/' + id,
    method: 'get'
  })
}

// 新增特种人员台账
export function addOperations(data) {
  return request({
    url: '/personnel/operations',
    method: 'post',
    data: data
  })
}

// 修改特种人员台账
export function updateOperations(data) {
  return request({
    url: '/personnel/operations',
    method: 'put',
    data: data
  })
}

// 删除特种人员台账
export function delOperations(id) {
  return request({
    url: '/personnel/operations/' + id,
    method: 'delete'
  })
}
