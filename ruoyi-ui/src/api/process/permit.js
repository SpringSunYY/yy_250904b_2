import request from '@/utils/request'

// 查询特殊作业管理列表
export function listPermit(query) {
  return request({
    url: '/process/permit/list',
    method: 'get',
    params: query
  })
}

// 查询特殊作业管理详细
export function getPermit(permitId) {
  return request({
    url: '/process/permit/' + permitId,
    method: 'get'
  })
}

// 新增特殊作业管理
export function addPermit(data) {
  return request({
    url: '/process/permit',
    method: 'post',
    data: data
  })
}

// 修改特殊作业管理
export function updatePermit(data) {
  return request({
    url: '/process/permit',
    method: 'put',
    data: data
  })
}

// 删除特殊作业管理
export function delPermit(permitId) {
  return request({
    url: '/process/permit/' + permitId,
    method: 'delete'
  })
}
