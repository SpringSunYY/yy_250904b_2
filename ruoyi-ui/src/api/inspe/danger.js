import request from '@/utils/request'

// 查询设备隐患治理列表
export function listDanger(query) {
  return request({
    url: '/inspe/danger/list',
    method: 'get',
    params: query
  })
}

// 查询设备隐患治理详细
export function getDanger(dangerId) {
  return request({
    url: '/inspe/danger/' + dangerId,
    method: 'get'
  })
}

// 新增设备隐患治理
export function addDanger(data) {
  return request({
    url: '/inspe/danger',
    method: 'post',
    data: data
  })
}

// 修改设备隐患治理
export function updateDanger(data) {
  return request({
    url: '/inspe/danger',
    method: 'put',
    data: data
  })
}

// 删除设备隐患治理
export function delDanger(dangerId) {
  return request({
    url: '/inspe/danger/' + dangerId,
    method: 'delete'
  })
}
