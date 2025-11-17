import request from '@/utils/request'

// 查询合同与协议列表
export function listContract(query) {
  return request({
    url: '/choice/contract/list',
    method: 'get',
    params: query
  })
}

// 查询合同与协议详细
export function getContract(id) {
  return request({
    url: '/choice/contract/' + id,
    method: 'get'
  })
}

// 新增合同与协议
export function addContract(data) {
  return request({
    url: '/choice/contract',
    method: 'post',
    data: data
  })
}

// 修改合同与协议
export function updateContract(data) {
  return request({
    url: '/choice/contract',
    method: 'put',
    data: data
  })
}

// 删除合同与协议
export function delContract(id) {
  return request({
    url: '/choice/contract/' + id,
    method: 'delete'
  })
}
