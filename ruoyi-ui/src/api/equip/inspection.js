import request from '@/utils/request'

// 查询设备检验检测列表
export function listInspection(query) {
  return request({
    url: '/equip/inspection/list',
    method: 'get',
    params: query
  })
}

// 查询设备检验检测详细
export function getInspection(inspectionId) {
  return request({
    url: '/equip/inspection/' + inspectionId,
    method: 'get'
  })
}

// 新增设备检验检测
export function addInspection(data) {
  return request({
    url: '/equip/inspection',
    method: 'post',
    data: data
  })
}

// 修改设备检验检测
export function updateInspection(data) {
  return request({
    url: '/equip/inspection',
    method: 'put',
    data: data
  })
}

// 删除设备检验检测
export function delInspection(inspectionId) {
  return request({
    url: '/equip/inspection/' + inspectionId,
    method: 'delete'
  })
}
