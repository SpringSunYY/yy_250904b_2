import request from '@/utils/request'

// 查询设备台账列表
export function listLedger(query) {
  return request({
    url: '/equip/ledger/list',
    method: 'get',
    params: query
  })
}

// 查询设备台账详细
export function getLedger(equipId) {
  return request({
    url: '/equip/ledger/' + equipId,
    method: 'get'
  })
}

// 新增设备台账
export function addLedger(data) {
  return request({
    url: '/equip/ledger',
    method: 'post',
    data: data
  })
}

// 修改设备台账
export function updateLedger(data) {
  return request({
    url: '/equip/ledger',
    method: 'put',
    data: data
  })
}

// 删除设备台账
export function delLedger(equipId) {
  return request({
    url: '/equip/ledger/' + equipId,
    method: 'delete'
  })
}
