import request from '@/utils/request'

// 查询发包项目信息列表
export function listProject(query) {
  return request({
    url: '/contractor/project/list',
    method: 'get',
    params: query
  })
}

// 查询发包项目信息详细
export function getProject(projectId) {
  return request({
    url: '/contractor/project/' + projectId,
    method: 'get'
  })
}

// 新增发包项目信息
export function addProject(data) {
  return request({
    url: '/contractor/project',
    method: 'post',
    data: data
  })
}

// 修改发包项目信息
export function updateProject(data) {
  return request({
    url: '/contractor/project',
    method: 'put',
    data: data
  })
}

// 删除发包项目信息
export function delProject(projectId) {
  return request({
    url: '/contractor/project/' + projectId,
    method: 'delete'
  })
}
