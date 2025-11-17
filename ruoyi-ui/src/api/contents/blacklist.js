import request from '@/utils/request'

// 查询承包商黑名单列表
export function listBlacklist(query) {
  return request({
    url: '/contents/blacklist/list',
    method: 'get',
    params: query
  })
}

// 查询承包商黑名单详细
export function getBlacklist(blackId) {
  return request({
    url: '/contents/blacklist/' + blackId,
    method: 'get'
  })
}

// 新增承包商黑名单
export function addBlacklist(data) {
  return request({
    url: '/contents/blacklist',
    method: 'post',
    data: data
  })
}

// 修改承包商黑名单
export function updateBlacklist(data) {
  return request({
    url: '/contents/blacklist',
    method: 'put',
    data: data
  })
}

// 删除承包商黑名单
export function delBlacklist(blackId) {
  return request({
    url: '/contents/blacklist/' + blackId,
    method: 'delete'
  })
}
