import request from '@/utils/request'

// 查询锅炉列表
export function listBoiler(query) {
  return request({
    url: '/special/boiler/list',
    method: 'get',
    params: query
  })
}

// 查询锅炉详细
export function getBoiler(boilerId) {
  return request({
    url: '/special/boiler/' + boilerId,
    method: 'get'
  })
}

// 新增锅炉
export function addBoiler(data) {
  return request({
    url: '/special/boiler',
    method: 'post',
    data: data
  })
}

// 修改锅炉
export function updateBoiler(data) {
  return request({
    url: '/special/boiler',
    method: 'put',
    data: data
  })
}

// 删除锅炉
export function delBoiler(boilerId) {
  return request({
    url: '/special/boiler/' + boilerId,
    method: 'delete'
  })
}
