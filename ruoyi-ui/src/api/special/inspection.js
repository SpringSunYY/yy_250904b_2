import request from '@/utils/request'

// 查询检验检测列表
export function listInspection(query) {
  return request({
    url: '/special/inspection/list',
    method: 'get',
    params: query
  })
}

// 查询检验检测详细
export function getInspection(id) {
  return request({
    url: '/special/inspection/' + id,
    method: 'get'
  })
}

// 新增检验检测
export function addInspection(data) {
  return request({
    url: '/special/inspection',
    method: 'post',
    data: data
  })
}

// 修改检验检测
export function updateInspection(data) {
  return request({
    url: '/special/inspection',
    method: 'put',
    data: data
  })
}

// 删除检验检测
export function delInspection(id) {
  return request({
    url: '/special/inspection/' + id,
    method: 'delete'
  })
}
