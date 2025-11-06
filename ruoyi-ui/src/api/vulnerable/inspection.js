import request from '@/utils/request'

// 查询易损设备检查列表
export function listInspection(query) {
  return request({
    url: '/vulnerable/inspection/list',
    method: 'get',
    params: query
  })
}

// 查询易损设备检查详细
export function getInspection(id) {
  return request({
    url: '/vulnerable/inspection/' + id,
    method: 'get'
  })
}

// 新增易损设备检查
export function addInspection(data) {
  return request({
    url: '/vulnerable/inspection',
    method: 'post',
    data: data
  })
}

// 修改易损设备检查
export function updateInspection(data) {
  return request({
    url: '/vulnerable/inspection',
    method: 'put',
    data: data
  })
}

// 删除易损设备检查
export function delInspection(id) {
  return request({
    url: '/vulnerable/inspection/' + id,
    method: 'delete'
  })
}
