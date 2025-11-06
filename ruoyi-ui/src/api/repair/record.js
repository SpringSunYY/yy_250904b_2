import request from '@/utils/request'

// 查询检维修记录列表
export function listRecord(query) {
  return request({
    url: '/repair/record/list',
    method: 'get',
    params: query
  })
}

// 查询检维修记录详细
export function getRecord(recordId) {
  return request({
    url: '/repair/record/' + recordId,
    method: 'get'
  })
}

// 新增检维修记录
export function addRecord(data) {
  return request({
    url: '/repair/record',
    method: 'post',
    data: data
  })
}

// 修改检维修记录
export function updateRecord(data) {
  return request({
    url: '/repair/record',
    method: 'put',
    data: data
  })
}

// 删除检维修记录
export function delRecord(recordId) {
  return request({
    url: '/repair/record/' + recordId,
    method: 'delete'
  })
}
