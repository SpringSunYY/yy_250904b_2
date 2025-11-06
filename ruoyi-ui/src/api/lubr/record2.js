import request from '@/utils/request'

// 查询设备润滑记录列表
export function listRecord2(query) {
  return request({
    url: '/lubr/record2/list',
    method: 'get',
    params: query
  })
}

// 查询设备润滑记录详细
export function getRecord2(recordId) {
  return request({
    url: '/lubr/record2/' + recordId,
    method: 'get'
  })
}

// 新增设备润滑记录
export function addRecord2(data) {
  return request({
    url: '/lubr/record2',
    method: 'post',
    data: data
  })
}

// 修改设备润滑记录
export function updateRecord2(data) {
  return request({
    url: '/lubr/record2',
    method: 'put',
    data: data
  })
}

// 删除设备润滑记录
export function delRecord2(recordId) {
  return request({
    url: '/lubr/record2/' + recordId,
    method: 'delete'
  })
}
