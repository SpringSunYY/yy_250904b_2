import request from '@/utils/request'

// 查询设备安装验收列表
export function listDevice(query) {
  return request({
    url: '/equip/device/list',
    method: 'get',
    params: query
  })
}

// 查询设备安装验收详细
export function getDevice(deviceId) {
  return request({
    url: '/equip/device/' + deviceId,
    method: 'get'
  })
}

// 新增设备安装验收
export function addDevice(data) {
  return request({
    url: '/equip/device',
    method: 'post',
    data: data
  })
}

// 修改设备安装验收
export function updateDevice(data) {
  return request({
    url: '/equip/device',
    method: 'put',
    data: data
  })
}

// 删除设备安装验收
export function delDevice(deviceId) {
  return request({
    url: '/equip/device/' + deviceId,
    method: 'delete'
  })
}
