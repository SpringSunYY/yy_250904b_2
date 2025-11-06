import request from '@/utils/request'

// 查询密封点列表
export function listBase(query) {
  return request({
    url: '/seal/base/list',
    method: 'get',
    params: query
  })
}

// 查询密封点详细
export function getBase(baseId) {
  return request({
    url: '/seal/base/' + baseId,
    method: 'get'
  })
}

// 新增密封点
export function addBase(data) {
  return request({
    url: '/seal/base',
    method: 'post',
    data: data
  })
}

// 修改密封点
export function updateBase(data) {
  return request({
    url: '/seal/base',
    method: 'put',
    data: data
  })
}

// 删除密封点
export function delBase(baseId) {
  return request({
    url: '/seal/base/' + baseId,
    method: 'delete'
  })
}
