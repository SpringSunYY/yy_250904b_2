import request from '@/utils/request'

// 查询管道数据列表
export function listData(query) {
  return request({
    url: '/special/data/list',
    method: 'get',
    params: query
  })
}

// 查询管道数据详细
export function getData(pipelineId) {
  return request({
    url: '/special/data/' + pipelineId,
    method: 'get'
  })
}

// 新增管道数据
export function addData(data) {
  return request({
    url: '/special/data',
    method: 'post',
    data: data
  })
}

// 修改管道数据
export function updateData(data) {
  return request({
    url: '/special/data',
    method: 'put',
    data: data
  })
}

// 删除管道数据
export function delData(pipelineId) {
  return request({
    url: '/special/data/' + pipelineId,
    method: 'delete'
  })
}
