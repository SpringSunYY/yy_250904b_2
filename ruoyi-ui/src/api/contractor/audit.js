import request from '@/utils/request'

// 查询承包商资格审查列表
export function listAudit(query) {
  return request({
    url: '/contractor/audit/list',
    method: 'get',
    params: query
  })
}

// 查询承包商资格审查详细
export function getAudit(auditId) {
  return request({
    url: '/contractor/audit/' + auditId,
    method: 'get'
  })
}

// 新增承包商资格审查
export function addAudit(data) {
  return request({
    url: '/contractor/audit',
    method: 'post',
    data: data
  })
}

// 修改承包商资格审查
export function updateAudit(data) {
  return request({
    url: '/contractor/audit',
    method: 'put',
    data: data
  })
}

// 删除承包商资格审查
export function delAudit(auditId) {
  return request({
    url: '/contractor/audit/' + auditId,
    method: 'delete'
  })
}
