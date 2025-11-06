import request from '@/utils/request'

// 查询设备检查记录列表
export function listInspe(query) {
  return request({
    url: '/inspe/inspe/list',
    method: 'get',
    params: query
  })
}

// 查询设备检查记录详细
export function getInspe(inspeId) {
  return request({
    url: '/inspe/inspe/' + inspeId,
    method: 'get'
  })
}

// 新增设备检查记录
export function addInspe(data) {
  return request({
    url: '/inspe/inspe',
    method: 'post',
    data: data
  })
}

// 修改设备检查记录
export function updateInspe(data) {
  return request({
    url: '/inspe/inspe',
    method: 'put',
    data: data
  })
}

// 删除设备检查记录
export function delInspe(inspeId) {
  return request({
    url: '/inspe/inspe/' + inspeId,
    method: 'delete'
  })
}
