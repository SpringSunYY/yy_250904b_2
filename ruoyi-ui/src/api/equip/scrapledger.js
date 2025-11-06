import request from '@/utils/request'

// 查询设备报废管理列表
export function listScrapledger(query) {
  return request({
    url: '/equip/scrapledger/list',
    method: 'get',
    params: query
  })
}

// 查询设备报废管理详细
export function getScrapledger(scrapId) {
  return request({
    url: '/equip/scrapledger/' + scrapId,
    method: 'get'
  })
}

// 新增设备报废管理
export function addScrapledger(data) {
  return request({
    url: '/equip/scrapledger',
    method: 'post',
    data: data
  })
}

// 修改设备报废管理
export function updateScrapledger(data) {
  return request({
    url: '/equip/scrapledger',
    method: 'put',
    data: data
  })
}

// 删除设备报废管理
export function delScrapledger(scrapId) {
  return request({
    url: '/equip/scrapledger/' + scrapId,
    method: 'delete'
  })
}
