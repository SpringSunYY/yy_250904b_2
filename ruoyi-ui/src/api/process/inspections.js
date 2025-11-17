import request from '@/utils/request'

// 查询监督检查记录列表
export function listInspections(query) {
  return request({
    url: '/process/inspections/list',
    method: 'get',
    params: query
  })
}

// 查询监督检查记录详细
export function getInspections(id) {
  return request({
    url: '/process/inspections/' + id,
    method: 'get'
  })
}

// 新增监督检查记录
export function addInspections(data) {
  return request({
    url: '/process/inspections',
    method: 'post',
    data: data
  })
}

// 修改监督检查记录
export function updateInspections(data) {
  return request({
    url: '/process/inspections',
    method: 'put',
    data: data
  })
}

// 删除监督检查记录
export function delInspections(id) {
  return request({
    url: '/process/inspections/' + id,
    method: 'delete'
  })
}
