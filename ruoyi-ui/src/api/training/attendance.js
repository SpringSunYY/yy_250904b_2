import request from '@/utils/request'

// 查询人员参训记录列表
export function listAttendance(query) {
  return request({
    url: '/training/attendance/list',
    method: 'get',
    params: query
  })
}

// 查询人员参训记录详细
export function getAttendance(id) {
  return request({
    url: '/training/attendance/' + id,
    method: 'get'
  })
}

// 新增人员参训记录
export function addAttendance(data) {
  return request({
    url: '/training/attendance',
    method: 'post',
    data: data
  })
}

// 修改人员参训记录
export function updateAttendance(data) {
  return request({
    url: '/training/attendance',
    method: 'put',
    data: data
  })
}

// 删除人员参训记录
export function delAttendance(id) {
  return request({
    url: '/training/attendance/' + id,
    method: 'delete'
  })
}
