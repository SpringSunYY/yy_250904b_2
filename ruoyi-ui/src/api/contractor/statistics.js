import request from '@/utils/request'

export function trainingRateStatistics(query) {
  return request({
    url: '/statistics/trainingRate',
    method: 'get',
    params: query
  })
}


export function toolInventoryRateStatistics(query) {
  return request({
    url: '/statistics/toolInventoryRate',
    method: 'get',
    params: query
  })
}

//承包商安全协议签订完成率
export function securityAgreementRate(query) {
  return request({
    url: '/statistics/securityAgreementRate',
    method: 'get',
    params: query
  })
}

//承包商资质审查完成率
export function qualificationRate(query) {
  return request({
    url: '/statistics/qualificationRate',
    method: 'get',
    params: query
  })
}
