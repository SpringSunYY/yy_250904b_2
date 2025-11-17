import request from '@/utils/request'

// 查询项目变更管理列表
export function listChange(query) {
  return request({
    url: '/process/change/list',
    method: 'get',
    params: query
  })
}

// 查询项目变更管理详细
export function getChange(changeId) {
  return request({
    url: '/process/change/' + changeId,
    method: 'get'
  })
}

// 新增项目变更管理
export function addChange(data) {
  return request({
    url: '/process/change',
    method: 'post',
    data: data
  })
}

// 修改项目变更管理
export function updateChange(data) {
  return request({
    url: '/process/change',
    method: 'put',
    data: data
  })
}

// 删除项目变更管理
export function delChange(changeId) {
  return request({
    url: '/process/change/' + changeId,
    method: 'delete'
  })
}
