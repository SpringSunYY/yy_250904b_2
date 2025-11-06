import request from '@/utils/request'

// 查询设备缺陷管理列表
export function listDefect(query) {
  return request({
    url: '/equip/defect/list',
    method: 'get',
    params: query
  })
}

// 查询设备缺陷管理详细
export function getDefect(defectId) {
  return request({
    url: '/equip/defect/' + defectId,
    method: 'get'
  })
}

// 新增设备缺陷管理
export function addDefect(data) {
  return request({
    url: '/equip/defect',
    method: 'post',
    data: data
  })
}

// 修改设备缺陷管理
export function updateDefect(data) {
  return request({
    url: '/equip/defect',
    method: 'put',
    data: data
  })
}

// 删除设备缺陷管理
export function delDefect(defectId) {
  return request({
    url: '/equip/defect/' + defectId,
    method: 'delete'
  })
}
