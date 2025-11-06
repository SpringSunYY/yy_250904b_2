import request from '@/utils/request'

// 查询设备运行管理列表
export function listStat(query) {
  return request({
    url: '/equip/stat/list',
    method: 'get',
    params: query
  })
}

// 查询设备运行管理详细
export function getStat(statId) {
  return request({
    url: '/equip/stat/' + statId,
    method: 'get'
  })
}

// 新增设备运行管理
export function addStat(data) {
  return request({
    url: '/equip/stat',
    method: 'post',
    data: data
  })
}

// 修改设备运行管理
export function updateStat(data) {
  return request({
    url: '/equip/stat',
    method: 'put',
    data: data
  })
}

// 删除设备运行管理
export function delStat(statId) {
  return request({
    url: '/equip/stat/' + statId,
    method: 'delete'
  })
}
