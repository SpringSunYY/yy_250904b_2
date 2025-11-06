import request from '@/utils/request'

//设备点检完成率
export function checkRateStatistics(query) {
  return request({
    url: '/statistics/checkRate',
    method: 'get',
    params: query
  })
}

//设备运行时间统计
export function runTimeStatistics(query) {
  return request({
    url: '/statistics/runTime',
    method: 'get',
    params: query
  })
}

//设备润滑率
export function lubricateRateStatistics(query) {
  return request({
    url: '/statistics/lubricateRate',
    method: 'get',
    params: query
  })
}

//设备隐患整改率
export function defectEliminateStatistics(query) {
  return request({
    url: '/statistics/defectEliminate',
    method: 'get',
    params: query
  })
}

//设备泄露率
export function leakageRateStatistics(query) {
  return request({
    url: '/statistics/leakageRate',
    method: 'get',
    params: query
  })
}

//设备缺陷消除率
export function defectEliminateRateStatistics(query) {
  return request({
    url: '/statistics/defectEliminateRate',
    method: 'get',
    params: query
  })
}
