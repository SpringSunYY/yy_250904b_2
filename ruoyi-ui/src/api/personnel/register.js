import request from '@/utils/request'

// 查询人员信息台账列表
export function listRegister(query) {
  return request({
    url: '/personnel/register/list',
    method: 'get',
    params: query
  })
}

// 查询人员信息台账详细
export function getRegister(personnelId) {
  return request({
    url: '/personnel/register/' + personnelId,
    method: 'get'
  })
}

// 新增人员信息台账
export function addRegister(data) {
  return request({
    url: '/personnel/register',
    method: 'post',
    data: data
  })
}

// 修改人员信息台账
export function updateRegister(data) {
  return request({
    url: '/personnel/register',
    method: 'put',
    data: data
  })
}

// 删除人员信息台账
export function delRegister(personnelId) {
  return request({
    url: '/personnel/register/' + personnelId,
    method: 'delete'
  })
}
