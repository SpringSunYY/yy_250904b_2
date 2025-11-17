import request from '@/utils/request'

// 查询培训记录列表
export function listRecords(query) {
  return request({
    url: '/training/records/list',
    method: 'get',
    params: query
  })
}

// 查询培训记录详细
export function getRecords(id) {
  return request({
    url: '/training/records/' + id,
    method: 'get'
  })
}

// 新增培训记录
export function addRecords(data) {
  return request({
    url: '/training/records',
    method: 'post',
    data: data
  })
}

// 修改培训记录
export function updateRecords(data) {
  return request({
    url: '/training/records',
    method: 'put',
    data: data
  })
}

// 删除培训记录
export function delRecords(id) {
  return request({
    url: '/training/records/' + id,
    method: 'delete'
  })
}
